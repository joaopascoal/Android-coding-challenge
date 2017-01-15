package com.germanautolabs.weatherapp.android.components.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Joao on 1/15/2017.
 */

public class DefaultURLConnection implements IURLConnection
{

    private int mError = 0;

    @Override
    public String getResult(String urlString)
    {
        StringBuilder result = new StringBuilder();

        try
        {
            InputStream stream = getHttpConnection(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return result.toString();
    }

    @Override
    public void clearError()
    {
        this.mError = 0;
    }

    @Override
    public int getError()
    {
        return this.mError;
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException
    {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}
