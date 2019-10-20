package tech.askru.questiondetails


import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_question_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.askru.R
import tech.askru.login.LoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class QuestionDetailsFragment : Fragment() {

    val args: QuestionDetailsFragmentArgs by navArgs()

    lateinit var questionDetailsViewModel: QuestionDetailsViewModel
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        questionDetailsViewModel =
            ViewModelProviders.of(this).get(QuestionDetailsViewModel::class.java)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.fragment_question_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        questionDetailsViewModel.viewModelScope.launch {
            questionDetailsViewModel.getAdviceForQuestion(args.question)
        }

        text_view_question_title.text = args.question.title
        text_view_question_body.text = args.question.body

        val adviceListAdapter = AdviceListAdapter()

        recycler_view_advice_list.adapter = adviceListAdapter
        recycler_view_advice_list.layoutManager = LinearLayoutManager(context)
        recycler_view_advice_list.setHasFixedSize(true)

        questionDetailsViewModel.adviceList.observe(this, Observer { newAdviceList ->
            adviceListAdapter.loadNewData(newAdviceList)
        })

        question_details_add_advice_fab.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.post_advice_dialog, null)

            val dialogEditText = dialogView.findViewById<EditText>(R.id.post_advice_dialog_edit_text)

            val dialog = AlertDialog.Builder(context!!)
                .setView(dialogView)
                .setPositiveButton("Create advice") { dialog, which ->
                    questionDetailsViewModel.viewModelScope.launch {
                        val addAdviceSuccessful = questionDetailsViewModel.addAdviceToQuestion(
                            args.question._id,
                            dialogEditText.text.toString(),
                            loginViewModel.userToken
                        )
                        withContext(Dispatchers.Main) {
                            if (addAdviceSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Successfully created advice",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Failed to create advice. Please try again.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        delay(1000)
                        // Refresh, whether or not it was successfully created
                        questionDetailsViewModel.refreshQuestionAndAdvice(args.question._id)
                    }
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .setCancelable(false)
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = false

            dialogEditText.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = s?.isNotEmpty() == true
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}
