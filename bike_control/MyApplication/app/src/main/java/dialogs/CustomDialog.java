package dialogs;

import android.app.Dialog;
import android.app.AlertDialog;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;


import com.bike_control.R;
import java.util.Arrays;

public class CustomDialog extends DialogFragment {

    public String status = "";

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if( getArguments() != null) {
            status = getArguments().getString("Success");

            if (status == null ) {
                status = getArguments().getString("Error");
            }
        }
        else { String status = "0"; }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_fragment);
        return builder
                .setTitle("Response is OK")
                .setMessage("Status: " + status)
                .setPositiveButton("OK", null)
                .create();

    }

}
//krtrz29x19w002529