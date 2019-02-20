package net.telepathix.petbase.helper

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

fun showSimpleAlert(context: Context, @StringRes stringId: Int) {
    AlertDialog.Builder(context)
        .setMessage(stringId)
        .setCancelable(false)
        .setNeutralButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
        .create()
        .show()
}
