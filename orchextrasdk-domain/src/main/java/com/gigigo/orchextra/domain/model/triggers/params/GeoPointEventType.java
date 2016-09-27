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

package com.gigigo.orchextra.domain.model.triggers.params;

import com.gigigo.orchextra.domain.model.StringValueEnum;

public enum GeoPointEventType implements StringValueEnum {
  ENTER("enter"),
  EXIT("exit"),
  STAY("stay");

  private final String type;

  GeoPointEventType(final String type) {
    this.type = type;
  }

  @Override
  public String getStringValue() {
    return type;
  }

  public static GeoPointEventType getTypeFromString(String stringValue) {
    for (GeoPointEventType geoPointEventType : values()) {
      if (geoPointEventType.getStringValue().equals(stringValue)) {
        return geoPointEventType;
      }
    }
    return ENTER;
  }
}
