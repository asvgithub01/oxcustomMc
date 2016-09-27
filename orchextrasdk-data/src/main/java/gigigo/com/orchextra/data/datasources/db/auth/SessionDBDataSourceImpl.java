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

package gigigo.com.orchextra.data.datasources.db.auth;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import io.realm.Realm;
import io.realm.exceptions.RealmException;


public class SessionDBDataSourceImpl implements SessionDBDataSource {

  private final Context context;
  private final SessionUpdater sessionUpdater;
  private final SessionReader sessionReader;
  private final RealmDefaultInstance realmDefaultInstance;

  public SessionDBDataSourceImpl(Context context, SessionUpdater sessionUpdater,
      SessionReader sessionReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.sessionUpdater = sessionUpdater;
    this.sessionReader = sessionReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  @Override public boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthCredentials(realm, sdkAuthCredentials);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthResponse(realm, sdkAuthData);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthCredentials(realm, clientAuthCredentials);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthResponse(realm, clientAuthData);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveUser(CrmUser crmUser) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crmUser);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public BusinessObject<ClientAuthData> getSessionToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ClientAuthData clientAuthData = sessionReader.readClientAuthData(realm);
      return new BusinessObject(clientAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<SdkAuthData> getDeviceToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      SdkAuthData sdkAuthData = sessionReader.readSdkAuthData(realm);
      return new BusinessObject(sdkAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<CrmUser> getCrm() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      CrmUser crmUser = sessionReader.readCrm(realm);
      return new BusinessObject(crmUser, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public boolean storeCrm(CrmUser crmUser) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crmUser);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
    return true;
  }


  @Override public void clearAuthenticatedUser() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      realm.beginTransaction();
      //realm.clear(ClientAuthRealm.class);
      realm.delete(ClientAuthRealm.class);
    } catch (RealmException re) {

    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
  }

  @Override
  public void clearAuthenticatedSdk() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      realm.beginTransaction();
      realm.delete(SdkAuthCredentialsRealm.class);
      realm.delete(SdkAuthRealm.class);
    } catch (RealmException re) {

    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
  }
}
