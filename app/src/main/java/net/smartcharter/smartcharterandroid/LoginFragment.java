package net.smartcharter.smartcharterandroid;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText username_entry;
    EditText password_entry;
    Button login_button;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFields(view);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(username_entry.getText().toString(), password_entry.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if(e == null){
                            if(parseUser != null){
                                MainActivity.currentUser = parseUser;
                                Tools.replaceFragment(R.id.main_fragment_container, getFragmentManager(), new HomeFragment(), true);
                            }
                            else{
                                Toast.makeText(getActivity(), "Not Logged In", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void initializeFields(View view){
        username_entry = (EditText) view.findViewById(R.id.username_entry);
        password_entry = (EditText) view.findViewById(R.id.password_entry);

        login_button = (Button) view.findViewById(R.id.login_button);
    }
}
