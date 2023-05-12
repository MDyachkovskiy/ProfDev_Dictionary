package gb.com.utils.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import gb.com.R

class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        var alertDialog = getStubAlertDialog(context!!)
        val args = arguments
        if (args != null){
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog
    }

    fun getAlertDialog(context: Context, title: String?, message: String?) : AlertDialog{
        val builder = AlertDialog.Builder(context)
        var finalTitle: String? = context.getString(R.string.dialog_title_stub)
        if (!title.isNullOrBlank()) {
            finalTitle = title
        }
        builder.setTitle(finalTitle)
        if(!message.isNullOrBlank()){
            builder.setMessage(message)
        }
        builder.setCancelable(true)
        builder.setPositiveButton(R.string.dialog_button_cancel){dialog, _ -> dialog.dismiss()}
        return builder.create()
    }

    fun getStubAlertDialog(context: Context): AlertDialog {
        return getAlertDialog(context, null, null)
    }

    companion object {
        private const val TITLE_EXTRA = "title_alert_dialog"
        private const val MESSAGE_EXTRA = "message_alert_dialog"

        fun newInstance(title: String?, message: String?): AlertDialogFragment{
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, title)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

}