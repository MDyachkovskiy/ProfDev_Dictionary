package gb.com.view.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.google.android.material.bottomnavigation.BottomNavigationView
import gb.com.R
import gb.com.databinding.ActivityMainBinding
import gb.com.utils.viewById
import gb.com.view.favorite.FavoriteFragment
import gb.com.view.history.HistoryFragment
import gb.com.view.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null



    private val bottomNavigationMenu by viewById<BottomNavigationView>(R.id.bottom_navigation_menu)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

        setDefaultSplashScreen()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, SearchFragment.newInstance())
            .commit()

       bottomNavigationMenu.setOnItemSelectedListener {item ->
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
                        .replace(R.id.main_container, FavoriteFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
       }
    }


    private fun setDefaultSplashScreen() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
        }

        setSplashScreenDuration()
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    @RequiresApi(31)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener {splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )

            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 1000L

            slideLeft.doOnEnd {splashScreenView}
            slideLeft.start()
        }
    }
}