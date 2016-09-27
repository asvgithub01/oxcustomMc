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

package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.di.modules.domain.DomainModuleProvider;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.domain.abstractions.threads.ThreadSpec;


public interface ControlModuleProvider extends DomainModuleProvider{
  ConfigObservable provideConfigObservable();
  GeofenceController provideProximityItemController();
  @BackThread
  ThreadSpec provideThreadSpec();
}
