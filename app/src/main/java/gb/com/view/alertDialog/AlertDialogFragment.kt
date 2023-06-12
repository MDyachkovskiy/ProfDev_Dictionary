package gb.com.view.alertDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import gb.com.R
import java.lang.IllegalStateException

class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity ?: throw IllegalStateException(getString(R.string.error_activity_null))
        val args = arguments
        return if (args != null) {
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            getAlertDialog(context, title, message)
        } else {
            getStubAlertDialog(context)
        }
    }

    companion object {
        const val TITLE_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        const val MESSAGE_EXTRA = "0dd00b66-91c2-447d-b627-530065040905"

        fun newInstance (title: String?, message: String?): AlertDialogFragment{
            return AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_EXTRA, title)
                    putString(MESSAGE_EXTRA, message)
                }
            }
        }
    }

    private fun getStubAlertDialog(context: Context): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(getString(R.string.dialog_title_stub))
            .setMessage(getString(R.string.undefined_error))
            .setPositiveButton("OK") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    private fun getAlertDialog(context: Context, title: String?, message: String?): Dialog {
        return AlertDialog.Builder(context)
            .setTitle(title ?: getString(R.string.dialog_title_stub))
            .setMessage(message ?: getString(R.string.undefined_error))
            .setPositiveButton("OK") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}