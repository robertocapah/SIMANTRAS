package com.kalbenutritionals.simantra;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker;
import com.kalbenutritionals.simantra.Fragment.FragmentHome;
import com.kalbenutritionals.simantra.Fragment.FragmentNotification;
import com.kalbenutritionals.simantra.Fragment.FragmentPushData;
import com.kalbenutritionals.simantra.Fragment.FragmentSPMSearch;
import com.kalbenutritionals.simantra.Fragment.FragmentSearch;
import com.kalbenutritionals.simantra.Fragment.FragmentSetting;

public class ActivityMainMenu2 extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private String i_View = "Fragment";
    FrameLayout frame;
    int selectedId;
    NavigationView nav_view;
    MenuItem checkNavItem = null;
    DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_2);
        frame = (FrameLayout) findViewById(R.id.frame);
        super.onCreate(savedInstanceState);
        initToolbar();
        initNavigationMenu();
        if (getIntent().getStringExtra(i_View) != null) {
            if (getIntent().getStringExtra(i_View).equals("FragmentNotification")) {
            } else {
                toolbar.setTitle("Home");
                setSupportActionBar(toolbar);
                FragmentHome homFragment = new FragmentHome();
                FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                fragmentTransactionHome.replace(R.id.frame, homFragment);
                fragmentTransactionHome.commit();
                selectedId = 99;
            }
        }
        if (getIntent().getStringExtra(new ClsHardCode().txtBundleKeyBarcode) != null) {
            FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
            FragmentTransaction fragmentTransactionInfoChecker = getSupportFragmentManager().beginTransaction();
            fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
            fragmentTransactionInfoChecker.commit();
        } else if (getIntent().getStringExtra(i_View) != null) {
            if (getIntent().getStringExtra(i_View).equals("FragmentNotification")) {
                toolbar.setTitle("Notification");
                setSupportActionBar(toolbar);

                FragmentNotification fragmentNotification = new FragmentNotification();
                FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                fragmentTransactionNotification.replace(R.id.frame, fragmentNotification);
                fragmentTransactionNotification.commit();
                selectedId = 99;
            } else if (getIntent().getStringExtra(i_View).equals("FragmentPushData")) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                toolbar.setTitle("Push Data");
                setSupportActionBar(toolbar);

//                toolbar.setTitle("Push Data");
//                toolbar.setSubtitle(null);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Bundle bundle = new Bundle();
                String myMessage = "notMainMenu";
                bundle.putString("message", myMessage);

                FragmentPushData fragmentPushData = new FragmentPushData();
                fragmentPushData.setArguments(bundle);
                FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                fragmentTransactionPushData.replace(R.id.frame, fragmentPushData);
                fragmentTransactionPushData.commit();
                selectedId = 99;
            }
        } else {
            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);


            /*FragmentHome homFragment = new FragmentHome();
            FragmentTransactions fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.frame, homFragment);
            fragmentTransactionHome.commit();*/
            selectedId = 99;
        }
    }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Simantra");
        ClsTools.setSystemBarColor(this);
    }

    private void initNavigationMenu() {
        nav_view = (NavigationView) findViewById(R.id.navigation_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                checkNavItem = item;
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                drawer.closeDrawers();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Fragment fragment = null;
                if (checkNavItem != null) {
                    switch (checkNavItem.getItemId()) {
                        case R.id.mnLogout:
                            checkNavItem = null;
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ActivityMainMenu2.this);

                            builder.setTitle("Confirm");
                            builder.setMessage("Logout Application?");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
//                                    logout(false);
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                            break;
                        case R.id.home:
                            checkNavItem = null;
                            toolbar.setTitle("Home");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            FragmentHome homFragment = new FragmentHome();
                            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionHome.replace(R.id.frame, homFragment);
                            fragmentTransactionHome.commit();
                            selectedId = 99;
                            break;
                        case R.id.search:
                            checkNavItem = null;
                            toolbar.setTitle("Search");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            FragmentSearch searchFragment = new FragmentSearch();
                            FragmentTransaction fragmentTransactionSearch = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSearch.replace(R.id.frame, searchFragment);
                            fragmentTransactionSearch.commit();
                            selectedId = 99;
                            break;
                        case R.id.transporter:
                            checkNavItem = null;
                            toolbar.setTitle("SIMANTRA");

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            // fragment yang dituju
                            FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
                            FragmentTransaction fragmentTransactionSPMSearch = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
                            fragmentTransactionSPMSearch.commit();

                            /*FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
                            FragmentTransactions fragmentTransactionInfoChecker = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
                            fragmentTransactionInfoChecker.commit();*/

                            /*FragmentChecklist checklistFragment = new FragmentChecklist();
                            FragmentTransactions fragmentTransactionChecklist = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionChecklist.replace(R.id.frame, checklistFragment);
                            fragmentTransactionChecklist.commit();*/
                            selectedId = 99;
                            break;
                        case R.id.mnpushData:
                            checkNavItem = null;
                            toolbar.setTitle("Push Data");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentPushData fragmentPushData = new FragmentPushData();
                            FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionPushData.replace(R.id.frame, fragmentPushData);
                            fragmentTransactionPushData.commit();
                            selectedId = 99;
                            break;
                        case R.id.mnDownloadData:
                            checkNavItem = null;
                            toolbar.setTitle("Download Data");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


                            selectedId = 99;
                            break;
                        case R.id.mnNotification:
                            checkNavItem = null;
                            toolbar.setTitle("Notification");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentNotification fragmentNotification = new FragmentNotification();
                            FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionNotification.replace(R.id.frame, fragmentNotification);
                            fragmentTransactionNotification.commit();
                            selectedId = 99;

                            break;
                        case R.id.mnSetting:
                            checkNavItem = null;
                            toolbar.setTitle("Setting");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            FragmentSetting fragmentSetting = new FragmentSetting();
                            FragmentTransaction fragmentTransactionSetting = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionSetting.replace(R.id.frame, fragmentSetting);
                            fragmentTransactionSetting.commit();
                            selectedId = 99;
                            break;
                        case R.id.mntestMenu:
                            checkNavItem = null;
                            toolbar.setTitle("Setting");
                            toolbar.setSubtitle(null);

                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            /*FragmentQuestionTab fragmentTestUI = new FragmentQuestionTab();
                            FragmentTransactions fragmentTransactionTestUI = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTestUI.replace(R.id.frame, fragmentTestUI);
                            fragmentTransactionTestUI.commit();*/
                            selectedId = 99;
                            break;

                        default:
                            checkNavItem = null;
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                            /*try {
                                Class<?> fragmentClass = Class.forName(linkMenu[checkNavItem.getItemId()]);
                                try {
                                    toolbar.setTitle(checkNavItem.getTitle().toString());
                                    toolbar.setSubtitle(null);

                                    fragment = (Fragment) fragmentClass.newInstance();
                                    FragmentTransactions fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.frame, fragment);
                                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
                                    fragmentTransaction.commit();
                                    selectedId = checkNavItem.getItemId();
                                    isSubMenu = false;

                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }*/
                    }
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };


        // open drawer at start
        drawer.openDrawer(GravityCompat.START);
    }
}
