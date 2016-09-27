package com.gigigo.orchextra.control.controllers.status;

import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusManager;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.services.status.ClearOrchextraCredentialsDomainService;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraDomainServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraDomainServiceStatus;

public class OrchextraStatusManagerImpl implements OrchextraStatusManager {

    private final Session session;
    private final LoadOrchextraDomainServiceStatus loadOrchextraDomainServiceStatus;
    private final UpdateOrchextraDomainServiceStatus updateOrchextraDomainServiceStatus;
    private final ClearOrchextraCredentialsDomainService clearOrchextraCredentialsDomainService;

    private OrchextraStatus orchextraStatus = null;

    public OrchextraStatusManagerImpl(Session session,
                                      LoadOrchextraDomainServiceStatus loadOrchextraDomainServiceStatus,
                                      UpdateOrchextraDomainServiceStatus updateOrchextraDomainServiceStatus,
                                      ClearOrchextraCredentialsDomainService clearOrchextraCredentialsDomainService) {
        this.session = session;
        this.loadOrchextraDomainServiceStatus = loadOrchextraDomainServiceStatus;
        this.updateOrchextraDomainServiceStatus = updateOrchextraDomainServiceStatus;
        this.clearOrchextraCredentialsDomainService = clearOrchextraCredentialsDomainService;
    }

    @Override
    public void setInitialized(boolean isInitialized) {
        loadOrchextraStatus();
        orchextraStatus.setInitialized(true);
        updateOrchextraStatus();
    }

    @Override
    public void changeSdkCredentials(String apiKey, String apiSecret) {
        clearSdkCredentials();

        loadOrchextraStatus();
        session.setAppParams(apiKey, apiSecret);
        session.setTokenString(null);
        orchextraStatus.setSession(session);
        updateOrchextraStatus();
    }

    @Override
    public void setSDKstatusAsStarted() {
        loadOrchextraStatus();
        orchextraStatus.setStarted(true);
        updateOrchextraStatus();
    }

    @Override
    public boolean isInitialized() {
        loadOrchextraStatus();
        return orchextraStatus.isInitialized();
    }

    @Override
    public boolean areCredentialsEquals(String startApiKey, String startApiSecret) {
        loadOrchextraStatus();
        String currentApiKey = orchextraStatus.getSession().getApiKey();
        String currentApiSecret = orchextraStatus.getSession().getApiSecret();
        return currentApiKey.equals(startApiKey) && currentApiSecret.equals(startApiSecret);
    }

    @Override
    public boolean isStarted() {
        loadOrchextraStatus();
        return orchextraStatus.isStarted();
    }

    @Override
    public boolean hasCredentials() {
        loadOrchextraStatus();
        String apiKey = orchextraStatus.getSession().getApiKey();
        String apiSecret = orchextraStatus.getSession().getApiSecret();
        return (!apiKey.isEmpty() && !apiSecret.isEmpty());
    }

    @Override
    public void setStoppedStatus() {
        loadOrchextraStatus();
        if (orchextraStatus.isStarted()) {
            orchextraStatus.setStarted(false);
            updateOrchextraStatus();
        }
    }


    // region domain services

  /*
    The following two operations were thought at the beginning to be executed in a separate thread
    using interactor executor, but we had to wait till the end of them to continue, this fact was not
    blocking the UI but was creating a dependency between threads that was making compulsory to use
    more than one threads in the thread poll. Regarding both operations are really fast, they are
    not always performed, and code is much cleaner, has been taken the decision of perform them
    on Ui thread. The previous implementation was at splitStartInit branch with a tag named
    Load_update_OxStatus_futures. Consider recovery of thar implementation and make it some
    improvement if this operations become much more expensive.
   */

    private OrchextraStatus loadOrchextraStatus() {
        if (orchextraStatus == null) {
            InteractorResponse<OrchextraStatus> response = loadOrchextraDomainServiceStatus.load();
            if (!response.hasError()) {
                this.orchextraStatus = response.getResult();
            }
        }
        return orchextraStatus;
    }

    private void updateOrchextraStatus() {
        InteractorResponse<OrchextraStatus> response =
                updateOrchextraDomainServiceStatus.update(orchextraStatus);
        if (!response.hasError()) {
            this.orchextraStatus = response.getResult();
        }
    }

    private void clearSdkCredentials() {
        clearOrchextraCredentialsDomainService.clearSdkCredentials();
    }

    //endregion


    //region TODO
    //TODO move CRM and Session management here
    //@Override public Session getSession() {
    //  //TODO
    //  return null;
    //}
    //
    //@Override public void updateSession(Session session) {
    //  //TODO
    //}
    //
    //@Override public CrmUser getCrmUser() {
    //  //TODO
    //  return null;
    //}
    //
    //@Override public void updateCrm(CrmUser crm) {
    //  //TODO
    //}
    // endregion
}
