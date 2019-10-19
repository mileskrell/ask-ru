package tech.askru.main.pages


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_map.*

import tech.askru.R

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    lateinit var mapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        map_temp_text_view.text = "Number of map clicks: ${mapViewModel.numClicks.value}"

        map_temp_text_view.setOnClickListener {
            mapViewModel.numClicks.value = (mapViewModel.numClicks.value ?: 0) + 1
            map_temp_text_view.text = "Number of map clicks: ${mapViewModel.numClicks.value}"
        }
    }
}
