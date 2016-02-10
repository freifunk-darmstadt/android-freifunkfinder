/* CameraView - Do all camera related stuffs here.
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
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;


public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    public static final String DEBUG_TAG = "CameraView :";
    private SurfaceHolder sHolder;
    private Camera camera;
    Activity activity;

    /**
     * Constructor.
     *
     * @param context the application context.
     * @param activity the MainActivity.
     */
    public CameraView(Context context, Activity activity){
        super(context);

        this.activity = activity;
        sHolder = getHolder();
        sHolder.addCallback(this);
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.d(DEBUG_TAG, "starting camera");
    }

    /**
     * Overriden method, set the preview display based on display orientation.
     * Reference http://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     *
     * @param sfHolder the SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder sfHolder) {
        Log.d(DEBUG_TAG, "surfaceCreated");
        camera = Camera.open();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, cameraInfo);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degreeRotate = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degreeRotate = 0;
                break;
            case Surface.ROTATION_90:
                degreeRotate = 90;
                break;
            case Surface.ROTATION_180:
                degreeRotate = 180;
                break;
            case Surface.ROTATION_270:
                degreeRotate = 270;
                break;
        }
        int displayOrietation = (cameraInfo.orientation - degreeRotate + 360) % 360;
        camera.setDisplayOrientation(displayOrietation);

        try {
            camera.setPreviewDisplay(sHolder);
            Log.d(DEBUG_TAG, "set holder");
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "Camera error on surfaceCreated " + e.getMessage());
        }

    }

    /**
     * Overriden method, start preview based on camera parameters.
     * Reference http://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     *
     * @param sfHolder the SurfaceHolder whose surface has changed.
     * @param format the new PixelFormat of the surface.
     * @param width the new width of the surface.
     * @param height the new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder sfHolder, int format, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizesSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : previewSizesSizes)
        {
            if((size.height <= height) && (size.width <= width))
            {
                parameters.setPreviewSize(size.width, size.height);
                break;
            }
        }
        Log.d(DEBUG_TAG, "starting preview");
        camera.setParameters(parameters);
        camera.startPreview();
    }

    /**
     * Overriden method, destroy the surface.
     * Reference http://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     *
     * @param sfHolder the SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder sfHolder) {
        camera.stopPreview();
        camera.release();
    }
}
