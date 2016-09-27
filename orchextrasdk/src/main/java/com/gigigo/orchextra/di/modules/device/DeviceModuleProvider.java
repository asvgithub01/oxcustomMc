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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.information.AndroidSdkVersionAppInfo;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.permissions.PermissionCameraImp;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;

public interface DeviceModuleProvider extends
    BluetoothModuleProvider,
    ActionsModuleProvider,
    NotificationsModuleProvider,
    GeolocationModuleProvider,
    ImageRecognitionModuleProvider{

    AndroidSdkVersionAppInfo provideAndroidApp();
    AndroidDevice provideAndroidDevice();
    OrchextraLogger provideOrchextraLogger();
    GoogleApiClientConnector provideGoogleApiClientConnector();
    PermissionChecker providePermissionChecker();
    PermissionLocationImp providePermissionLocationImp();
    PermissionCameraImp providePermissionCameraImp();
    OrchextraStatusAccessor provideOrchextraStatusAccessor();

}
