package fv017739.PasswordProtect;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Class to manage all fragments in project
 */
public class HomeScreen extends AppCompatActivity {

    TextView mTitle; //Define title

    /**
     * Method to handle the container, bottom navigation and toolbar for app
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen); //Set layout to home screen XML
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //Get window manager


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); //Find toolabr ID in XML
        mTitle = (TextView) toolbar.findViewById(R.id.custom_title); //Find title ID in XML

        setSupportActionBar(toolbar); //Set the tool bar as support action bar to display

        //Find navigation bar ID and make it listen to clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        //Set fragment container and define first fragment as home fragment which will occur on launch
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        //Find floating action button ID in XML
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

        //Make floating action button listion to clicks
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new HomeFragment(); //launch home fragment button on click
                mTitle.setText("Home"); //Set title as home
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit(); //Commit process
            }
        });


    }

    /**
     * Method to add options menu to toolbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu); //Inflate the menu called menu_items and display on tool bar
        return true;
    }

    /**
     * Method to hand what happens on options menu click
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_icon: //Define options menu icon in this case a + icon
                Fragment fragment = new AddPasswordFragment(); //When user clicks icon take them to Addpassword fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit(); //Commit process
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Bottom navigation On click listener to take user to different fragments based on icon
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    //On lock icon click take user to vault fragment and set text to Vault
                    switch (menuItem.getItemId()) {
                        case R.id.lock:
                            selectedFragment = new VaultFragment();
                            mTitle.setText("Vault");

                            break;
                        //On tools icon click take user to Tools fragment and set text to Tools
                        case R.id.tools:
                            selectedFragment = new ToolsFragment();
                            mTitle.setText("Tools");
                            break;
                        //On settings icon click take user to settings fragment and set text to settings
                        case R.id.settings:
                            selectedFragment = new SettingsFragment();
                            mTitle.setText("Settings");
                            break;
                        //On profile icon click take user to profile fragment and set text to profile
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            mTitle.setText("Profile");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit(); //Commit transaction and replace the current fragment each time
                    return true;
                }

            };


}
