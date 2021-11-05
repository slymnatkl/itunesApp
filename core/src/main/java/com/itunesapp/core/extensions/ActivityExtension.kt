package com.itunesapp.core.extensions

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.google.android.material.snackbar.Snackbar

//<editor-fold desc="SnackBar">

private const val DURATION_CONSTANT : Int = 50
private const val MIN_DURATION      : Int = 2000 // 2 second

@SuppressLint("WrongConstant")
fun Activity.showMessage(message: String) {

    val duration = DURATION_CONSTANT * message.length

    Snackbar.make(
        findViewById(R.id.content),
        message,
        if (duration <= MIN_DURATION) MIN_DURATION else duration
    ).show()
}

//</editor-fold>

//<editor-fold desc="Progress Dialog">

fun Activity.getProgressDialog(): Dialog{

    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(com.itunesapp.core.R.layout.progress_dialog)

    return dialog
}

//</editor-fold>