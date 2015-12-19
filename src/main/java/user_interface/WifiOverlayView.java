package user_interface;

import android.graphics.Camera;

import java.util.ArrayList;
import java.util.List;

import application.WifiFinderApplication;
import common.WifiAccessPointVO;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiOverlayView {

    private WifiAccessPointVO wifiAccessPointVO;

    private WifiFinderApplication wifiFinderApp;

    private SensorManager sensorManager;

    private CameraView cameraView;

    public void persistWifiNodes(){
        wifiFinderApp.persistWifiNode();
    }

    public List<WifiAccessPointVO> displayRelevantWifiNodes(){
        List<WifiAccessPointVO> relevantWifiNodes = wifiFinderApp.getRelevantWifiNodes();

        return relevantWifiNodes;
    }

    public WifiAccessPointVO getDescription(String nodeId){
        WifiAccessPointVO wifiAccessPointVO = null;

        return wifiAccessPointVO;
    }
}
