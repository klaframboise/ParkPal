package com.mcdevz.parkpal.gtfs;

/**
 * Created by Kevin on 2018-03-06.
 */

public interface ExtractionCallback<T> {
    interface Progress {
        int ERROR = -1;
        int ENTRY_EXTRACTED = 0;
        int FILE_EXTRACTED = 1;
    }
}
