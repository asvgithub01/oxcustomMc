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

package com.gigigo.orchextra.domain.model.entities.proximity;

/**
 * This class only store action id and boolean Is the action cancelable, for when the response comes,
 * we can do the relationship beetween response schedulable action and the action will be executed
 */
public class ActionRelatedWithRegionAndGeofences {

  final String actionId;
  final boolean cancelable;

  public ActionRelatedWithRegionAndGeofences(String actionId, boolean cancelable) {
    this.actionId = actionId;
    this.cancelable = cancelable;
  }

  public String getActionId() {
    return actionId;
  }

  public boolean isCancelable() {
    return cancelable;
  }
}
