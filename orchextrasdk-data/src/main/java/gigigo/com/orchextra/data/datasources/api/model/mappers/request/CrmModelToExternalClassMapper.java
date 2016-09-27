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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.gggjavalib.general.utils.DateFormatConstants;
import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmTag;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiCrmUser;
import java.util.ArrayList;
import java.util.List;

public class CrmModelToExternalClassMapper implements ModelToExternalClassMapper<CrmUser, ApiCrmUser> {

  @Override public ApiCrmUser modelToExternalClass(CrmUser crmUser) {

    ApiCrmUser apiCrmUser = new ApiCrmUser();

    apiCrmUser.setBirthDate(
        DateUtils.dateToStringWithFormat(crmUser.getBirthDate(), DateFormatConstants.DATE_FORMAT_NO_TIME));
    apiCrmUser.setCrmId(crmUser.getCrmId());

    //TODO change all if null with Checker from utils
    if (crmUser.getGender() != null) {
      apiCrmUser.setGender(crmUser.getGender().getStringValue());
    }

    return apiCrmUser;
  }

}
