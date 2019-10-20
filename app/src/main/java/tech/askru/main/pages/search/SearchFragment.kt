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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import tech.askru.R

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchResultsAdapter = SearchResultsAdapter()
        recycler_view_search_results.adapter = searchResultsAdapter
        recycler_view_search_results.layoutManager = LinearLayoutManager(context)
        recycler_view_search_results.setHasFixedSize(true)

        searchViewModel.searchResults.observe(this, Observer { searchResults ->
            // This if statement is because this method is annoyingly called on fragment creation
            // for some reason
            if (edit_text_search.text.isNotEmpty()) {
                if (searchResults.isEmpty()) {
                    recycler_view_search_results.visibility = View.INVISIBLE
                    text_view_no_results.visibility = View.VISIBLE
                } else {
                    searchResultsAdapter.loadNewData(searchResults)
                    text_view_no_results.visibility = View.INVISIBLE
                    recycler_view_search_results.visibility = View.VISIBLE
                }
            }
        })

        ////////////////////////

        edit_text_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        text_view_no_results.visibility = View.INVISIBLE
                    } else {
                        searchViewModel.viewModelScope.launch {
                            searchViewModel.searchQuestions(s.toString())
                        }
                    }
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
