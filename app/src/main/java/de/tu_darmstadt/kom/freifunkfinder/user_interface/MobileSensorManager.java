/* MobileSensorManager - Do all sensor related stuffs here.
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

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MobileSensorManager implements SensorEventListener {

    public static final String DEBUG_TAG = "MobileSensorManager :";
    private final Context applicationContext;
    private WifiOverlayView wifiOverlayView;
    private SensorManager sensorManager = null;
    private float[] accelerometer = null;
    private float[] compass = null;
    private boolean accelAvailable;
    private boolean compassAvailable;
    private boolean gyroAvailable;
    private Sensor accelSensor;
    private Sensor compassSensor;
    private Sensor gyroSensor;
    private float smoothVectorAcc[][] = new float[5][3];
    private float smoothVectorMag[][] = new float[5][3];
    private int accCount = 0;
    private int magCount = 0;
    private float divider = 5;

    /**
     * Constructor.
     *
     * @param applicationContext the application context.
     * @param wifiOverlayView an instance of WifiOverlayView.
     */
    public MobileSensorManager(Context applicationContext, WifiOverlayView wifiOverlayView) {
        this.applicationContext = applicationContext;
        this.wifiOverlayView = wifiOverlayView;
        sensorManager = (SensorManager) applicationContext.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                smoothVectorAcc[i][j] = 0.0f;
                smoothVectorMag[i][j] = 0.0f;
            }
        }
    }

    /**
     * register sensors for sensor updates.
     * Reference http://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     */
    public void initSensors() {
        accelAvailable = sensorManager.registerListener(this, accelSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        compassAvailable = sensorManager.registerListener(this, compassSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        gyroAvailable = sensorManager.registerListener(this, gyroSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * unregister for stopping sensor updates.
     */
    public void unRegister() {
        sensorManager.unregisterListener(this);
    }

    /**
     * returns latest accelerometer value.
     *
     * @return accelerometer values
     */
    public float[] getAccelerometer() {
        return accelerometer;
    }

    /**
     * returns latest compass value.
     *
     * @return compass values
     */
    public float[] getCompass() {
        return compass;
    }

    /**
     * Overriden method, on sensor accuracy changed event.
     *
     * @param id the ID of the sensor being monitored.
     * @param accuracy the new accuracy of this sensor.
     */
    @Override
    public void onAccuracyChanged(Sensor id, int accuracy) {
        Log.d(DEBUG_TAG, "onAccuracyChanged");

    }

    /**
     * Overriden method, called when sensor values have changed.
     * Reference http://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     *
     * @param event the sensor event.
     */
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometer = exSmooth(event.values.clone(), accelerometer, event, 0.8f);
                break;
            case Sensor.TYPE_GYROSCOPE:
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                compass = exSmooth(event.values.clone(), compass, event, 0.8f);
                break;
        }
        wifiOverlayView.invalidate();
    }

    /**
     * Sensor smoothing using both averaging and exponential smoothing.
     * Reference http://stackoverflow.com/questions/15864223
     * Reference http://stackoverflow.com/questions/7598574
     *
     * @param in new sensor reading.
     * @param out old sensor reading.
     * @param event the sensor event.
     * @param smoothfactor the smoothing factor.
     * @return smoothed sensor value
     */
    private float[] exSmooth(float[] in, float[] out, SensorEvent event, float smoothfactor) {
        float avg = 0.0f;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                if (out == null) {
                    smoothVectorAcc[accCount][0] = in[0];
                    smoothVectorAcc[accCount][1] = in[1];
                    smoothVectorAcc[accCount][2] = in[2];
                    accCount = (accCount + 1) % 5;
                    return in;
                }
                for (int i = 0; i < in.length; i++) {
                    smoothVectorAcc[accCount][i] = in[i];
                    avg = (smoothVectorAcc[0][i] + smoothVectorAcc[1][i] + smoothVectorAcc[2][i]
                            + smoothVectorAcc[3][i] + smoothVectorAcc[4][i]) / divider;
                    if (Math.abs(avg - in[i]) < 1.5f) {

                        out[i] = out[i] + 0.03f * (in[i] - out[i]);
                    } else {
                        out[i] = out[i] + smoothfactor * (in[i] - out[i]);
                    }
                }
                accCount = (accCount + 1) % 5;
                break;
            case Sensor.TYPE_GYROSCOPE:
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                if (out == null) {
                    smoothVectorMag[magCount][0] = in[0];
                    smoothVectorMag[magCount][1] = in[1];
                    smoothVectorMag[magCount][2] = in[2];
                    magCount = (magCount + 1) % 5;
                    return in;
                }
                for (int i = 0; i < in.length; i++) {
                    smoothVectorMag[magCount][i] = in[i];
                    avg = (smoothVectorMag[0][i] + smoothVectorMag[1][i] + smoothVectorMag[2][i]
                            + smoothVectorMag[3][i] + smoothVectorMag[4][i]) / divider;
                    if (Math.abs(avg - in[i]) < 1.5f) {
                        out[i] = out[i] + 0.03f * (in[i] - out[i]);
                    } else {
                        out[i] = out[i] + smoothfactor * (in[i] - out[i]);
                    }
                }
                magCount = (magCount + 1) % 5;
                break;
        }
        return out;
    }
}
