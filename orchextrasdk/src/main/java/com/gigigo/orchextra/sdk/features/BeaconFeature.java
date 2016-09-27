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

package com.gigigo.orchextra.sdk.features;

import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.initalization.features.Feature;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType;
import com.gigigo.orchextra.domain.model.StringValueEnum;

//todo notcomplete
public class BeaconFeature extends Feature {

  public BeaconFeature(StringValueEnum status) {
    super(FeatureType.BEACONS, status);
  }

  @Override public boolean isSuccess() {
    return !(getStatus() == BluetoothStatus.NO_BLTE_SUPPORTED
        || getStatus() == BluetoothStatus.NO_PERMISSIONS);
  }
}
