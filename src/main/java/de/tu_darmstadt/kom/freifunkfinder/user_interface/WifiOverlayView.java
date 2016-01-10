package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.List;
import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplication;
import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplicationInt;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiOverlayView extends View {

    public static final String DEBUG_TAG = "WifiOverlayView :";
    private final Context context;
    private Handler handler;
    private WifiFinderApplicationInt wifiFinderApplication;

    private final static Location mountWashington = new Location("manual");
    static {
        mountWashington.setLatitude(70.90566);
        mountWashington.setLongitude(8.85432);
        mountWashington.setAltitude(200);
    }

    private MobileLocationManager mobileLocationManager;
    private MobileSensorManager mobileSensorManager;

    private Location currentLocation = null;
    private float[] currentAccelerometer = null;
    private float[] currentCompass = null;

    private Display display;
    private float displayX = 0.0f;
    private float displayY = 0.0f;
    private float canvasH = 1.0f;
    private float canvasW = 1.0f;

    private float dx = 0.0f;
    private float dy = 0.0f;

    private float orientation[] = new float[3];

    private float verticalFOV;
    private float horizontalFOV;

    private TextPaint contentPaint;
    private Paint targetPaint;
    private Paint compassPaint;
    private Paint compassPaintLoc;
    private Paint compassPaintTxt;
    private Paint compassPaintLin;
    private Paint compassPaintHot;

    public WifiOverlayView(Context context, MobileLocationManager mobileLocationManager) {
        super(context);

        this.context = context;
        this.handler = new Handler();
        this.mobileLocationManager = mobileLocationManager;
        mobileSensorManager = new MobileSensorManager(context, this);
        mobileSensorManager.initSensors();
        wifiFinderApplication = WifiFinderApplication.getWifiFinderApplication(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

        // get camera parameters for FOV's
        Camera camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        verticalFOV = params.getVerticalViewAngle();
        horizontalFOV = params.getHorizontalViewAngle();
        camera.release();

        // paint for target
        targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetPaint.setColor(Color.GREEN);

        compassPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        compassPaint.setColor(Color.GRAY);
        compassPaint.setAlpha(60);

        compassPaintLoc = new Paint(Paint.ANTI_ALIAS_FLAG);
        compassPaintLoc.setColor(Color.CYAN);

        compassPaintLin = new Paint(Paint.ANTI_ALIAS_FLAG);
        compassPaintLin.setColor(Color.GRAY);
        compassPaintLin.setAlpha(200);

        compassPaintTxt = new Paint(Paint.ANTI_ALIAS_FLAG);
        compassPaintTxt.setColor(Color.BLUE);
        compassPaintTxt.setTextSize(25);
        compassPaintTxt.setAlpha(100);

        compassPaintHot = new Paint(Paint.ANTI_ALIAS_FLAG);
        compassPaintHot.setColor(Color.GREEN);
        compassPaintHot.setAlpha(100);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float dx_n_180 = 2364.9634f;

        float curBearingToMW = 0.0f;

        if (MobileLocation.getLocation() != null) {
            currentLocation = MobileLocation.getLocation();
            curBearingToMW = currentLocation.bearingTo(mountWashington);
        }

        float rotation[] = new float[9];
        float identity[] = new float[9];
        currentAccelerometer = mobileSensorManager.getAccelerometer();
        currentCompass = mobileSensorManager.getCompass();
        if (currentAccelerometer != null && currentCompass != null) {
            boolean isRotation = SensorManager.getRotationMatrix(rotation, identity, currentAccelerometer, currentCompass);
            if (isRotation) {
                float cameraRotation[] = new float[9];
                // remap such that the camera is pointing straight down the Y
                // axis
                SensorManager.remapCoordinateSystem(rotation,
                        SensorManager.AXIS_X, SensorManager.AXIS_Z,
                        cameraRotation);

                // orientation vector
                SensorManager.getOrientation(cameraRotation, orientation);

                canvas.save();
                // use roll for screen rotation
                canvas.rotate((float) (0.0f - Math.toDegrees(orientation[2])));

                Double al = calculateAngle(mountWashington);
                // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
                dx = (float) ( (canvas.getWidth()/ horizontalFOV) * (Math.toDegrees(orientation[0])));
                dy = (float) ( (canvas.getHeight()/ verticalFOV) * (Math.toDegrees(orientation[1])));

                displayX += dx;
                displayY += dy;

                canvasW = canvas.getWidth();
                canvasH = canvas.getHeight();

                //altitude
                float dyal = (float) ( (canvas.getHeight()/ verticalFOV) * (al));

                float dx1 = (float) ( (canvas.getWidth()/ horizontalFOV) * (Math.toDegrees(orientation[0])-curBearingToMW));
                dx1 = dx1-dx;

                 // wait to translate the dx so the horizon doesn't get pushed off
                canvas.translate(0.0f, 0.0f-dy);

                // make our line big enough to draw regardless of rotation and translation
                //canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight()/2, canvas.getWidth()+canvas.getHeight(), canvas.getHeight()/2, targetPaint);

                // now translate the dx
                canvas.translate(0.0f-dx, 0.0f);

                // draw our point -- we've rotated and translated this to the right spot already
                canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, 20.0f, targetPaint);
                canvas.drawCircle(canvas.getWidth()/2-dx1, canvas.getHeight()/2, 20.0f, targetPaint);
                canvas.restore();

            }
        }

        canvas.save();
        //for compass part
        //draw rectangle
        canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), 120.0f, compassPaint);
        //draw lines
        canvas.drawLine(canvas.getWidth() / 2, 0.0f, canvas.getWidth() / 2, 100.0f, compassPaintLin);
        canvas.drawLine(canvas.getWidth() / 4, 0.0f, canvas.getWidth() / 4, 100.0f, compassPaintLin);
        canvas.drawLine(3 * canvas.getWidth() / 4, 0.0f, 3 * canvas.getWidth() / 4, 100.0f, compassPaintLin);
        canvas.drawLine(0.0f, 0.0f, 0.0f, 100.0f, compassPaintLin);
        canvas.drawLine(canvas.getWidth(), 0.0f, canvas.getWidth(), 100.0f, compassPaintLin);
        //draw s,w,n,e,s
        canvas.drawText("N", (canvas.getWidth() / 2) - 3, 120.0f, compassPaintTxt);
        canvas.drawText("W", (canvas.getWidth() / 4) - 3, 120.0f, compassPaintTxt);
        canvas.drawText("E", (3 * canvas.getWidth() / 4) - 3, 120.0f, compassPaintTxt);
        canvas.drawText("S", (0.0f) - 3, 120.0f, compassPaintTxt);
        //draw hotspots
        canvas.drawLine((curBearingToMW * canvas.getWidth()) / (2 * dx_n_180), 25.0f, (curBearingToMW * canvas.getWidth()) / (2 * dx_n_180), 75.0f, compassPaintHot);

        float move = dx * canvas.getWidth() / (2 * dx_n_180);
        canvas.drawCircle(canvas.getWidth()/2+move, 110.0f, 10.0f, compassPaintLoc);

        canvas.translate(15.0f, 15.0f);
        canvas.restore();
    }

    public void onPause() {
        mobileLocationManager.locationRemove();
        mobileSensorManager.unRegister();
    }

    public void onResume() {
        mobileLocationManager.initLocation();
        mobileSensorManager.initSensors();
    }

    private Double calculateAngle(Location loc) {

        Double near = haversineDistanceLocal(loc.getLatitude(), loc.getLongitude());
        Double oppo = loc.getAltitude() - 200;
        Double angle = Math.toDegrees(Math.atan(oppo / near));
        return angle;
    }


    private Double haversineDistanceLocal(Double aq, Double bq) {

        final int R = 6371;
        Double lat1 = 49.900;
        Double lon1 = 8.856;
        Double lat2 = 0.0;
        Double lon2 = 0.0;
        lat2 = aq;
        lon2 = bq;

        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c * 1000;

        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }


    //this is for showig hotspot detail
    @Override
    public boolean onTouchEvent( MotionEvent event) {
        super.onTouchEvent(event);
        //Log.d("motionEvent", event.toString());
        System.out.println(event.getX() + "==" + event.getY() + "==" + event.getRawX() + "==" + event.getRawY());
        float tX = dx + event.getRawX() * canvasW / display.getWidth();
        float tY = dy + event.getRawY() * canvasH / display.getHeight();
        System.out.println(display.getWidth() + "================================" + display.getHeight());
        System.out.println(tX + "================================" + tY);
        if (event.getAction()==MotionEvent.ACTION_UP){
            Log.d("motionEvent", "action_up");
            //this.x+=(int)event.getX();
            //this.y+=(int)event.getY();
            return true;
        }
        this.postInvalidate();
        return true;
    }

    public List<WifiAccessPointDTO> displayRelevantWifiNodes(){
        return wifiFinderApplication.getRelevantWifiNodes();
    }

}
