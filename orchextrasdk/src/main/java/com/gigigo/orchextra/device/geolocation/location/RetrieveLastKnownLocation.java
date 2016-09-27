/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.geolocation.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;

public class RetrieveLastKnownLocation {

    private final ContextProvider contextProvider;
    private final GoogleApiClientConnector googleApiClientConnector;
    private final PermissionChecker permissionChecker;
    private final PermissionLocationImp accessFineLocationPermissionImp;
    private final OrchextraLogger orchextraLogger;

    private OnLastKnownLocationListener onLastKnownLocationListener;

    public RetrieveLastKnownLocation(ContextProvider contextProvider,
                                     GoogleApiClientConnector googleApiClientConnector,
                                     PermissionChecker permissionChecker,
                                     PermissionLocationImp accessFineLocationPermissionImp,
                                     OrchextraLogger orchextraLogger) {

        this.contextProvider = contextProvider;
        this.googleApiClientConnector = googleApiClientConnector;
        this.permissionChecker = permissionChecker;
        this.accessFineLocationPermissionImp = accessFineLocationPermissionImp;
        this.orchextraLogger = orchextraLogger;
    }

    public void getLastKnownLocation(OnLastKnownLocationListener onLastKnownLocationListener) {
        this.onLastKnownLocationListener = onLastKnownLocationListener;
        googleApiClientConnector.setOnConnectedListener(onConnectedListener);
        googleApiClientConnector.connect();
    }

    private GoogleApiClientConnector.OnConnectedListener onConnectedListener = new GoogleApiClientConnector.OnConnectedListener() {
        @Override
        public void onConnected(Bundle bundle) {
            askPermissionAndGetLastKnownLocation();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            boolean isGranted = permissionChecker.isGranted(accessFineLocationPermissionImp);
            if (isGranted) {
                getNetworkGpsLocation();
            }
        }
    };

    public void askPermissionAndGetLastKnownLocation() {
        boolean isGranted = permissionChecker.isGranted(accessFineLocationPermissionImp);
        if (isGranted) {
            getLastKnownLocation();
        } else {
            if (contextProvider.getCurrentActivity() != null) {
                permissionChecker.askForPermission(accessFineLocationPermissionImp, userPermissionResponseListener, contextProvider.getCurrentActivity());
            }
        }
    }

    @SuppressWarnings("ResourceType")
    private void getLastKnownLocation() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClientConnector.getGoogleApiClient());
        if (onLastKnownLocationListener != null) {
            onLastKnownLocationListener.onLastKnownLocation(lastLocation);
        }
    }

    private UserPermissionRequestResponseListener userPermissionResponseListener =
            new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed) {
            getLastKnownLocation();
        }
    };

    @SuppressWarnings("ResourceType")
    private void getNetworkGpsLocation() {
        Context context = contextProvider.getApplicationContext();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        String locationProvider;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            orchextraLogger.log("Connection failed: Location not Available");
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);

        if (onLastKnownLocationListener != null) {
            onLastKnownLocationListener.onLastKnownLocation(location);
        }
    }

    public interface OnLastKnownLocationListener {
        void onLastKnownLocation(Location location);
    }
}
