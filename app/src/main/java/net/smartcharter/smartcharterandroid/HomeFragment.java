package net.smartcharter.smartcharterandroid;


import android.os.Bundle;
import android.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CustomAdapter2 adapter;
    List<ParseObject> flights;

    ListView myFlights;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFields(view);
        setAdapter();
        getMyFlights();
    }

    private void setAdapter(){

        adapter = new CustomAdapter2(getActivity(), flights, CustomAdapter.RESERVATIONS);

        myFlights.setAdapter(adapter);

    }

    private void initializeFields(View view){
        flights = new ArrayList<>();
        myFlights = (ListView) view.findViewById(R.id.my_flights_list_view);


    }

    private void getMyFlights(){
        flights = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ReservedFlights");
        query.whereEqualTo("passenger", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (!list.isEmpty()) {

                        flights.addAll(list);

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "No Flights Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
