package github.nisrulz.navigationdrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();

    DrawerLayout androidDrawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;

    NavigationView navigationView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavDrawerToggel();

        navigateToFragment(getString(R.string.str_home));
        navigationView.setCheckedItem(R.id.nav_menu_home);
    }

    private void initNavDrawerToggel() {

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        androidDrawerLayout = findViewById(R.id.drawer_design_support_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, androidDrawerLayout, R.string.app_name,
                R.string.app_name);
        androidDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                navigationView.setCheckedItem(item.getItemId());

                //Closing drawer on item click
                androidDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.nav_menu_home:
                        navigateToFragment(getResources().getString(R.string.str_home));
                        break;
                    case R.id.nav_menu_settings:
                        navigateToFragment(getResources().getString(R.string.str_settings));
                        break;
                    case R.id.nav_menu_aboutus:
                        navigateToFragment(getResources().getString(R.string.str_aboutus));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // This is required to make the drawer toggle work
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        /*
         * if you have other menu items in your activity/toolbar
         * handle them here and return true
         */
        return super.onOptionsItemSelected(item);
    }

    private void navigateToFragment(String title) {
        // Update the toolbar title
        updateToolbarTitle(title);

        // Get the fragment by tag
        Fragment fragment = fragmentManager.findFragmentByTag(title);

        if (fragment == null) {
            // Initialize the fragment based on tag
            if (title.equals(getResources().getString(R.string.str_home))) {
                fragment = new HomeScreenFragment();
            } else if (title.equals(getResources().getString(R.string.str_settings))) {
                fragment = new SettingsScreenFragment();
            } else if (title.equals(getResources().getString(R.string.str_aboutus))) {
                fragment = new AboutUsSceenFragment();
            }

            // Add fragment with tag
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment, title).commit();
        } else {
            fragmentManager.beginTransaction()
                    // detach the fragment that is currently visible
                    .detach(fragmentManager.findFragmentById(R.id.fragment_container))
                    // attach the fragment found as per the tag
                    .attach(fragment)
                    // commit fragment transaction
                    .commit();
        }
    }

    private void updateToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
