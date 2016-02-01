package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.MobileLocation;

/**
 * Created by govind on 12/10/2015.
 */
public class MobileLocationManager implements LocationListener {

    public static final String DEBUG_TAG = "MobileLocationManager :";
    private final Context applicationContext;
    private LocationManager locationManager = null;
    private String bestProvider;
    private Activity activity;

    public MobileLocationManager(Context applicationContext, Activity activity) {
        this.applicationContext = applicationContext;
        this.activity = activity;
        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void initLocation() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAltitudeRequired(true);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        bestProvider = locationManager.getBestProvider(criteria, true);
        Log.v(DEBUG_TAG, "selected best provider: " + bestProvider);

        /*Location lastLocation = locationManager.getLastKnownLocation(bestProvider);
        MobileLocation.setLocation(lastLocation);
        Log.d(DEBUG_TAG, "last location " + lastLocation);

        if (bestProvider.equals("gps") && (lastLocation == null)) {
            Log.i(DEBUG_TAG, "no gps coverage, switching to WiFi \"network\"");
            bestProvider = "network";
        }*/

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }

        GlobalParams.setBestLocationProvider(bestProvider);

        Log.d(DEBUG_TAG, "doing requestLocationUpdates");
        locationManager.requestLocationUpdates(bestProvider, 50, 0, this);
    }

    public void locationRemove() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        MobileLocation.setLocation(location);
        if (bestProvider.equals("network") && (location.getAltitude() == 0.0)) {
            MobileLocation.getLocation().setAltitude(150);
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        applicationContext.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onProviderDisabled(String provider) {
        // ...
    }

    public void onProviderEnabled(String provider) {
        // ...
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // ...
    }
}
