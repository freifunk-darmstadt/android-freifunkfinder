package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;

/**
 * Created by govind on 12/10/2015.
 */
public class MobileLocationManager implements LocationListener {

    public static final String DEBUG_TAG = "MobileLocationManager :";
    private final Context applicationContext;
    private LocationManager locationManager = null;
    private String bestProvider;

    public MobileLocationManager(Context applicationContext) {
        this.applicationContext = applicationContext;
        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void initLocation() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAltitudeRequired(true);

        bestProvider = locationManager.getBestProvider(criteria, true);
        Log.v(DEBUG_TAG, "selected best provider: " + bestProvider);

        Location lastLocation = locationManager.getLastKnownLocation(bestProvider);
        if ( (bestProvider == "gps") && (lastLocation == null)) {
            Log.i(DEBUG_TAG, "no gps coverage, switching to WiFi \"network\"");
            bestProvider = "network";
        }

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        GlobalParams.setBestLocationProvider(bestProvider);
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
