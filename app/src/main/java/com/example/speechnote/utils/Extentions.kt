package com.example.speechnote.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}


fun View.show() {
    this.visibility = View.VISIBLE
}


fun View.gone() {
    this.visibility = View.GONE
}


fun Fragment.showMsg(msg: String) {
    view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() }
}