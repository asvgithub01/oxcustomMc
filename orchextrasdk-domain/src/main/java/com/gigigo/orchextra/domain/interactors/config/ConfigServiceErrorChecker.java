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

package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.error.OrchextraBusinessErrors;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

public class ConfigServiceErrorChecker extends ServiceErrorChecker {

  public ConfigServiceErrorChecker(AuthenticationService authenticationService) {
    super(authenticationService);
  }

  @Override protected InteractorResponse checkConcreteException(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }

  @Override protected InteractorResponse checkConcreteBusinessErrors(BusinessError businessError) {

    OrchextraBusinessErrors error =
        OrchextraBusinessErrors.getEnumTypeFromInt(businessError.getCode());

    if (error == OrchextraBusinessErrors.VALIDATION_ERROR) {
      return new InteractorResponse(new ValidationError(businessError));
    } else {
      return new InteractorResponse(new GenericError(businessError));
    }
  }
}
