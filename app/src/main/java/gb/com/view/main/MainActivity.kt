package gb.com.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.com.R
import gb.com.databinding.ActivityMainBinding
import gb.com.view.history.HistoryFragment
import gb.com.view.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, SearchFragment.newInstance())
            .commit()

        binding.bottomNavigationMenu.setOnItemSelectedListener {item ->
            when (item.itemId) {
                R.id.action_search -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, SearchFragment.newInstance())
                        .commit()
                    true
                }
                R.id.action_history -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, HistoryFragment.newInstance())
                        .commit()
                    true
                }
                R.id.action_favorite -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, HistoryFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}