package net.smartcharter.smartcharterandroid;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends Activity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //do initial fragment load
        Tools.replaceFragment(R.id.main_fragment_container, getFragmentManager(), new HomeFragment(), true);

        setUpNavDrawer();

    }

    private void setUpNavDrawer(){

        //set up nav Drawer
        String[] titles = new String[]{"Home", "Available Flights", "Available Charters", "Settings", "About Us"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, titles));

        // Set the list's click listener
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        //move to home
                        Tools.replaceFragment(R.id.main_fragment_container, getFragmentManager(), new HomeFragment(), true);
                        break;

                    case 1:
                        //move to available flights
                        Tools.replaceFragment(R.id.main_fragment_container, getFragmentManager(), new AvailableFlightsFragment(), true);
                        break;

                    case 2:
                        //move to settings

                        break;


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
