package tech.askru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(
            this, Navigation.findNavController(this, R.id.nav_host_fragment), AppBarConfiguration(
                setOf(R.id.dest_login_fragment, R.id.dest_main_fragment)
            )
        )
    }
}
