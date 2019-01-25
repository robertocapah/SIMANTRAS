package com.kalbe.mobiledevknlibs.Maps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.R;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

/**
 * Created by Robert on 31/12/2017.
 */

public class PopUpMaps extends Activity implements LocationListener, OnMapReadyCallback {
    private Location mLastLocation;

    GoogleMap mMap;
    boolean mockStatus = false;

    public void popUpMaps(final Context context, int resViewLayout) {
        Boolean valid = true;
        getLocation(context);
        double latitude = 0;
        double longitude = 0;
//                double latitudeOutlet = 0;
//                double longitudeOutlet = 0;

        try {
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
            } else {
//                latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
//                longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
                getLocation(context);
            }

        } catch (Exception ex) {
            valid = false;
//            new clsMainActivity().showCustomToast(getContext(), "Your location not found", false);
            new ToastCustom().showToasty(context.getApplicationContext(),"Your location is not detected",4);
        }

                /*if (valid) {
                    try {
                        latitudeOutlet = Double.parseDouble(HMoutletLat.get(spnOutlet.getSelectedItem().toString()));
                        longitudeOutlet = Double.parseDouble(HMoutletLong.get(spnOutlet.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        valid = false;

                        new clsMainActivity().showCustomToast(getContext(), "Outlet location not found", false);
                    }
                }*/

        if (valid) {

            LayoutInflater layoutInflater = LayoutInflater.from(context.getApplicationContext());

//                    MapFragment f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
//                    if (f != null) {
//                        (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
//                    }
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View promptView = inflater.inflate(resViewLayout, null);

            final Activity activity = (Activity) context;

            mMap = ((MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap == null) {
                mMap = ((MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location");

//                    String outletName = spnOutlet.getSelectedItem().toString();
//                    MarkerOptions markerOutlet = new MarkerOptions().position(new LatLng(latitudeOutlet, longitudeOutlet)).title("Outlet Location").snippet(outletName);


            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(marker.getPosition());
//                    builder.include(markerOutlet.getPosition());
            LatLngBounds bounds = builder.build();

            mMap.clear();
            mMap.addMarker(marker);
//                    mMap.addMarker(markerOutlet);
//                    PolylineOptions rectOptions = new PolylineOptions().add(new LatLng(latitude, longitude))
//                            .add(new LatLng(latitudeOutlet, longitudeOutlet));
//                    Polyline polyline = mMap.addPolyline(rectOptions);
            //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(19).build();

            final GoogleMap finalMMap = mMap;
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    // Move camera.
                    finalMMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60));
                    // Remove listener to prevent position reset on camera move.
                    finalMMap.setOnCameraChangeListener(null);
                }
            });

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MapFragment f = (MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map);
                                    if (f != null) {
                                        (activity).getFragmentManager().beginTransaction().remove(f).commit();
                                    }

