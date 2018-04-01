package com.mcdevz.parkpal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class PickupDialog extends DialogFragment {

    private String lastParking;
    private PickupDialogCallback callback;

    /* Bundle keys */
    public static final String LAST_PARKING = "lastparking";
    public static final String CALLBACK = "callback";

    private static final String TAG = "parkpal/pickDial";

    public void setArguments(Bundle args) {
        this.lastParking = (String) args.get(LAST_PARKING);
        this.callback = (PickupDialogCallback) args.get(CALLBACK);
    }

    public void setCallback(PickupDialogCallback callback) {
        this.callback = callback;
    }

    /**
     * Creates the pickup dialog asking the user whether they wish to pcikup their car via transit.
     * Throws an IllegalStateException if the construction argument were not set.
     * @param savedInstanceState
     * @return the dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(lastParking == null || callback == null) {
            Log.d(TAG, "lastParking: " + lastParking + " callback: " + callback);
            throw new IllegalStateException("lastParking and callback must be set before building"
                    + "the dialog. Use setArguments, setCallback to set the values.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(String.format(getString(R.string.pickup_dialog_msg), lastParking));
        builder.setTitle(R.string.pickup_dialog_name);

        /* Yes button */
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.pickupYes(lastParking);
            }
        });

        /* No button */
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.pickupNo();
            }
        });

        builder.setNeutralButton(R.string.already_picked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.pickupAlready();
            }
        });


        AlertDialog dialog = builder.create();
        return dialog;
    }
}
