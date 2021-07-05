package ir.nwise.app.ui.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.nwise.app.R

class LoadingSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    init {
        setImageDrawable(AnimatedVectorDrawableCompat.create(context, R.drawable.loading_spinner))
        (drawable as Animatable).start()
    }
}