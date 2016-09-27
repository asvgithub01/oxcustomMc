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

package com.gigigo.orchextra.device.permissions;

import android.Manifest;

import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.orchextra.R;

public class PermissionLocationImp implements Permission {

    @Override
    public String getAndroidPermissionStringType() {
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    @Override
    public int getPermissionSettingsDeniedFeedback() {
        return R.string.ox_permission_settings;
    }

    @Override
    public int getPermissionDeniedFeedback() {
        return R.string.ox_permission_denied_geolocation;
    }

    @Override
    public int getPermissionRationaleTitle() {
        return R.string.ox_permission_rationale_title_location;
    }

    @Override
    public int getPermissionRationaleMessage() {
        return R.string.ox_permission_rationale_message_location;
    }
}
