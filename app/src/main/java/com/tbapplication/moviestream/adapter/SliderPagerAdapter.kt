package com.tbapplication.moviestream.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.tbapplication.moviestream.R
import com.tbapplication.moviestream.model.Slider

class SliderPagerAdapter(private var context: Context, list: List<Slider>) :
    PagerAdapter() {
    private var list: List<Slider>

    init {
        this.list = list
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val slideLayout: View = layoutInflater.inflate(R.layout.slide_item, null)

        val sliderImage = slideLayout.findViewById<ImageView>(R.id.slide_img)
        val sliderText = slideLayout.findViewById<TextView>(R.id.slide_title)

        sliderImage.setImageResource(list[position].image)
        sliderText.text = list[position].title

        container.addView(slideLayout)

        return slideLayout
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}