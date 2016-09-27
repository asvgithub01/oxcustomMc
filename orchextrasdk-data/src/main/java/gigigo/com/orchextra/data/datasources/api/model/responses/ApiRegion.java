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

package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiRegion extends ApiProximityItem {

  @Expose @SerializedName("minor") private Integer minor;

  @Expose @SerializedName("major") private Integer major;

  @Expose @SerializedName("uuid") private String uuid;

  @Expose @SerializedName("active") private boolean active;

  public Integer getMinor() {
    return minor;
  }

  public void setMinor(Integer minor) {
    this.minor = minor;
  }

  public Integer getMajor() {
    return major;
  }

  public void setMajor(Integer major) {
    this.major = major;
  }

  public boolean isActive() {
    return active;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
