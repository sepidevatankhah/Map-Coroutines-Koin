package ir.nwise.app.ui.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.nwise.app.R
import ir.nwise.app.databinding.ViewErrorBinding
import ir.nwise.app.ui.error.ErrorType
import ir.nwise.app.ui.utils.show
/**
 * Generic error view that populates its content depending
 * on the supplied instance of [ErrorType].
 */
class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private var errorType: ErrorType = ErrorType.General
    var binding: ViewErrorBinding = ViewErrorBinding.inflate(LayoutInflater.from(context), this,true)

    fun show(errorType: ErrorType) {
        this.show()
        this.errorType = errorType
        binding.apply {
            with(errorType)
            {
                errorImageView.setImageResource(icon)
                errorMessage.text = resources.getString(message)
                errorTitle.text = resources.getString(title)
            }
        }
    }

    fun setButtonListener(onClick: () -> Unit) {
        binding.errorButton.setOnClickListener { onClick.invoke() }
    }
}