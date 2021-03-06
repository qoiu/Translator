package com.qoiu.translator.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.qoiu.translator.R
import com.qoiu.utils.OnlineLiveData
import com.qoiu.utils.ui.AlertDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        description_screen_refresh_layout.setOnRefreshListener {
            startLoadingOrShowError()
        }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setData() {
        val bundle = intent.extras
        val transcription = "[${bundle?.getString(TRANSCRIPTION_EXTRA)}]"
        description_layout_header.text = bundle?.getString(WORD_EXTRA)
        description_layout_transcription.text = transcription
        description_layout_description.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useGlideToLoadPhoto(description_layout_image, imageLink)
        }
    }


    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(
            this@DescriptionActivity,
            Observer<Boolean> {
                if (it) {
                    setData()
                } else {
                    AlertDialogFragment.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(
                        supportFragmentManager,
                        DIALOG_FRAGMENT_TAG
                    )
                    stopRefreshAnimationIfNeeded()
                }
            })
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_screen_refresh_layout.isRefreshing) {
            description_screen_refresh_layout.isRefreshing = false
        }
    }


    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.with(applicationContext)
            .load("https:$imageLink")
            .placeholder(R.drawable.ic_baseline_picture_in_picture_24).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_baseline_not_interested_24)
                }
            })
    }


    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_baseline_not_interested_24)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_picture_in_picture_24)
                    .centerCrop()
            )
            .into(imageView)
    }


    companion object {
        private const val WORD_EXTRA = "com.qoiu.translator.view.word.extra"
        private const val DIALOG_FRAGMENT_TAG = "com.qoiu.translator.view.dialog.fragment.tag"
        private const val DESCRIPTION_EXTRA = "com.qoiu.translator.view.dialog.description.extra"
        private const val TRANSCRIPTION_EXTRA =
            "com.qoiu.translator.view.dialog.transcription.extra"
        private const val URL_EXTRA = "com.qoiu.translator.view.dialog.url.extra"

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?,
            transcription: String?

        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
            putExtra(TRANSCRIPTION_EXTRA, transcription)
        }
    }
}