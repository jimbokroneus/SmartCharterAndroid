package net.smartcharter.smartcharterandroid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

/**
 * Created by James on 8/2/2015.
 */
public class Tools {


    public static void replaceFragment(int container, FragmentManager fragmentManager, Fragment newFragment, boolean addToBackStack){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(container, newFragment);
        if(addToBackStack) transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
}
