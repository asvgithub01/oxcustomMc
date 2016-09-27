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

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.DeleteGeofenceEventError;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveGeofenceItemError;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.services.DomainService;
import java.util.ArrayList;
import java.util.List;

public class GeofenceCheckerDomainService implements DomainService {

  private final ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider;

  public GeofenceCheckerDomainService(ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {
    this.proximityAndGeofencesLocalDataProvider = proximityAndGeofencesLocalDataProvider;
  }

  public InteractorResponse<List<OrchextraGeofence>> obtainEventGeofences(
      List<String> triggeringGeofenceIds, GeoPointEventType geofenceTransition) {

    if (geofenceTransition != GeoPointEventType.EXIT) {
      return new InteractorResponse(storeGeofences(triggeringGeofenceIds));
    } else {
      return new InteractorResponse(deleteStoredGeofences(triggeringGeofenceIds));
    }
  }

  private List<OrchextraGeofence> storeGeofences(List<String> triggeringGeofenceIds) {

    List<OrchextraGeofence> addedGeofenceList = new ArrayList<>();

    for (String triggeringGeofenceId : triggeringGeofenceIds) {
      InteractorResponse<OrchextraGeofence> interactorResponse =
          storeGeofence(triggeringGeofenceId);
      if (!interactorResponse.hasError()) {
        addedGeofenceList.add(interactorResponse.getResult());
      }
    }

    return addedGeofenceList;
  }

  private InteractorResponse<OrchextraGeofence> storeGeofence(String triggeringGeofenceId) {
    BusinessObject<OrchextraGeofence> boSavedGeofence =
        proximityAndGeofencesLocalDataProvider.obtainSavedGeofenceInDatabase(triggeringGeofenceId);

    if (boSavedGeofence.isSuccess()) {
      return storeGeofenceEvent(boSavedGeofence.getData());
    } else {
      return new InteractorResponse(new RetrieveGeofenceItemError(
          BusinessError.createKoInstance(boSavedGeofence.getBusinessError().getMessage())));
    }
  }

  private InteractorResponse<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence geofence) {
    BusinessObject<OrchextraGeofence> boEventGeofence =
        proximityAndGeofencesLocalDataProvider.obtainGeofenceEvent(geofence);

    if (boEventGeofence.getData() != null) {
      return new InteractorResponse(new RetrieveGeofenceItemError(
          BusinessError.createKoInstance(boEventGeofence.getBusinessError().getMessage())));
    } else {
      boEventGeofence = proximityAndGeofencesLocalDataProvider.storeGeofenceEvent(geofence);
      return new InteractorResponse(boEventGeofence.getData());
    }
  }

  private List<OrchextraGeofence> deleteStoredGeofences(List<String> triggeringGeofenceIds) {

    List<OrchextraGeofence> removedGeofenceList = new ArrayList<>();

    for (String triggeringGeofenceId : triggeringGeofenceIds) {
      InteractorResponse<OrchextraGeofence> interactorResponse =
          deleteStoredGeofence(triggeringGeofenceId);
      if (!interactorResponse.hasError()) {
        removedGeofenceList.add(interactorResponse.getResult());
      }
    }

    return removedGeofenceList;
  }

  private InteractorResponse<OrchextraGeofence> deleteStoredGeofence(String triggeringGeofenceId) {
    BusinessObject<OrchextraGeofence> bo =
        proximityAndGeofencesLocalDataProvider.deleteGeofenceEvent(triggeringGeofenceId);
    if (!bo.isSuccess()) {
      return new InteractorResponse(new DeleteGeofenceEventError(
          BusinessError.createKoInstance(bo.getBusinessError().getMessage())));
    } else {
      return new InteractorResponse(bo.getData());
    }
  }

  public BusinessObject<OrchextraGeofence> obtainCheckedGeofence(String eventCode) {
    return proximityAndGeofencesLocalDataProvider.obtainSavedGeofenceInDatabase(eventCode);
  }

  public List<OrchextraGeofence> obtainGeofencesById(List<String> triggeringGeofenceIds) {
    List<OrchextraGeofence> orchextraGeofenceList = new ArrayList<>();

    for (String triggeringGeofenceId : triggeringGeofenceIds) {
      BusinessObject<OrchextraGeofence> boGeofence =
          proximityAndGeofencesLocalDataProvider.obtainSavedGeofenceInDatabase(triggeringGeofenceId);
      if (boGeofence.isSuccess()) {
        orchextraGeofenceList.add(boGeofence.getData());
      }
    }

    return orchextraGeofenceList;
  }
}
