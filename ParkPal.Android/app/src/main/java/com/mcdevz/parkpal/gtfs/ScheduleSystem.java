package com.mcdevz.parkpal.gtfs;

import android.content.Context;

import com.kevinlaframboise.gtfsparser.builder.GTFSDirectory;
import com.kevinlaframboise.gtfsparser.serialization.GTFSPersistence;

import java.io.File;


/**
 * Created by Kevin on 2018-02-21.
 */

public class ScheduleSystem {

    public static final int NUM_OF_AGENCIES = 17;
    public static final String FILENAME = "gtfs.json.zip";

    private Context context;

    public ScheduleSystem(Context context) {
        this.context = context;

        buildGtfsModel();
    }

    private void buildGtfsModel() {
        File file = new File(context.getFilesDir(), FILENAME);
        if(file.exists()) {
            GTFSPersistence.deserializeJsonIoAndGzip(file);
        }
        else {
            GTFSDownloadHelper downloadHelper = new GTFSDownloadHelper(context, this, true);
            downloadHelper.downloadGTFS();
        }
    }

    public void parseFeeds(File gtfsDir) {
        GTFSDirectory gtfsDirectory = new GTFSDirectory(gtfsDir.getParentFile(), gtfsDir.getName());
        gtfsDirectory.parseGTFS();
    }
}
