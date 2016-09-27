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

package com.gigigo.orchextra.device.bluetooth;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothAvailability;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusListener;
import com.gigigo.orchextra.device.permissions.CoarseLocationPermission;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.sdk.features.BeaconFeature;


public class BluetoothStatusInfoImpl implements BluetoothStatusInfo {

  private final PermissionChecker permissionChecker;
  private final BluetoothAvailability bluetoothAvailability;
  private final ContextProvider contextProvider;
  private final AppRunningMode appRunningMode;
  private final FeatureListener featureListener;
  private BluetoothStatusListener bluetoothStatusListener;


  public BluetoothStatusInfoImpl(PermissionChecker permissionChecker,
      BluetoothAvailability bluetoothAvailability, ContextProvider contextProvider,
      AppRunningMode appRunningMode, FeatureListener featureListener) {

    this.permissionChecker = permissionChecker;
    this.bluetoothAvailability = bluetoothAvailability;
    this.contextProvider = contextProvider;
    this.appRunningMode = appRunningMode;
    this.featureListener = featureListener;
  }

  @Override public void obtainBluetoothStatus() {

    if (!checkBlteSupported()){
      return;
    }

    hasBltePermissions();

  }

  private void checkEnabled() {

    if (bluetoothAvailability.isBlteEnabled()){
      informBluetoothStatus(BluetoothStatus.READY_FOR_SCAN);
    }else{
      informBluetoothStatus(BluetoothStatus.NOT_ENABLED);
    }

  }

  private void hasBltePermissions() {
    final Permission permission = new CoarseLocationPermission();

    boolean allowed = permissionChecker.isGranted(permission);
    if (allowed){
      onPermissionResponse(allowed);
    }else{
    if (appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND){
      permissionChecker.askForPermission(permission, new UserPermissionRequestResponseListener() {
        @Override public void onPermissionAllowed(boolean permissionAllowed) {
          onPermissionResponse(permissionAllowed);
        }
      }, contextProvider.getCurrentActivity());
    }else{
      onPermissionResponse(false);
    }
    }
  }

  private void onPermissionResponse(boolean allowed) {
    if (allowed){
      checkEnabled();
    }else{
      informBluetoothStatus(BluetoothStatus.NO_PERMISSIONS);
    }
  }

  private boolean checkBlteSupported() {
    if (!bluetoothAvailability.isBlteSupported()){
      informBluetoothStatus(BluetoothStatus.NO_BLTE_SUPPORTED);
      return false;
    }
    return true;
  }

  @Override
  public void setBluetoothStatusListener(BluetoothStatusListener bluetoothStatusListener) {
    this.bluetoothStatusListener = bluetoothStatusListener;
  }

  private void informBluetoothStatus(BluetoothStatus status) {
    if (bluetoothStatusListener!=null){
      bluetoothStatusListener.onBluetoothStatus(status);
    }
    featureListener.onFeatureStatusChanged(new BeaconFeature(status));
  }

}
