package tech.askru.main.pages.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch

import tech.askru.R
import tech.askru.hideSoftKeyboard
import tech.askru.main.MainFragmentDirections

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchResultsAdapter = SearchResultsAdapter { question ->
            // Called when an item is clicked
            hideSoftKeyboard()
            findNavController().navigate(
                MainFragmentDirections.actionDestMainFragmentToDestQuestionDetailsFragment(
                    question
                )
            )
        }

        recycler_view_search_results.adapter = searchResultsAdapter
        recycler_view_search_results.layoutManager = LinearLayoutManager(context)
        recycler_view_search_results.setHasFixedSize(true)

        searchViewModel.searchResults.observe(this, Observer { searchResults ->
            // This if statement is because this method is annoyingly called on fragment creation
            // for some reason

            if (searchResults.isEmpty()) {
                recycler_view_search_results.visibility = View.INVISIBLE
                text_view_no_results.visibility = View.VISIBLE
            } else {
                searchResultsAdapter.loadNewData(searchResults)
                text_view_no_results.visibility = View.INVISIBLE
                recycler_view_search_results.visibility = View.VISIBLE
            }
        })

        ////////////////////////

        edit_text_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.searchText = s.toString()
                if (s != null) {
                    searchViewModel.viewModelScope.launch {
                        if (s.isEmpty()) {
                            searchViewModel.listQuestions()
                        } else {
                            searchViewModel.searchQuestions(s.toString())
                        }
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        edit_text_search.setText(searchViewModel.searchText)
    }
}
