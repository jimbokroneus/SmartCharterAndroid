package net.smartcharter.smartcharterandroid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;


public class PostFlight extends Fragment {

    EditText title, description, dCity, dDate, dTime, aCity, aDate, aTime;

    Spinner dSpinner, aSpinner;

    Button post_button;

    Date departure, arrival;


    public PostFlight() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_flight, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFields(view);
    }

    private void initializeFields(View view){
        title = (EditText) view.findViewById(R.id.title_entry);
        description = (EditText) view.findViewById(R.id.details_entry);
        dCity = (EditText) view.findViewById(R.id.departure_city_entry);
        dDate = (EditText) view.findViewById(R.id.departure_date);
        dTime = (EditText) view.findViewById(R.id.departure_time);
        aCity = (EditText) view.findViewById(R.id.arrival_city_entry);
        aDate = (EditText) view.findViewById(R.id.arrival_date);
        aTime = (EditText) view.findViewById(R.id.arrival_time);



        dSpinner = (Spinner) view.findViewById(R.id.departure_spinner);
        aSpinner = (Spinner) view.findViewById(R.id.arrival_spinner);

        dSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.states, android.R.layout.simple_spinner_item));
        aSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.states, android.R.layout.simple_spinner_item));

        post_button = (Button) view.findViewById(R.id.post_button);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePost();
            }
        });

        dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        dDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        aDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        aDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        dTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dTime.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        aTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        aTime.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void validatePost(){

        String sTitle = title.getText().toString();
        String sDetails = description.getText().toString();
        String s_dCity = dCity.getText().toString();
        String s_aCity = aCity.getText().toString();
        String s_dDate = dDate.getText().toString();
        String s_dTime = dTime.getText().toString();
        String s_aDate = aDate.getText().toString();
        String s_aTime = aTime.getText().toString();

        String dState = dSpinner.getSelectedItem().toString();
        String aState = dSpinner.getSelectedItem().toString();

        Date dDate = new Date();
        Date aDate = new Date();

        //validate all entries

        //build parse object and save it
        ParseObject parseObject = new ParseObject("Flights");

        parseObject.put("Title", sTitle);
        parseObject.put("Description", sDetails);
        parseObject.put("departureCity", s_dCity);
        parseObject.put("departureState", dState);
        parseObject.put("arrivalCity", s_aCity);
        parseObject.put("arrivalState", aState);
        parseObject.put("Departure" , dDate);
        parseObject.put("Arrival", aDate);
        parseObject.put("availableSeats", 5);
        parseObject.put("Price", 100);


        //save flight to parse
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(getActivity(), "Flight Posted", Toast.LENGTH_SHORT).show();
                }
                else{
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
