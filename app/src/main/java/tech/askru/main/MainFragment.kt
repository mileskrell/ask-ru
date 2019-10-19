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
import tech.askru.main.pages.MapFragment
import tech.askru.main.pages.SearchFragment
import tech.askru.main.pages.TopFragment

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private val searchFragment = SearchFragment()
    private val mapFragment = MapFragment()
    private val topFragment = TopFragment()

    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentManager?.commit {
            add(R.id.main_fragment_container, searchFragment)
            add(R.id.main_fragment_container, mapFragment)
            hide(mapFragment)
            add(R.id.main_fragment_container, topFragment)
            hide(topFragment)
        }

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.dest_search_fragment -> {
                    fragmentManager?.commit {
                        hide(mapFragment)
                        hide(topFragment)
                        show(searchFragment)
                    }
                    true
                }
                R.id.dest_map_fragment -> {
                    fragmentManager?.commit {
                        hide(searchFragment)
                        hide(topFragment)
                        show(mapFragment)
                    }
                    true
                }
                R.id.dest_top_fragment -> {
                    fragmentManager?.commit {
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
}
