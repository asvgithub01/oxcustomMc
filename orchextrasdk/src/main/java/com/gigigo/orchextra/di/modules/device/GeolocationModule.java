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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeocoder;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceRegisterImpl;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.geolocation.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module
public class GeolocationModule {

  @Singleton
  @Provides
  RetrieveLastKnownLocation provideRetrieveLastKnownLocation(ContextProvider contextProvider,
                                                             GoogleApiClientConnector googleApiClientConnector,
      PermissionChecker permissionChecker,
      PermissionLocationImp permissionLocationImp,
      OrchextraLogger orchextraLogger) {

    return new RetrieveLastKnownLocation(contextProvider, googleApiClientConnector, permissionChecker, permissionLocationImp, orchextraLogger);
  }

  @Singleton
  @Provides AndroidGeocoder provideAndroidGeocoder(ContextProvider contextProvider) {
    return new AndroidGeocoder(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides GeolocationManager provideAndroidGeolocationManager(
      RetrieveLastKnownLocation retrieveLastKnownLocation, AndroidGeocoder androidGeocoder) {
    return new AndroidGeolocationManager(retrieveLastKnownLocation, androidGeocoder);
  }

  @Singleton
  @Provides GeofencePendingIntentCreator provideGeofencePendingIntentCreator(ContextProvider contextProvider) {
    return new GeofencePendingIntentCreator(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides
  GeofenceRegister provideAndroidGeofenceManager(GeofenceDeviceRegister geofenceDeviceRegister,
                                                        ConfigObservable configObservable,
                                                        GeofenceController geofenceController) {
    return new AndroidGeofenceRegisterImpl(geofenceDeviceRegister, configObservable, geofenceController);
  }

  @Singleton
  @Provides AndroidGeofenceConverter provideAndroidGeofenceMapper() {
        return new AndroidGeofenceConverter();
    }

  @Singleton
  @Provides GeofenceDeviceRegister provideGeofenceDeviceRegister(ContextProvider contextProvider,
                                                                 GoogleApiClientConnector googleApiClientConnector,
                                                                 GeofencePendingIntentCreator geofencePendingIntentCreator,
                                                                 PermissionChecker permissionChecker,
                                                                 PermissionLocationImp permissionLocationImp,
                                                                 AndroidGeofenceConverter androidGeofenceConverter,
                                                                 OrchextraLogger orchextraLogger) {
    return new GeofenceDeviceRegister(contextProvider, googleApiClientConnector, geofencePendingIntentCreator,
            permissionChecker, permissionLocationImp, androidGeofenceConverter, orchextraLogger);
  }
}
