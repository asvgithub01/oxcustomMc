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

package com.gigigo.orchextra.dataprovision.authentication.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;


public interface SessionDBDataSource {

  boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials);

  boolean saveSdkAuthResponse(SdkAuthData sdkAuthData);

  boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials);

  boolean saveClientAuthResponse(ClientAuthData clientAuthData);

  boolean saveUser(CrmUser crmUser);

  BusinessObject<ClientAuthData> getSessionToken();

  BusinessObject<SdkAuthData> getDeviceToken();

  BusinessObject<CrmUser> getCrm();

  boolean storeCrm(CrmUser crmUser);

  void clearAuthenticatedUser();

  void clearAuthenticatedSdk();
}