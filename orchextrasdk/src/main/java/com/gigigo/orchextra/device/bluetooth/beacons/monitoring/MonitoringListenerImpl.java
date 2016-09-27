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

package com.gigigo.orchextra.device.bluetooth.beacons.monitoring;

import com.gigigo.orchextra.device.bluetooth.beacons.ranging.BeaconRangingScanner;
import com.gigigo.orchextra.domain.abstractions.beacons.BackgroundBeaconsRangingTimeType;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

import java.util.ArrayList;
import java.util.List;

import org.altbeacon.beacon.Region;

public class MonitoringListenerImpl implements MonitoringListener {

    private final AppRunningMode appRunningMode;
    private final BeaconRangingScanner beaconRangingScanner;
    private final BackgroundBeaconsRangingTimeType backgroundBeaconsRangingTimeType;
    private final OrchextraLogger orchextraLogger;

    public MonitoringListenerImpl(AppRunningMode appRunningMode,
                                  BeaconRangingScanner beaconRangingScanner, OrchextraLogger orchextraLogger) {

        this.appRunningMode = appRunningMode;
        this.beaconRangingScanner = beaconRangingScanner;
        this.backgroundBeaconsRangingTimeType = beaconRangingScanner.getBackgroundBeaconsRangingTimeType();
        this.orchextraLogger = orchextraLogger;
    }

    @Override
    public void onRegionEnter(Region region) {

        List<Region> regions = new ArrayList<>();
        regions.add(region);
        if (appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND)
            beaconRangingScanner.initRangingScanForDetectedRegion(regions,
                    BackgroundBeaconsRangingTimeType.INFINITE);
        else
            beaconRangingScanner.initRangingScanForDetectedRegion(regions,
                    BackgroundBeaconsRangingTimeType.MAX);
    /*
    if (appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND){

      beaconRangingScanner.initRangingScanForDetectedRegion(regions,
          BackgroundBeaconsRangingTimeType.INFINITE);

      orchextraLogger.log("Ranging will be Started with infinite duration");

    }else if (appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND &&
        backgroundBeaconsRangingTimeType != BackgroundBeaconsRangingTimeType.DISABLED){

      beaconRangingScanner.initRangingScanForDetectedRegion(regions,
          backgroundBeaconsRangingTimeType);

      orchextraLogger.log("Ranging will be Started with " +
          String.valueOf(backgroundBeaconsRangingTimeType.getIntValue()) + " duration");

    }*/
    }

    @Override
    public void onRegionExit(Region region) {
        beaconRangingScanner.stopRangingScanForDetectedRegion(region);
    }
}
