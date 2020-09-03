package com.example.casestudy

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.casestudy.model.Product
import com.example.casestudy.model.Social
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var product: Product
    private lateinit var social: Social
    private val gson = Gson()
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveData()
        initProductView()
        initSocialView()
        setTimer()
    }

    private fun retrieveData() {
        val jsonProduct = getJsonDataFromAsset(applicationContext, "product.json")
        product = gson.fromJson(jsonProduct, Product::class.java)
        val jsonSocial = getJsonDataFromAsset(applicationContext, "social.json")
        social = gson.fromJson(jsonSocial, Social::class.java)
    }

    private fun initProductView() {
        Glide.with(applicationContext).load(product.image).into(image_product)
        text_product_name.text = product.name
        text_product_type.text = product.desc
        text_price.text =
            getString(R.string.price, product.price.value.toString(), product.price.currency)
    }

    private fun initSocialView() {
        text_like.text = social.likeCount.toString()
        text_rating.text = social.commentCounts.averageRating.toString()
        rating_bar.rating = social.commentCounts.averageRating.toFloat()
        text_comments.text = getString(
            R.string.comments_count,
            (social.commentCounts.anonymousCommentsCount + social.commentCounts.memberCommentsCount).toString()
        )
    }

    private fun setTimer() {
        timer = object : CountDownTimer(20000, 1000) {
            override fun onFinish() {
                retrieveData()
                initSocialView()
                timer?.start()
            }

            override fun onTick(milisUntilFinised: Long) {
            }
        }.start()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
