/* HttpServerImpl - An implementation of ServerInterface to read data from Freifunk server.
 * Copyright (C) 2016  Govind Singh
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
 * govind.singh@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.application.server;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpServerImpl implements ServerInterface<String> {

    private static final String DEBUG_TAG = "HttpServerImpl : ";

    /**
     * HTTP GET request to the Freifunk server.
     *
     * @param url the Freifunk service URL.
     * @return response of type String.
     */
    @Override
    public String getRequest(String url) {
        BufferedReader bufferedReader = null;
        String httpResponse = null;
        try {
            Log.d(DEBUG_TAG, " Request URL : " + url);
            URL httpRequestUrl = new URL(url);
            bufferedReader = new BufferedReader(new InputStreamReader(httpRequestUrl.openStream()));
            char[] responseChars = new char[1024];
            int lastByteRead = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((lastByteRead = bufferedReader.read(responseChars)) != -1) {
                stringBuffer.append(responseChars, 0, lastByteRead);
            }
            httpResponse = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return httpResponse;
    }

}
