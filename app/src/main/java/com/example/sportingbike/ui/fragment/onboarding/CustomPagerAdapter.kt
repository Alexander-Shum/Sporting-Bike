package com.example.sportingbike.ui.fragment.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.sportingbike.R
import com.example.sportingbike.ui.fragment.menu.MainFragment

class CustomPagerAdapter(context: Context, private val activity: FragmentActivity) :
    PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val titles = arrayOf(
        "Welcome to Sporting Bike",
        "How well do you know the bike?",
        "Play game and earn money"
    )

    private val texts = arrayOf(
        "Glad see you!",
        "You can test your knowledge in a quiz",
        "Catch coins and dodge rocks."
    )

    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.fragment_on_boarding_item, container, false)

        val title = view.findViewById<TextView>(R.id.titleTV)
        val text = view.findViewById<TextView>(R.id.textTV)
        val exit = view.findViewById<ImageButton>(R.id.exitBtn)

        title.text = titles[position]
        text.text = texts[position]

        if (position == titles.size - 1) {
            exit.visibility = View.VISIBLE
            exit.setOnClickListener {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, MainFragment(), "main")
                    .addToBackStack("fragment")
                    .commit()
            }
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}