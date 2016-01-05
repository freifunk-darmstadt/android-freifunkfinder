package de.tu_darmstadt.kom.freifunkfinder.application.server;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by govind on 12/17/2015.
 */
public class HttpServerImpl implements ServerInterface<String> {

    @Override
    public String getRequest(String url) {
        BufferedReader bufferedReader = null;
        String response = null;
        try{
            URL httpUrl = new URL(url);
            System.out.println("http url : " +url);
            bufferedReader = new BufferedReader(new InputStreamReader(httpUrl.openStream()));
            char []responseChars = new char[1024];
            int lastByteRead = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((lastByteRead = bufferedReader.read(responseChars)) != -1){
                stringBuffer.append(responseChars, 0, lastByteRead);
            }
            response = stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
        }catch (IOException io){
            io.printStackTrace();
        }
        return response;
    }

}
