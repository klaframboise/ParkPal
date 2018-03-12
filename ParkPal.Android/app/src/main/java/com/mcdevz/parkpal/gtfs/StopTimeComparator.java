package com.mcdevz.parkpal.gtfs;

import com.kevinlaframboise.gtfsparser.model.StopTime;

import java.util.Comparator;

/**
 * Created by Kevin on 2018-03-11.
 */

public class StopTimeComparator implements Comparator<StopTime> {

    @Override
    public int compare(StopTime stopTime, StopTime t1) {
        if(stopTime.getSequence() < t1.getSequence()) return -1;
        else if(stopTime.getSequence() == t1.getSequence()) return 0;
        else return 1;
    }
}
