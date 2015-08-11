package net.smartcharter.smartcharterandroid;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableFlightsFragment extends Fragment {

    List<ParseObject> flights;

    CustomAdapter adapter;

    ListView flights_list_view;

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
    }

    private void getFlights(){

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


}
