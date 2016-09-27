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
package com.gigigo.orchextra.domain.services.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomainService;
import java.util.Collections;
import java.util.List;

public class ObtainGeofencesDomainService implements DomainService {

  private final ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider;

  public ObtainGeofencesDomainService(ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {
    this.proximityAndGeofencesLocalDataProvider = proximityAndGeofencesLocalDataProvider;
  }

  public InteractorResponse obtainGeofencesFromLocalStorage() throws Exception {

    BusinessObject<List<OrchextraGeofence>> bo = proximityAndGeofencesLocalDataProvider.obtainGeofencesForRegister();

    if (bo.isSuccess()) {
      return new InteractorResponse<>(bo.getData());
    } else {
      return new InteractorResponse<>(Collections.<OrchextraRegion>emptyList());
    }
  }
}
