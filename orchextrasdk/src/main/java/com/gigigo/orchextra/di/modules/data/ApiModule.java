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

package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.executors.RetrofitApiServiceExcecutor;
import com.gigigo.orchextra.di.modules.data.qualifiers.AcceptLanguage;
import com.gigigo.orchextra.di.modules.data.qualifiers.ApiVersion;
import com.gigigo.orchextra.di.modules.data.qualifiers.Endpoint;
import com.gigigo.orchextra.di.modules.data.qualifiers.HeadersInterceptor;
import com.gigigo.orchextra.di.modules.data.qualifiers.XAppSdk;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;

import gigigo.com.orchextra.data.datasources.api.model.responses.base.GenericErrorOrchextraApiResponse;
import java.util.Locale;

import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.interceptors.Headers;
import gigigo.com.orchextra.data.datasources.api.service.DefatultErrorConverterImpl;
import gigigo.com.orchextra.data.datasources.api.service.DefaultRetryOnErrorPolicyImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;

import com.gigigo.orchextra.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    @Endpoint
    String provideEndpoint() {
        return BuildConfig.API_URL;
    }

    @Provides
    @Singleton
    @ApiVersion
    String provideApiVersion() {
        return BuildConfig.API_VERSION;
    }

    @Provides
    @Singleton
   
    @XAppSdk
    String provideXAppSdk() {
        return BuildConfig.X_APP_SDK + "_" + BuildConfig.VERSION_NAME;
    }

    @Provides
    @Singleton
    @AcceptLanguage
    String provideAcceptLanguage() {
        return Locale.getDefault().toString();
    }

    @Provides
    @Singleton Retrofit provideOrchextraRetrofitObject(
            @Endpoint String enpoint, @ApiVersion String version,
            GsonConverterFactory gsonConverterFactory, OkHttpClient okClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(enpoint + version)
                .client(okClient)
                .addConverterFactory(gsonConverterFactory).build();

        return retrofit;

    }

    @Provides
    @Singleton
    OrchextraApiService provideOrchextraApiService(Retrofit retrofit) {
        return retrofit.create(OrchextraApiService.class);
    }

    @Provides
    @Singleton HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    @Singleton
    @HeadersInterceptor Interceptor provideHeadersInterceptor(@XAppSdk String xAppSdk, @AcceptLanguage String acceptLanguage, Session session) {
        return new Headers(xAppSdk, acceptLanguage, session);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkClient(@HeadersInterceptor Interceptor headersInterceptor,
                                    HttpLoggingInterceptor loggingInterceptor,
                                    OrchextraLogger orchextraLogger) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(headersInterceptor);




        if (orchextraLogger.isNetworkLoggingLevelEnabled()){
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    ApiServiceExecutor provideApiServiceExecutor(ErrorConverter errorConverter,
                                                 RetryOnErrorPolicy retryOnErrorPolicy) {
        return new RetrofitApiServiceExcecutor.Builder()
                .errorConverter(errorConverter)
                .retryOnErrorPolicy(retryOnErrorPolicy).build();
    }

    @Provides
    @Singleton
    ErrorConverter provideErrorConverter(Retrofit retrofit) {
        return new DefatultErrorConverterImpl(retrofit, GenericErrorOrchextraApiResponse.class);
    }

    @Provides
    @Singleton
    RetryOnErrorPolicy provideRetryOnErrorPolicy() {
        return new DefaultRetryOnErrorPolicyImpl();
    }

}
