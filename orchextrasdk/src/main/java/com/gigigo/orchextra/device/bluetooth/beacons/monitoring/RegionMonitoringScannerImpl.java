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

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.bluetooth.beacons.mapper.BeaconRegionAndroidMapper;
import com.gigigo.orchextra.control.controllers.proximity.beacons.BeaconsController;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;


public class RegionMonitoringScannerImpl implements RegionMonitoringScanner,
        BeaconConsumer, MonitorNotifier {

    private final org.altbeacon.beacon.BeaconManager beaconManager;
    private final Context context;
    private final MonitoringListener monitoringListener;
    private final BeaconsController beaconsController;
    private final BeaconRegionAndroidMapper regionMapper;
    private final OrchextraLogger orchextraLogger;

    private List<Region> regionsToBeMonitored = (List<Region>) Collections.synchronizedList(new ArrayList<Region>());
    private List<Region> regionsInEnter = (List<Region>) Collections.synchronizedList(new ArrayList<Region>());

    private boolean monitoring = false;

    public RegionMonitoringScannerImpl(ContextProvider contextProvider, BeaconManager beaconManager,
                                       MonitoringListener monitoringListener, BeaconsController beaconsController,
                                       BeaconRegionAndroidMapper regionMapper, OrchextraLogger orchextraLogger) {

        this.beaconManager = beaconManager;
        this.beaconsController = beaconsController;
        this.context = contextProvider.getApplicationContext();
        this.monitoringListener = monitoringListener;
        this.regionMapper = regionMapper;
        this.orchextraLogger = orchextraLogger;

        this.beaconManager.setMonitorNotifier(this);
    }

    //region BeaconConsumer Interface

    @Override
    public void onBeaconServiceConnect() {
        obtainRegionsToScan();
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return context.bindService(intent, serviceConnection, i);
    }

    // endregion

    //region MonitorNotifier Interface

    private void enterRegion(Region region) {
        OrchextraRegion orchextraRegion = regionMapper.externalClassToModel(region);
        beaconsController.onRegionEnter(orchextraRegion);
        monitoringListener.onRegionEnter(region);
        regionsInEnter.add(region);

        orchextraLogger.log("ENTER BEACON REGION : " + region.getUniqueId());
    }

    public void exitRegion(Region region) {
        OrchextraRegion orchextraRegion = regionMapper.externalClassToModel(region);
        beaconsController.onRegionExit(orchextraRegion);
        monitoringListener.onRegionExit(region);
        regionsInEnter.remove(region);

        orchextraLogger.log("EXIT BEACON REGION : " + region.getUniqueId());
    }

    @Override
    public void didEnterRegion(Region region) {
       // enterRegion(region);
    }

    @Override
    public void didExitRegion(Region region) {
       // exitRegion(region);
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        if (state == MonitorNotifier.INSIDE)
            enterRegion(region);
        if (state == MonitorNotifier.OUTSIDE)
            exitRegion(region);
    }
    // endregion

    //region RegionMonitoringScanner Interface

    @Override
    public void initMonitoring() {
        beaconManager.bind(this);
    }

    @Override
    public void stopMonitoring() {
        stopMonitoringRegions(regionsToBeMonitored);
        monitoring = false;
        regionsInEnter.clear();
        beaconManager.unbind(this);
    }

    private void obtainRegionsToScan() {
        beaconsController.getAllRegionsFromDataBase(this);
    }

    @Override
    public boolean isMonitoring() {
        return monitoring;
    }

    @Override
    public void setRunningMode(AppRunningModeType appRunningModeType) {
        beaconManager.setBackgroundMode(false);
        //beaconManager.setBackgroundMode(appRunningModeType == AppRunningModeType.BACKGROUND);
    }

    @Override
    public void updateRegions(List deletedRegions, List newRegions) {
        if (!deletedRegions.isEmpty()) {
            List<Region> deleted = regionMapper.modelListToExternalClassList(deletedRegions);
            stopMonitoringRegions(deleted);
        }

        if (!newRegions.isEmpty()) {
            List<Region> added = regionMapper.modelListToExternalClassList(newRegions);
            startMonitoringRegions(added);
        }
    }
    // region RegionsProviderListener Interface

    @Override
    public void onRegionsReady(List<OrchextraRegion> regions) {
        List<Region> altRegions = regionMapper.modelListToExternalClassList(regions);
        this.regionsToBeMonitored.clear();
        this.regionsToBeMonitored.addAll(altRegions);
        startMonitoringRegions(altRegions);
    }

    private void startMonitoringRegions(final List<Region> altRegions) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Region region : altRegions) {
                    try {
                        beaconManager.startMonitoringBeaconsInRegion(region);
                        orchextraLogger.log("Start Beacons Monitoring for region " + region.getUniqueId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        monitoring = true;
    }

    private void stopMonitoringRegions(List<Region> altRegions) {
        try {
            for (Region region : altRegions) {
                beaconManager.stopMonitoringBeaconsInRegion(region);
                orchextraLogger.log("Stop Beacons Monitoring for region " + region.getUniqueId());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<Region> obtainRegionsInRange() {
        return regionsInEnter;
    }

    // endregion

    // endregion

}
