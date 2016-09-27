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

package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.interactors.config.ClearLocalStorageInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceEventsInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofencesProviderInteractor;
import com.gigigo.orchextra.domain.interactors.imagerecognition.GetImageRecognitionCredentialsInteractor;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveCrmUserInteractor;

public interface InteractorsModuleProvider {
  SaveCrmUserInteractor provideSaveUserInteractor();
  RegionsProviderInteractor provideRegionsProviderInteractor();
  SendConfigInteractor provideSendConfigInteractor();
  BeaconEventsInteractor provideBeaconEventsInteractor();
  GeofenceEventsInteractor provideGeofenceInteractor();
  GeofencesProviderInteractor provideGeofenceProviderInteractor();

  GetImageRecognitionCredentialsInteractor provideGetImageRecognitionCredentialsInteractor();
  ScannerInteractor provideScannerInteractor();
  ClearLocalStorageInteractor provideClearLocalStorageInteractor();
}
