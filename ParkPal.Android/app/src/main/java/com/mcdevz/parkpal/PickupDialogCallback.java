package com.mcdevz.parkpal;

import android.support.annotation.IntDef;

/**
 * Callback interface for the pickup dialog.
 * @author Kevin Laframboise
 */
interface PickupDialogCallback {
    void pickupYes(String lastParking);
    void pickupNo();
}
