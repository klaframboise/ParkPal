package com.mcdevz.parkpal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mcdevz.parkpal.gtfs.GTFSItem;
import com.mcdevz.parkpal.gtfs.MockGTFSItem;

public class ScheduleBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_browser);

        ListView lv = (ListView)findViewById(R.id.gtfsListView);
        GTFSListAdapter gtfsAdapter = new GTFSListAdapter();
        gtfsAdapter.setItemToDisplay(new MockGTFSItem());
        lv.setAdapter(gtfsAdapter);
    }

    /**
     * List adapter
     * @author Kevin Laframboise
     */
    class GTFSListAdapter extends BaseAdapter {

        private GTFSItem itemDisplayed;

        @Override
        public int getCount() {
            return itemDisplayed.getListItemsCount();
        }

        @Override
        public Object getItem(int i) {
            return itemDisplayed.getListItem(i);
        }

        @Override
        public long getItemId(int i) {
            return itemDisplayed.getListItem(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null) {
                view = getLayoutInflater().inflate(itemDisplayed.getLayout(), viewGroup, false);
            }

            ((TextView)findViewById(R.id.agencyName)).setText((String)getItem(i));
            return view;
        }

        public void setItemToDisplay(GTFSItem item) {
            this.itemDisplayed = item;
        }

    }
}
