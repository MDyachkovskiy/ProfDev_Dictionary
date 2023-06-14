package gb.com

import gb.com.view.alertDialog.AlertDialogFragment
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AlertDialogFragmentTest {

    @Test
    fun newInstance_Sets_Title_And_Message_In_Arguments() {
        val title = "Title"
        val message = "Message"
        val fragment = AlertDialogFragment.newInstance(title, message)

        assertEquals(title, fragment.arguments?.getString(AlertDialogFragment.TITLE_EXTRA))
        assertEquals(message, fragment.arguments?.getString(AlertDialogFragment.MESSAGE_EXTRA))
    }

}