package ir.nwise.app.ui.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import ir.nwise.app.R

class DialogHelper {
    companion object {
        fun showOfflineNoDataError(context: Context, retry: DialogInterface.OnClickListener) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.oops))
                .setMessage(context.getString(R.string.no_internet_error_title))
                .setPositiveButton(context.getString(R.string.retry), retry)
                .setNegativeButton(context.getString(R.string.ok)) { _, _ -> }
                .setCancelable(false)
                .show()
        }
    }
}