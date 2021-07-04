package ir.nwise.app.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ir.nwise.app.R

/**
 * This function fetches images from the URL and turns it to the bitmap
 * It is needed as the MarkerOptions only accepts bitmaps as input
 *
 * @param url image's url address
 * @param showOnMap a lambda to show fetched bitmap as a marker
 */
fun getBitmap(context: Context, url: String?, showOnMap: (Bitmap) -> Unit) {
    val glideRequestManager = Glide.with(context)
    glideRequestManager
        .asBitmap()
        .load(url)
        .apply(
            RequestOptions()
                .override(350, 170)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_car_svg)
        )
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                showOnMap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun ImageView.loadUrl(url: String?) {
    val glideRequestManager = Glide.with(context)
    glideRequestManager
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_car_svg)
        )
        .into(this)
}