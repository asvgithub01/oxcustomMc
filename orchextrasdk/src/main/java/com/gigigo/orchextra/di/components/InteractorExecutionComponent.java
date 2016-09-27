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

package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.di.modules.domain.InteractorsModule;
import com.gigigo.orchextra.di.modules.domain.InteractorsModuleProvider;
import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import java.util.List;

import orchextra.dagger.Subcomponent;

@PerExecution @Subcomponent(modules = InteractorsModule.class)
public interface InteractorExecutionComponent extends InteractorsModuleProvider {
    void injectSaveUserInteractorExecution(InteractorExecution<ClientAuthData> interactorExecution);
    void injectConfigInteractorInteractorExecution(InteractorExecution<OrchextraUpdates> interactorExecution);
    void injectRegionsProviderInteractorExecution(InteractorExecution<List<OrchextraRegion>> interactorExecution);
    void injectBeaconEventsInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);
    void injectGeofenceInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);
    void injectGeofenceProviderInteractorExecution(InteractorExecution<List<OrchextraGeofence>> interactorExecution);
    void injectObtainIrCredentialsInteractorExecution(InteractorExecution<VuforiaCredentials> interactorExecution);
    void injectScannerInteractorExecution(InteractorExecution<BasicAction> interactorExecution);
    void injectClearStorageInteractorExecution(InteractorExecution<Boolean> interactorExecution);
}
