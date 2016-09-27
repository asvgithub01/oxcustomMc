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

package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiClientAuthCredentials implements ApiCredentials {

  @Expose @SerializedName("clientToken") private final String clientToken;
  @Expose @SerializedName("instanceId") private final String instanceId;

  @Expose @SerializedName("secureId") private final String secureId;
  @Expose @SerializedName("crmId") private final String crmId;
  @Expose @SerializedName("serialNumber") private final String serialNumber;
  @Expose @SerializedName("wifiMacAddress") private final String wifiMacAddress;
  @Expose @SerializedName("bluetoothMacAddress") private final String bluetoothMacAddress;

  public ApiClientAuthCredentials(AuthCredentials authCredentials) {

    ClientAuthCredentials clientCredentials =
        ConsistencyUtils.checkInstance(authCredentials, ClientAuthCredentials.class);

    this.clientToken = clientCredentials.getClientToken();
    this.instanceId = clientCredentials.getInstanceId();
    this.secureId = clientCredentials.getSecureId();
    this.crmId = clientCredentials.getCrmId();
    this.serialNumber = clientCredentials.getSerialNumber();
    this.wifiMacAddress = clientCredentials.getWifiMacAddress();
    this.bluetoothMacAddress = clientCredentials.getBluetoothMacAddress();
  }

  public String getClientToken() {
    return clientToken;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public String getSecureId() {
    return secureId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public String getWifiMacAddress() {
    return wifiMacAddress;
  }

  public String getBluetoothMacAddress() {
    return bluetoothMacAddress;
  }

  public String getCrmId() {
    return crmId;
  }
}
