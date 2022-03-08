package com.ayizor.finterest.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.ayizor.finterest.R

class Utils {

    fun alertDialog(
        context: Context?,
        title: String?,
        description: String?,
        dialogAction: OnAlertDialogAction
    ) {
        val alertDialog = AlertDialog.Builder(
            context!!
        ).create()
        val view1: View = LayoutInflater.from(context).inflate(
            R.layout.alert_dialog,
            alertDialog.findViewById<View>(R.id.alert_dialog_card) as CardView?
        )
        alertDialog.setView(view1)
        (view1.findViewById<View>(R.id.alert_dialog_big_title) as TextView).text = title
        (view1.findViewById<View>(R.id.alert_dialog_title) as TextView).text =
            description
        (view1.findViewById<View>(R.id.alert_dialog_cencel_text) as TextView).setText(R.string.cencel)
        (view1.findViewById<View>(R.id.alert_dialog_ok_text) as TextView).setText(R.string.ok)
        view1.findViewById<View>(R.id.alert_dialog_cencel).setOnClickListener { v12: View? ->
            dialogAction.alertActionListener(false)
            alertDialog.dismiss()
        }
        view1.findViewById<View>(R.id.alert_dialog_ok).setOnClickListener { v1: View? ->
            dialogAction.alertActionListener(true)
            alertDialog.dismiss()
        }
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

    interface OnAlertDialogAction {
        fun alertActionListener(action: Boolean)
    }

    fun fireToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
