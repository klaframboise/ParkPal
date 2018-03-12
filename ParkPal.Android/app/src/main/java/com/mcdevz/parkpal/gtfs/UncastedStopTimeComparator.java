package com.mcdevz.parkpal.gtfs;

import com.kevinlaframboise.gtfsparser.model.StopTime;

import java.util.Comparator;

/**
 * Created by Kevin on 2018-03-11.
 */

public class UncastedStopTimeComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        StopTime t1 = (StopTime) o1;
        StopTime t2 = (StopTime) o2;
        if(t1.getSequence() < t2.getSequence()) return -1;
        else if(t1.getSequence() == t2.getSequence()) return 0;
        else return 1;
    }
}
