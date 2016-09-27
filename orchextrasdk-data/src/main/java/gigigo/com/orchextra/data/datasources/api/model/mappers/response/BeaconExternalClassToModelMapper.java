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

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiRegion;


public class BeaconExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiRegion, OrchextraRegion> {

  @Override public OrchextraRegion externalClassToModel(ApiRegion apiRegion) {

    OrchextraRegion region =
        new OrchextraRegion(apiRegion.getCode(), apiRegion.getUuid(),
            (apiRegion.getMajor() != null) ? apiRegion.getMajor() : -1,
            (apiRegion.getMinor() != null) ? apiRegion.getMinor() : -1,
            apiRegion.getActive());

    //Set supper fields
    region.setNotifyOnEntry(apiRegion.getNotifyOnEntry());
    region.setNotifyOnExit(apiRegion.getNotifyOnExit());
    region.setTags(apiRegion.getTags());

    return region;
  }
}
