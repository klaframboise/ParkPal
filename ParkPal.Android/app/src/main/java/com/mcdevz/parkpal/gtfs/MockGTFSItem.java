package com.mcdevz.parkpal.gtfs;

import com.mcdevz.parkpal.R;

/**
 * Created by Kevin on 2018-02-21.
 */

public class MockGTFSItem implements GTFSItem {

    static String[] names = {"CIT Roussillon", "STM", "RTL", "CIT Richelain", "STL"};

    @Override
    public String getListItem(int i) {
        return names[i];
    }

    @Override
    public int getListItemsCount() {
        return names.length;
    }

    @Override
    public int getLayout() {
        return R.layout.list_item;
    }
}
