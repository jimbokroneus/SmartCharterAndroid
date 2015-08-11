package net.smartcharter.smartcharterandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by James on 8/11/2015.
 */
public class CustomAdapter2 extends BaseAdapter {

        public static final int FLIGHTS = 0;
        public static final int RESERVATIONS = 1;
        private int mode;
        private LayoutInflater mInflater;
        private List<ParseObject> flights;

        public CustomAdapter2(Context context, List<ParseObject> flights, int mode) {
            mInflater = LayoutInflater.from(context);
            this.flights = flights;
            this.mode = mode;
        }

        @Override
        public int getCount() {
            return flights.size();
        }

        @Override
        public Object getItem(int position) {
            return flights.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if(convertView == null) {
                view = mInflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.flight_title);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder)view.getTag();
            }

            holder.index = position;
            ParseObject curr1 = flights.get(position);

            holder.flightId = curr1.getObjectId();
            holder.title.setText(curr1.getString("Title"));

            return view;

        }

        public class ViewHolder {
            public TextView title, time, route, price, seats;
            public int index;
            public String flightId;
        }

        private String getRoute(ParseObject curr){

            String message = "";
            message += curr.getString("departureCity") + ", " + curr.getString("departureState");
            message += " to ";
            message += curr.getString("arrivalCity") + ", " + curr.getString("arrivalState");

            return message;
        }

        private String getTime(ParseObject curr){

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            String fDepart = formatter.format(curr.getDate("Departure"));
            String fArrive = formatter.format(curr.getDate("Arrival"));
            String message = "Depart: " + fDepart + "\nArrival: " + fArrive;

            return message;
        }


}
