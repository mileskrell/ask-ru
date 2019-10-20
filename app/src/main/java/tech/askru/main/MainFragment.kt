package tech.askru.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_main.*
import tech.askru.R
import tech.askru.hideSoftKeyboard
import tech.askru.main.pages.MapFragment
import tech.askru.main.pages.newquestion.NewQuestionFragment
import tech.askru.main.pages.search.SearchFragment
import tech.askru.main.pages.TopFragment

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var newQuestionFragment: NewQuestionFragment
    lateinit var searchFragment: SearchFragment
    lateinit var mapFragment: MapFragment
    lateinit var topFragment: TopFragment

    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            newQuestionFragment = NewQuestionFragment()
            searchFragment = SearchFragment()
            mapFragment = MapFragment()
            topFragment = TopFragment()

            fragmentManager?.commit {
                add(R.id.main_fragment_container, newQuestionFragment, "newQuestion")
                hide(newQuestionFragment)
                add(R.id.main_fragment_container, searchFragment, "search")
                add(R.id.main_fragment_container, mapFragment, "map")
                hide(mapFragment)
                add(R.id.main_fragment_container, topFragment, "top")
                hide(topFragment)
            }
            bottom_nav_view.selectedItemId = R.id.menu_item_search_fragment
        } else {
            newQuestionFragment = fragmentManager?.findFragmentByTag("newQuestion") as NewQuestionFragment
            searchFragment = fragmentManager?.findFragmentByTag("search") as SearchFragment
            mapFragment = fragmentManager?.findFragmentByTag("map") as MapFragment
            topFragment = fragmentManager?.findFragmentByTag("top") as TopFragment
            bottom_nav_view.selectedItemId = savedInstanceState.getInt("selectedItemId")
        }

        bottom_nav_view.setOnNavigationItemSelectedListener {
            hideSoftKeyboard()
            when(it.itemId) {
                R.id.menu_item_new_question_fragment -> {
                    fragmentManager?.commit {
                        show(newQuestionFragment)
                        hide(mapFragment)
                        hide(topFragment)
                        hide(searchFragment)
                    }
                    true
                }
                R.id.menu_item_search_fragment -> {
                    fragmentManager?.commit {
                        hide(newQuestionFragment)
                        show(searchFragment)
                        hide(mapFragment)
                        hide(topFragment)
                    }
                    true
                }
                R.id.menu_item_map_fragment -> {
                    fragmentManager?.commit {
                        hide(newQuestionFragment)
                        hide(searchFragment)
                        show(mapFragment)
                        hide(topFragment)
                    }
                    true
                }
                R.id.menu_item_top_fragment -> {
                    fragmentManager?.commit {

                        hide(newQuestionFragment)
                        hide(searchFragment)
                        hide(mapFragment)
                        show(topFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("selectedItemId", bottom_nav_view.selectedItemId)
    }
}
