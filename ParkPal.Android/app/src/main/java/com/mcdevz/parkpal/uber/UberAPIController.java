package com.mcdevz.parkpal.uber;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Kevin on 2018-02-14.
 */
public class UberAPIController {

    private final String DEEP_LINK;

    /**
     * Constructs a Uber API Controller with the given client id.
     * @param clientId
     */
    public UberAPIController(String clientId) {
        DEEP_LINK = "uber://?client_id=" + clientId;
        Log.d("parkpal", "Controller constructed");
    }

    /**
     * Opens the uber app to offer a ride to the user from their current location to the set
     * destination. Throws an exception if the user's location is not available.
     * @param context context calling the method
     * @param destination LatLng object representing the destination
     * @throws RuntimeException if uber could not be launched
     */
    public void openUberApp(Context context, LatLng destination) throws RuntimeException {

        // Build URI and intent
        String deepLink = String.format("%saction=setPickup&pickup=my_location&dropoff[latitude]=%f&dropoff[longitude]=%f",
                this.DEEP_LINK, destination.latitude, destination.longitude);
        Uri uri = Uri.parse(deepLink);
        Intent openUberIntent = new Intent(Intent.ACTION_VIEW, uri);

        // try to launch uber
        try {
            verifyAndStart(context, openUberIntent);
        } catch (RuntimeException e) {
            throw e;
        }

    }

    /**
     * Opens the uber app to offer a ride to the user from their current location to the set
     * destination. Throws an exception if the user's location is not available.
     * @param context context calling the method
     * @param origin LatLng object representing the origin
     * @param destination LatLng object representing the destination
     * @throws RuntimeException
     */
    public void openUberApp(Context context, LatLng origin, LatLng destination) throws RuntimeException {

        // Build URI and intent
        String deepLink = String.format("%s&action=setPickup&pickup[latitude]=%f&pickup[longitude]=%f&dropoff[latitude]=%f&dropoff[longitude]=%f",
                this.DEEP_LINK, origin.latitude, origin.longitude, destination.latitude,
                destination.longitude);
        Uri uri = Uri.parse(deepLink);
        Intent openUberIntent = new Intent(Intent.ACTION_VIEW, uri);
        Log.d("parkpal", "Starting Uber with: " + deepLink);

        // try to launch uber
        try {
            verifyAndStart(context, openUberIntent);
        } catch (RuntimeException e) {
            throw e;
        }

    }

    /**
     * Verifies if the given intent resolves in the given context and launches the attached
     * activity.
     * @param context
     * @param intent
     */
    private boolean verifyAndStart(Context context, Intent intent) throws RuntimeException {

        // Verify it resolves
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            context.startActivity(intent);
            return true;
        }
        else {
            throw new RuntimeException("An error occurred while launching Uber.");
        }
    }
}
