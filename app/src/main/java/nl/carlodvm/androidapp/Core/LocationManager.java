package nl.carlodvm.androidapp.Core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.ar.core.Pose;
import nl.carlodvm.androidapp.AugmentedNode;
import nl.carlodvm.androidapp.PermissionHelper.LocationPermissionHelper;

//All units are in meters
public class LocationManager implements LocationListener {
    private final long EARTH_RADIUS = 6378137;
    private boolean isGPSEnabled;
    private boolean isNetEnabled;
    private android.location.LocationManager m_locationManager;
    private Location m_deviceLocation;
    private SensorManager m_sensorManager;


    @SuppressLint("MissingPermission")
    public LocationManager(Context context, Activity activity) {
        m_locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        m_sensorManager = new SensorManager(context);

        checkIfAvailable(context, activity);

        m_locationManager.requestSingleUpdate(isGPSEnabled ? android.location.LocationManager.GPS_PROVIDER : android.location.LocationManager.NETWORK_PROVIDER
                , this, context.getMainLooper());
    }

    public Location GetModelGPSLocation(AugmentedNode node) {
        if (m_deviceLocation == null) {
            Log.e(LocationManager.class.getSimpleName(), "Device location could not be found.");
            return null;
        }

        m_sensorManager.updateOrientationAngles();
        float angleBetweenDeviceAndNorth = m_sensorManager.getmOrientationAngles()[0];

        Location modelLoc = new Location(android.location.LocationManager.GPS_PROVIDER);
        Pose position = node.getImage().getCenterPose();

        double calculatedDistanceForLatitude = Math.sin(angleBetweenDeviceAndNorth) * position.tz();
        modelLoc.setLatitude(CalculateLatitudeFromOffset(calculatedDistanceForLatitude));

        double calculatedDistanceForLongitude = Math.cos(angleBetweenDeviceAndNorth) * position.tz();
        modelLoc.setLongitude(CalculateLongitudeFromOffset(calculatedDistanceForLongitude));

        modelLoc.setAltitude(position.ty() + m_deviceLocation.getAltitude());

        return modelLoc;
    }

    ///offset in meters
    private double CalculateLatitudeFromOffset(double offset) {
        return m_deviceLocation.getLatitude() + (180 / Math.PI) * (offset / EARTH_RADIUS);
    }

    private double CalculateLongitudeFromOffset(double offset) {
        return m_deviceLocation.getLongitude() + (180 / Math.PI) * (offset / EARTH_RADIUS) / Math.cos(Math.toRadians(m_deviceLocation.getLatitude()));

    }

    private void checkIfAvailable(Context context, Activity activity) {
        if (!LocationPermissionHelper.hasLocationPermission(activity)) {
            LocationPermissionHelper.requestLocationPermission(activity);
        }

        isGPSEnabled = m_locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        isNetEnabled = m_locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetEnabled) {
            Toast.makeText(context, "Enable GPS to enable functionality.", Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        m_deviceLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
