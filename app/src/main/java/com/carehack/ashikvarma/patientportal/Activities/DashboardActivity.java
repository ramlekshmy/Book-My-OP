package com.carehack.ashikvarma.patientportal.Activities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.carehack.ashikvarma.patientportal.Fragments.DepartmentFragment;
import com.carehack.ashikvarma.patientportal.Fragments.MyAppointments;
import com.carehack.ashikvarma.patientportal.R;
import com.carehack.ashikvarma.patientportal.Fragments.TipsFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomnav;
    @BindView(R.id.container)
    LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        if (isDeviceOnline(DashboardActivity.this)) {
            startFragmentNavigation(new DepartmentFragment());
        } else {
            displaySnackBar(rootView, getResources().getString(R.string.device_offline_alert));
        }

        setBottomNavigation();
    }

    private void setBottomNavigation() {
        BottomNavigationViewHelper.disableShiftMode(bottomnav);

        bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_departments:
                        getFragmentManager().beginTransaction().replace(R.id.content, new DepartmentFragment()).commit();
                        return true;
                    case R.id.navigation_my_appointments:
                        getFragmentManager().beginTransaction().replace(R.id.content, new MyAppointments()).commit();
                        return true;
                    case R.id.navigation_tips:
                        getFragmentManager().beginTransaction().replace(R.id.content, new TipsFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void startFragmentNavigation(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public void startFragmentNavigation(Fragment fragment, Bundle bundle) {

        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // this is basically context of the class
        fragmentTransaction.add(R.id.content, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }


}