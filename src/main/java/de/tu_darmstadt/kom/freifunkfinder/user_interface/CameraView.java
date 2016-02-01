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

/**
 * Created by govind,sooraj,puneet on 12/10/2015.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    public static final String DEBUG_TAG = "CameraView :";
    private SurfaceHolder sHolder;
    private Camera camera;
    Activity activity;

    public CameraView(Context context, Activity activity){
        super(context);

        this.activity = activity;
        sHolder = getHolder();
        sHolder.addCallback(this);
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.d(DEBUG_TAG, "starting camera");
    }

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

    @Override
    public void surfaceDestroyed(SurfaceHolder sfHolder) {
        camera.stopPreview();
        camera.release();
    }
}
