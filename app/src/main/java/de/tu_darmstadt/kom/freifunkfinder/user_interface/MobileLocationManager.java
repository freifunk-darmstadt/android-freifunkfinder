/* MobileLocationManager - Do all location related stuffs here.
 * Copyright (C) 2016  Sooraj Mandotti
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * sooraj.mandotti@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import de.tu_darmstadt.kom.freifunkfinder.common.MobileLocation;

public class MobileLocationManager {

    public static final String DEBUG_TAG = "MobileLocationManager :";
    private final Context applicationContext;
    private LocationManager locationManager = null;
    private String bestProvider;
    private Activity activity;
    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;
    private boolean firstLocationSet = false;
    private boolean gpsUpdating = false;
    private boolean onceCalled = false;
    private AlertDialog alert;

    /**
     * Constructor.
     *
     * @param applicationContext the application context.
     * @param activity the MainActivity.
     */
    public MobileLocationManager(Context applicationContext, Activity activity) {
        this.applicationContext = applicationContext;
        this.activity = activity;
        locationManager = (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Check and register location providers
     * Reference http://stackoverflow.com/questions/2227292
     */
    public void initLocation() {

        //get both provider status
        try{
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        try{
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        //open settings in case location is not enabled
        if(!gpsEnabled && !networkEnabled && !onceCalled) {
            onceCalled = true;
            buildAlertMessageNoGpsNet();
        }

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }

        //start GPS updates
        if(gpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 0, locationListenerForGps);
        }

        //start Network updates
        if(networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 0, locationListenerForNetwork);
        }
    }

    /**
     * A listener for GPS provider
     * Reference http://stackoverflow.com/questions/2227292
     */
    LocationListener locationListenerForGps = new LocationListener() {

        /**
         * Overriden method, on location changed event.
         * Reference http://stackoverflow.com/questions/2227292
         */
        public void onLocationChanged(Location location) {
            Log.d(DEBUG_TAG,"in GPS changed location");
            gpsUpdating = true;
            MobileLocation.setLocation(location);
            if (!firstLocationSet) {
                firstLocationSet = true;
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    /**
     * A listener for Network provider
     * Reference http://stackoverflow.com/questions/2227292
     */
    LocationListener locationListenerForNetwork = new LocationListener() {

        /**
         * Overriden method, on location changed event.
         * Reference http://stackoverflow.com/questions/2227292
         */
        public void onLocationChanged(Location location) {
            if (!firstLocationSet) {
                MobileLocation.setLocation(location);
                firstLocationSet = true;
            }

            if (gpsUpdating) {
                if (MobileLocation.getLocation() != null &&
                        ((Math.abs(MobileLocation.getLocation().getTime() - location.getTime())) >= 1000)) {
                    gpsUpdating = false;
                }
            }

            if (!gpsUpdating) {
                MobileLocation.setLocation(location);
                Log.d(DEBUG_TAG,"in Network changed location");
                if (location.getAltitude() == 0.0) {
                    MobileLocation.getLocation().setAltitude(150);
                }
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    /**
     * Unregister all location providers.
     */
    public void locationRemove() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(locationListenerForGps);
        locationManager.removeUpdates(locationListenerForNetwork);
    }

    /**
     * For showing an alert message if location is disabled.
     * Reference http://stackoverflow.com/questions/843675
     */
    private void buildAlertMessageNoGpsNet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your Location seems to be off, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        dialog.dismiss();
                        dialog.cancel();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        applicationContext.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
}
