package net.smartcharter.smartcharterandroid;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableFlightsFragment extends Fragment {

    List<ParseObject> flights, myFlights;

    CustomAdapter adapter;

    ListView flights_list_view;

    TextView page_title;

    public AvailableFlightsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_flights, container, false);

        //TODO tabbed viewpager setup for flights/charters

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFields(view);

        setAdapter();

        getFlights();

    }

    private void setAdapter(){

        adapter = new CustomAdapter(getActivity(), flights);

        flights_list_view.setAdapter(adapter);

    }

    private void initializeFields(View view){

        flights = new ArrayList<>();
        flights_list_view = (ListView) view.findViewById(R.id.list_of_flights);
        page_title = (TextView) view.findViewById(R.id.page_title);

        flights_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDialog(view);

            }
        });

    }

    private void showDialog(final View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title
        alertDialogBuilder.setTitle("Reserve Flight?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click reserve to reserve 1 ticket on this flight!")
                .setCancelable(true)
                .setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {

                        //RESERVE THE FLIGHT
                        //TODO move this to method, once reservation class is finalized

                        CustomAdapter.ViewHolder vh = (CustomAdapter.ViewHolder) view.getTag();
                        ParseObject reservation = new ParseObject("ReservedFlights");

                        reservation.put("flight", vh.flightId);
                        reservation.put("passenger", ParseUser.getCurrentUser().getObjectId());

                        reservation.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    dialog.cancel();
                                    Toast.makeText(getActivity(), "Flight Reserved", Toast.LENGTH_SHORT).show();
                                } else {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "Flight Not Reserved", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void reserveFlight(View view){

    }

    private void getFlights(){
        flights = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Flights");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if(e == null){
                    if(!list.isEmpty()){

                        flights.addAll(list);

                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getActivity(), "No Flights Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void getMyFlights(){
        flights = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Flights");
        query.whereEqualTo("passenger", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if(e == null){
                    if(!list.isEmpty()){

                        myFlights.addAll(list);

                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getActivity(), "No Flights Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


}
