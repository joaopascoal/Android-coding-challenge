package com.germanautolabs.weatherapp.android.components.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Joao on 1/15/2017.
 */

public class LocationSystem implements LocationListener
{
    private LocationManager mLocationManager;

    private int mLatitude;
    private int mLongitude;
    private String mCity;
    private String mCountryName;
    private boolean mFindLocation;

    private String mWeatherData;

    private Context mContext;

    public LocationSystem() {}

    public void init(Context context)
    {
        this.mContext = context;
        this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void start()
    {
        if (!isMock())
        {
            if (this.mContext != null)
            {
                if (ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
        else
        {
            postEvent();
        }
    }

    private void stop()
    {
        if (ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.mLocationManager.removeUpdates(this);
    }

    public boolean isMock()
    {
        return false;
    }

    public boolean isFindLocation()
    {
        return this.mFindLocation;
    }

    public void setWeatherData(String pData)
    {
        this.mWeatherData = pData;
    }

    public String getWeatherData()
    {
        return this.mWeatherData;
    }

    public String getCity()
    {
        return this.mCity;
    }

    public String getCountryName()
    {
        return this.mCountryName;
    }

    public void postEvent()
    {
        if (isFindLocation())
            EventBus.getDefault().post(new Event(this.mLatitude, this.mLongitude));
    }

    /**
     * LocationListener methods
     *
     */
    @Override
    public void onLocationChanged(Location location)
    {
        this.mLatitude = (int) Math.round(location.getLatitude());
        this.mLongitude = (int) Math.round(location.getLongitude());

        Geocoder geoCoder = new Geocoder(this.mContext, Locale.getDefault());
        try
        {
            List<Address> addressList = geoCoder.getFromLocation(this.mLatitude, this.mLongitude, 1);

            if (addressList.size() > 0)
            {
                Address address = addressList.get(0);
                this.mCity = address.getLocality();
                this.mCountryName = address.getCountryName();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.mFindLocation = true;
        this.postEvent();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    /**
     * Event class
     */
    public class Event
    {
        private int mLatitude;
        private int mLongitude;

        public Event(int pLat, int pLon)
        {
            this.mLatitude = pLat;
            this.mLongitude = pLon;
        }

        public int getLatitude()
        {
            return this.mLatitude;
        }

        public int getLongitude()
        {
            return this.mLongitude;
        }
    }
}
