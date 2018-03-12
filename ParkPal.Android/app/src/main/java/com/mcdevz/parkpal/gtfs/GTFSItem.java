package com.mcdevz.parkpal.gtfs;

import java.util.List;

/**
 * Created by Kevin on 2018-02-21.
 */

public interface GTFSItem {

    Object getListItem(int i);
    int getListItemsCount();
    int getLayout();

}
