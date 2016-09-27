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

package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GeofenceEventRealm extends RealmObject {

  public static final String CODE_FIELD_NAME = "code";
  public static final String TYPE_FIELD_NAME = "type";

  @PrimaryKey private String code;
  private RealmPoint point;
  private int radius;
  private boolean notifyOnExit;
  private boolean notifyOnEntry;
  private int stayTime;

  private String type;
  private String actionRelated;
  private boolean actionRelatedCancelable;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public RealmPoint getPoint() {
    return point;
  }

  public void setPoint(RealmPoint point) {
    this.point = point;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public boolean isNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }

  public boolean isNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }

  public int getStayTime() {
    return stayTime;
  }

  public void setStayTime(int stayTime) {
    this.stayTime = stayTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setActionRelated(String actionRelated) {
    this.actionRelated = actionRelated;
  }

  public String getActionRelated() {
    return actionRelated;
  }

  public boolean isActionRelatedCancelable() {
    return actionRelatedCancelable;
  }

  public void setActionRelatedCancelable(boolean actionRelatedCancelable) {
    this.actionRelatedCancelable = actionRelatedCancelable;
  }
}
