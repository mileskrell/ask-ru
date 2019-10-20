package tech.askru.main.pages.newquestion


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_new_question.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import tech.askru.R
import tech.askru.Repository
import tech.askru.hideSoftKeyboard
import tech.askru.login.LoginViewModel
import tech.askru.main.MainFragment

/**
 * A simple [Fragment] subclass.
 */
class NewQuestionFragment(val mainFragment: MainFragment) : Fragment() {

    lateinit var newQuestionViewModel: NewQuestionViewModel
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newQuestionViewModel =
            ViewModelProviders.of(activity!!).get(NewQuestionViewModel::class.java)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.fragment_new_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        edit_text_new_question_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newQuestionViewModel.titleText = s.toString()
                button_post_question.isEnabled =
                    edit_text_new_question_title.text?.isNotEmpty() == true && edit_text_new_question_body.text?.isNotEmpty() == true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        edit_text_new_question_body.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newQuestionViewModel.bodyText = s.toString()
                button_post_question.isEnabled =
                    edit_text_new_question_title.text?.isNotEmpty() == true && edit_text_new_question_body.text?.isNotEmpty() == true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        button_post_question.setOnClickListener {
            hideSoftKeyboard()
            button_post_question.visibility = View.INVISIBLE
            progress_bar_create_question.visibility = View.VISIBLE

            newQuestionViewModel.viewModelScope.launch {
                val result = newQuestionViewModel.createQuestion(
                    edit_text_new_question_title.text.toString(),
                    edit_text_new_question_body.text.toString(),
                    loginViewModel.userToken
                )
                withContext(Dispatchers.Main) {
                    if (result) {
                        edit_text_new_question_title.text.clear()
                        edit_text_new_question_body.text.clear()
                        Toast.makeText(context, "Question posted successfully", Toast.LENGTH_LONG)
                            .show()
                        mainFragment.refreshSearchResultsExternal()
                    } else {
                        Toast.makeText(
                            context,
                            "Error posting question. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    progress_bar_create_question.visibility = View.INVISIBLE
                    button_post_question.visibility = View.VISIBLE
                }
            }
        }

        edit_text_new_question_title.setText(newQuestionViewModel.titleText)
        edit_text_new_question_body.setText(newQuestionViewModel.bodyText)
    }
}
