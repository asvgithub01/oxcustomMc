package com.gigigo.orchextra;

public interface OrchextraCompletionCallback {
    void onSuccess();

    void onError(String s);

    void onInit(String s);
}
