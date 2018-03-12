package com.mcdevz.parkpal.gtfs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.kevinlaframboise.gtfsparser.builder.GTFSDirectory;
import com.kevinlaframboise.gtfsparser.listener.ParsingProgressListener;
import com.kevinlaframboise.gtfsparser.model.Agency;
import com.kevinlaframboise.gtfsparser.model.Route;
import com.kevinlaframboise.gtfsparser.model.Stop;
import com.kevinlaframboise.gtfsparser.model.StopTime;
import com.kevinlaframboise.gtfsparser.model.Trip;
import com.kevinlaframboise.gtfsparser.serialization.GTFSPersistence;
import com.mcdevz.parkpal.ScheduleBrowser;

import java.io.File;
import java.io.Serializable;


/**
 * Created by Kevin on 2018-02-21.
 */

public class ScheduleSystem {

    public static final int NUM_OF_AGENCIES = 14;
    private static final String TAG = "parkpal/Schedule";
    public static final String FILENAME = "gtfs.json.zip";
    public static final String ACTION_UPDATE_PROGRESS = "action-update-progress";

    private ScheduleBrowser context;

    public ScheduleSystem(ScheduleBrowser context) {
        this.context = context;

        buildGtfsModel();
    }

    private void buildGtfsModel() {
        File file = new File(context.getFilesDir(), "gtfs");
        if(!ScheduleSystem.gtfsDownloadNeeded(context)) parseFeeds(file);
        else downloadFeeds(context);
    }

    public static boolean gtfsDownloadNeeded(Context context) {
        File file = new File(context.getFilesDir(), "gtfs");
        if(file.exists()) {
            if(file.listFiles().length == NUM_OF_AGENCIES) return false;
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    public static void downloadFeeds(Context context) {
        GTFSDownloadHelper downloadHelper = new GTFSDownloadHelper(context, true);
        downloadHelper.downloadGTFS();
    }

    public void parseFeeds(File gtfsDir) {
        GTFSDirectory gtfsDirectory = new GTFSDirectory(gtfsDir.getParentFile(), gtfsDir.getName());
        new ParseGtfsTask().execute(gtfsDirectory);
    }

    private class ParseGtfsTask extends AsyncTask<GTFSDirectory, Integer, Integer> {

        @Override
        protected Integer doInBackground(GTFSDirectory... gtfsDirectories) {
            int count = gtfsDirectories.length;
            long start = System.currentTimeMillis();
            MyProgressListener listener = new MyProgressListener();
            for(int i = 0; i < count; i++) {
                Log.d(TAG,"Parsing gtfs directory " + i);
                gtfsDirectories[i].parseGTFS(listener);
                publishProgress((int)(100.0*(i+1.0)/count));
            }
            long end = System.currentTimeMillis();
            Log.d(TAG, "Parse complete. Time taken: " + (end-start)/1000.0 + " secs");
            return count;
        }

        /**
         * Prints success message to debug
         * @param result the number of entries extracted
         */
        @Override
        protected void onPostExecute(Integer result) {
            //TODO serialize
            deleteFiles();
            Log.d(TAG, "Finished parsing agencies");
//            Log.d(TAG, "Starting serialization");
//            long start = System.currentTimeMillis();
//            GTFSPersistence.serializeWithJsonIoAndGZip(new File(context.getFilesDir(), FILENAME));
//            long end = System.currentTimeMillis();
//            Log.d(TAG, "Finished serializing. Time taken: " + (end-start)/1000.0 + " secs");
        }

        private void deleteFiles() {
            for(File file : context.getFilesDir().listFiles()) {
                if(file.getName().matches("^gtfs_[a-zA-Z0-9]+_zip$")) {
                    Log.d(TAG, "Deleting: " + file.getName());
                    file.delete();
                }
            }
        }
    }

    private class MyProgressListener implements ParsingProgressListener {

        @Override
        public void onAgencyCreate(Agency agency) {
            Log.d(TAG, "Created agency: " + agency.getName());
            Intent progress = new Intent(ACTION_UPDATE_PROGRESS);
            Log.d(TAG, "Sending local broadcast.");
            LocalBroadcastManager.getInstance(context).sendBroadcast(progress);
        }

        @Override
        public void onRouteCreate(Route route) {

        }

        @Override
        public void onTripCreate(Trip trip) {

        }

        @Override
        public void onStopCreate(Stop stop) {

        }

        @Override
        public void onStopTimeCreate(StopTime stopTime) {

        }
    }
}
