package com.mcdevz.parkpal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kevinlaframboise.gtfsparser.model.Agency;
import com.kevinlaframboise.gtfsparser.model.GTFSModel;
import com.kevinlaframboise.gtfsparser.model.Route;
import com.kevinlaframboise.gtfsparser.model.Stop;
import com.kevinlaframboise.gtfsparser.model.StopTime;
import com.kevinlaframboise.gtfsparser.model.Trip;
import com.mcdevz.parkpal.gtfs.ScheduleSystem;
import com.mcdevz.parkpal.gtfs.StopTimeComparator;
import com.mcdevz.parkpal.gtfs.UncastedStopTimeComparator;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ScheduleBrowser extends AppCompatActivity {

    private static final String TAG = "parkpal/Schedule";
    private GTFSListAdapter gtfsAdapter;
    private BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_browser);
        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ScheduleSystem.ACTION_UPDATE_PROGRESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(br, filter);
        ScheduleSystem scheduleSystem = new ScheduleSystem(this);

        ListView lv = (ListView)findViewById(R.id.gtfsListView);
        lv.setOnItemClickListener(new MyItemClickListener());
        gtfsAdapter = new GTFSListAdapter();
        lv.setAdapter(gtfsAdapter);
    }

    public void updateList() {
        gtfsAdapter.updateList();
    }

    public void onBackClick(View view) {
        gtfsAdapter.displayParent();
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Object gtfsObj = gtfsAdapter.getItem(i);

            if(gtfsObj instanceof Agency) {
                gtfsAdapter.setDisplayedList(((Agency) gtfsObj).getRoutes(), gtfsAdapter.displayedList);
            }
            else if(gtfsObj instanceof Route) {
                gtfsAdapter.setDisplayedList(((Route) gtfsObj).getTrips(), gtfsAdapter.displayedList);
            }
            else if(gtfsObj instanceof Trip) {
                gtfsAdapter.setDisplayedList(((Trip) gtfsObj).getStopTimes(), gtfsAdapter.displayedList);
            }
            else if(gtfsObj instanceof  StopTime) {
                //lowest level, do nothing
            }
        }
    }
    /**
     * List adapter
     * @author Kevin Laframboise
     */
    private class GTFSListAdapter extends BaseAdapter {

        List displayedList;
        Stack<List> parents;
        GTFSModel gtfs;

        public GTFSListAdapter() {
            super();
            gtfs = GTFSModel.getInstance();
            parents = new Stack<>();
            displayedList = new ArrayList<>();
            setDisplayedList(gtfs.getAgencies(), null);
        }

        @Override
        public int getCount() {
            return displayedList.size();
        }

        @Override
        public Object getItem(int i) {

            return displayedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup container) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }

            ((TextView) convertView.findViewById(R.id.listTextView)).setText(getText(getItem(i)));
            return convertView;
        }

        public void setDisplayedList(List displayedList, List parent) {
            this.displayedList = new ArrayList();
            if(parent != null) parents.push(parent);
            // sort by stop sequence if list of stop times
            copyList(displayedList, this.displayedList);
            sortStopTimes();
            notifyDataSetChanged();
        }

        public void displayParent() {
            if(parents.empty()) {
                setDisplayedList(gtfs.getAgencies(), null);
            }
            else {
                setDisplayedList(parents.pop(), null);
            }
        }

        public void updateList() {
            Log.d(TAG, "Requesting list update. List size now: " + displayedList.size());
            setDisplayedList(gtfs.getAgencies(), null);
            notifyDataSetChanged();
        }

        private void copyList(List from, List to) {
            for(Object item : from) {
                to.add(item);
            }
        }

        private void sortStopTimes() {
            if(displayedList.size() > 0 && displayedList.get(0) instanceof StopTime) {
                Collections.sort(displayedList, new UncastedStopTimeComparator());
            }
        }

        private String getText(Object gtfsObj) {
            if(gtfsObj instanceof Agency) {
                return ((Agency) gtfsObj).getName();
            }
            else if(gtfsObj instanceof Route) {
                return ((Route) gtfsObj).getLongName();
            }
            else if(gtfsObj instanceof Trip) {
                return getTripText((Trip)gtfsObj);
            }
            else if(gtfsObj instanceof  StopTime) {
                return getStopTimeText((StopTime) gtfsObj);
            }
            return "";
        }

        private String getStopTimeText(StopTime stopTime) {
            String result = "";
            Stop associatedStop = stopTime.getStop();
            if(associatedStop.getName() != null) result += stopTime.getStop().getName();
            else result += associatedStop.getCode();
            Time departureTime = new Time(stopTime.getDepartureTime().getTime());
            result += " - " + departureTime.toString();
            return result;
        }

        private String getTripText(Trip trip) {
            String result = "";
            if(trip.getHeadsign() != null) result += trip.getHeadsign();
            Time departureTime = new Time(getFirstStopTime(trip).getDepartureTime().getTime());
            result += " - " + departureTime.toString();
            return result;
        }

        private StopTime getFirstStopTime(Trip trip) {
            List<StopTime> stops = new ArrayList<>(trip.getStopTimes());
            Collections.sort(stops, new StopTimeComparator());
            return stops.get(0);
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Received local broadcast.");
            if(intent.getAction().equals(ScheduleSystem.ACTION_UPDATE_PROGRESS)) {
                Log.d(TAG, "Intent matches, update list");
                updateList();
            }
        }
    }

}
