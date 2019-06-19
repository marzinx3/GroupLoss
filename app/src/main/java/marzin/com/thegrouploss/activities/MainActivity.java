 package marzin.com.thegrouploss.activities;

 import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import marzin.com.thegrouploss.R;
import marzin.com.thegrouploss.fragments.FriendsFragment;
import marzin.com.thegrouploss.fragments.HomeFragment;
import marzin.com.thegrouploss.fragments.MessagesFragment;
import marzin.com.thegrouploss.fragments.ProfileFragment;
import marzin.com.thegrouploss.fragments.SearchFragment;

 public class MainActivity extends FragmentActivity implements
         ProfileFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,
         HomeFragment.OnFragmentInteractionListener,MessagesFragment.OnFragmentInteractionListener,
         SearchFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // verify activity is using fragment or not
        if(findViewById(R.id.fragment_Container)!=null){

            // if being restored from previous state
            if (savedInstanceState !=null){
                return;
            }
            HomeFragment homeFragment= new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            homeFragment.setArguments(getIntent().getExtras());

            // first loaded fragment is home screen once activity launches
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_Container,homeFragment).commit();

        }

        BottomBar bottomBar= (BottomBar)findViewById(R.id.bottomBar);

        /**
         *
         */
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {

                    case R.id.tab_account:
                        ProfileFragment profileFragment = new ProfileFragment();
                        Bundle profileArgs = new Bundle();
                        profileArgs.putString("message", "hello world");
                        profileArgs.putInt("number", 10);
                        fragmentStarter(profileFragment,profileArgs);
                        break;
                    case R.id.tab_friends:
                        FriendsFragment friendsFragment = new FriendsFragment();
                        Bundle friendArgs = new Bundle();
                        friendArgs.putString("message", "hello world");
                        friendArgs.putInt("number", 10);
                        fragmentStarter(friendsFragment,friendArgs);
                        break;
                    case R.id.tab_home:
                        HomeFragment homeFragment = new HomeFragment();
                        Bundle homeArgs = new Bundle();
                        homeArgs.putString("message", "hello world");
                        homeArgs.putInt("number", 10);
                        fragmentStarter(homeFragment,homeArgs);
                        break;
                    case R.id.tab_messages:
                        MessagesFragment messagesFragment = new MessagesFragment();
                        Bundle messagesArgs = new Bundle();
                        messagesArgs.putString("message", "hello world");
                        messagesArgs.putInt("number", 10);
                        fragmentStarter(messagesFragment,messagesArgs);
                         break;
                    case R.id.tab_search:
                        SearchFragment searchFragment = new SearchFragment();
                        Bundle searchArgs = new Bundle();
                        searchArgs.putString("message", "hello world");
                        searchArgs.putInt("number", 10);
                        fragmentStarter(searchFragment,searchArgs);
                        break;
                    default:
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            Log.e("bottomButton","Failed to get bottom tab pressed "+ e);

                        }
                        break;
                }
            }
        });

    }

     /**
      *
      * @param fragment
      * @param args
      */
    public void fragmentStarter(Fragment fragment,@Nullable Bundle args){
        if (args!=null) {
            fragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_Container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_Container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
     @Override
     public void onFragmentInteraction(Uri uri) {

     }
 }