                                    dialog.dismiss();
                                }
                            });
            final android.support.v7.app.AlertDialog alertD = alertDialogBuilder.create();

            Location locationA = new Location("point A");

            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);
            alertD.setTitle("Your Position");
            alertD.show();
        }
    }

    public void popUpMapsCustom(final Context context, int resViewLayout, String lblLat, String lblLong) {
        Boolean valid = true;
        getLocation(context);
        double latitude = 0;
        double longitude = 0;

        try {
            latitude = Double.parseDouble(lblLat);
            longitude = Double.parseDouble(lblLong);
        } catch (Exception ex) {
            valid = false;
            new ToastCustom().showToasty(context.getApplicationContext(),"Your location is not detected",4);
        }

        if (valid) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View promptView = inflater.inflate(resViewLayout, null);

            final Activity activity = (Activity) context;

            mMap = ((MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap == null) {
                mMap = ((MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location");
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(marker.getPosition());
            mMap.clear();
            mMap.addMarker(marker);
            final GoogleMap finalMMap = mMap;
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    // Move camera.
                    finalMMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60));
                    // Remove listener to prevent position reset on camera move.
                    finalMMap.setOnCameraChangeListener(null);
                }
            });

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MapFragment f = (MapFragment) (activity).getFragmentManager().findFragmentById(R.id.map);
                                    if (f != null) {
                                        (activity).getFragmentManager().beginTransaction().remove(f).commit();
                                    }

                                    dialog.dismiss();
                                }
                            });
            final android.support.v7.app.AlertDialog alertD = alertDialogBuilder.create();

            Location locationA = new Location("point A");

            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);
            alertD.setTitle("Your Position");
            alertD.show();
        }
    }

    public void popUpMapsTwoCoordinates(final Context context, int resViewLayout, String paramLatitude, String paramLongitude) {
        Boolean valid = true;

        double latitude = 0;
        double longitude = 0;
        double latitudeOutlet = 0;
        double longitudeOutlet = 0;

        //Check longlat my location
        try {

            latitude = getLocation(context).getLatitude();
            longitude = getLocation(context).getLongitude();
        } catch (Exception ex) {
            valid = false;
            new ToastCustom().showToastSPGMobile(context, "Your location not found", false);
        }

        //Check longlat outlet location
        if (valid) {
            try {
                latitudeOutlet = Double.parseDouble(String.valueOf(paramLatitude));
                longitudeOutlet = Double.parseDouble(String.valueOf(paramLongitude));
            } catch (Exception ex) {
                valid = false;
                new ToastCustom().showToastSPGMobile(context, "Outlet location not found", false);
            }
        }

        if (valid) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(resViewLayout, null);

            GoogleMap mMap;
            mMap = ((MapFragment) ((Activity)context).getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap == null) {
                mMap = ((MapFragment) ((Activity)context).getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location");

            MarkerOptions markerOutlet = new MarkerOptions().position(new LatLng(latitudeOutlet, longitudeOutlet)).title("Outlet Location");

            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(marker.getPosition());
            builder.include(markerOutlet.getPosition());

            mMap.clear();
            mMap.addMarker(marker);
            mMap.addMarker(markerOutlet);

            final GoogleMap finalMMap = mMap;
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    // Move camera.
                    finalMMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60));
                    // Remove listener to prevent position reset on camera move.
                    finalMMap.setOnCameraChangeListener(null);
                }
            });

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MapFragment f = (MapFragment) ((Activity)context).getFragmentManager().findFragmentById(R.id.map);
                                    if (f != null) {
                                        ((Activity)context).getFragmentManager().beginTransaction().remove(f).commit();
                                    }

                                    dialog.dismiss();
                                }
                            });
            final AlertDialog alertD = alertDialogBuilder.create();

            Location locationA = new Location("point A");

            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);

            Location locationB = new Location("point B");

            locationB.setLatitude(latitudeOutlet);
            locationB.setLongitude(longitudeOutlet);

            float distance = locationA.distanceTo(locationB);

            alertD.setTitle(String.valueOf((int) Math.ceil(distance)) + " meters");
            alertD.show();
        }
    }
    public Location getLocation(Context activity) {

        try {
            LocationManager locationManager = (LocationManager) activity
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean canGetLocation = false;


            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
//                new clsMainActivity().showCustomToast(getContext(), "no network provider is enabled", false);
                new ToastCustom().showToasty(activity.getApplicationContext(),"no network provider is enabled",4);
            } else {
                canGetLocation = true;
                Location location = null;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            0, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            if (mLastLocation != null){
                                mLastLocation.reset();
                            }
                            mLastLocation = location;
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    Location location2 = null;
                    if (mLastLocation == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1000,
                                0, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                if (mLastLocation != null){
                                    mLastLocation.reset();
                                }
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                            }else{
//                                new clsMainActivity().showCustomToast(context,"Your GPS off", false);
                                new ToastCustom().showToasty(activity.getApplicationContext(),"Your GPS off",4);
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mLastLocation != null) {
            int intOs = Integer.valueOf(android.os.Build.VERSION.SDK);
            if (intOs >= 18) {
                mockStatus = mLastLocation.isFromMockProvider();
            }
        }
        if (mockStatus){
//            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Fake GPS detected, !", false);
            new ToastCustom().showToasty(activity.getApplicationContext(),"Fake GPS detected !",4);
            Toast.makeText(activity.getApplicationContext(),"Please Turn Off Fake Location, And Restart Your Phone",Toast.LENGTH_LONG);
            Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(5000);
        }

        return mLastLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        Bundle extras = location.getExtras();
        mockStatus = extras != null && extras.getBoolean(FusedLocationProviderApi.KEY_MOCK_LOCATION, false);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        setUpMap();
    }
}
