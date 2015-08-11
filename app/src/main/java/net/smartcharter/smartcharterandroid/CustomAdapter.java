package net.smartcharter.smartcharterandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by James on 8/10/2015.
 */
public class CustomAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<ParseObject> flights;

        public CustomAdapter(Context context, List<ParseObject> flights) {
            mInflater = LayoutInflater.from(context);
            this.flights = flights;
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
            View view;
            ViewHolder holder;
            if(convertView == null) {
                view = mInflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.flight_title);
                holder.route = (TextView) view.findViewById(R.id.route);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.price = (TextView) view.findViewById(R.id.price);
                holder.seats = (TextView) view.findViewById(R.id.seats);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder)view.getTag();
            }

            holder.index = position;
            ParseObject curr = flights.get(position);

            holder.flightId = curr.getObjectId();
            holder.title.setText(curr.getString("Title"));
            holder.route.setText(getRoute(curr));
            holder.time.setText(getTime(curr));
            holder.seats.setText(curr.getNumber("availableSeats").toString() + " seats available");
            holder.price.setText("$" + curr.getNumber("Price").toString());

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
