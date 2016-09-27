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

package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.services.DomainService;
import java.util.List;

public class BeaconCheckerDomainService implements DomainService {

  private final ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider;
  private final ConfigDataProvider configDataProvider;

  public BeaconCheckerDomainService(ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider,
                                    ConfigDataProvider configDataProvider) {
    this.proximityAndGeofencesLocalDataProvider = proximityAndGeofencesLocalDataProvider;
    this.configDataProvider = configDataProvider;
  }

  public InteractorResponse getCheckedBeaconList(List<OrchextraBeacon> orchextraBeacons) {

    int requestTime = configDataProvider.obtainRequestTime();

    proximityAndGeofencesLocalDataProvider.purgeOldBeaconEventsWithRequestTime(requestTime);

    return obtainTriggerableBeacons(orchextraBeacons);
  }

  private InteractorResponse obtainTriggerableBeacons(List<OrchextraBeacon> orchextraBeacons) {

    BusinessObject<List<OrchextraBeacon>> bo =
        proximityAndGeofencesLocalDataProvider.getNotStoredBeaconEvents(orchextraBeacons);

    List<OrchextraBeacon> triggerableBeacons = bo.getData();

    for (OrchextraBeacon beacon : triggerableBeacons) {
      proximityAndGeofencesLocalDataProvider.storeBeaconEvent(beacon);
    }

    return new InteractorResponse<>(triggerableBeacons);
  }
}
