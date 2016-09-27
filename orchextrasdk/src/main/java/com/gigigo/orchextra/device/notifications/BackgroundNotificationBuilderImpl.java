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

package com.gigigo.orchextra.device.notifications;

import android.app.PendingIntent;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;

public class BackgroundNotificationBuilderImpl implements NotificationBuilder {

    private final AndroidNotificationBuilder androidNotification;
    private final AndroidBasicActionMapper androidBasicActionMapper;

    public BackgroundNotificationBuilderImpl(AndroidNotificationBuilder androidNotification,
                                             AndroidBasicActionMapper androidBasicActionMapper) {
        this.androidNotification = androidNotification;
        this.androidBasicActionMapper = androidBasicActionMapper;
    }

    @Override
    public void buildNotification(BasicAction action, OrchextraNotification notification) {
        AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToExternalClass(
            action);

        PendingIntent pendingIntent = androidNotification.getPendingIntent(androidBasicAction);

        androidNotification.createNotification(notification, pendingIntent);
    }

}
