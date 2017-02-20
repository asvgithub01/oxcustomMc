package gigigo.com.orchextrasdk;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.appoxee.Appoxee;
//import com.appoxee.AppoxeeObserver;
//import com.appoxee.asyncs.initAsync;
import com.gigigo.orchextra.CustomSchemeReceiver;
//import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.ui.webview.OxWebViewActivity;

import gigigo.com.orchextrasdk.adonservices.UpdateConfigReceiver;
import gigigo.com.orchextrasdk.adonservices.UpdateConfigUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onPause() {
        super.onPause();
    //   App.mMotionServiceUtility.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("APP", "Hello MainActivity, start onCreate");
        //orchextraSDK
        getViews();
        setListeners();

        //appoxeeSDK
        // startAppoxee();
        Orchextra.start();

       // App.mMotionServiceUtility.stop();

        //we only program updater if the OX are in start mode
        UpdateConfigUtility updater = new UpdateConfigUtility(MainActivity.this);
        updater.createUpdateConfigurationByTime(60 * 1000 * 60);
        enablerUpdateConfigReBootService(MainActivity.this.getApplication(), true);
    }


    //region Orchextra
    private boolean isRunning = false;
    private Button button, button2, button3, button4, button5;
    private TextView statusText;

    //region getViews/Buttons onClick
    private void getViews() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        statusText = (TextView) findViewById(R.id.statusText);
    }

    private void setListeners() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String TEST_STREAM_URL = "http://research.gigigo.com";
        final String CUSTOM_SCHEME = "webview://";

        if (v.getId() == R.id.button) {
            Orchextra.startImageRecognition();
        }
        if (v.getId() == R.id.button2) {
            Orchextra.startScannerActivity();
        }
        if (v.getId() == R.id.button3) {
            if (isRunning) {
                stopOrchextra();
            } else {
                startOrchextra();
            }
        }
        //region orchextraWebview

        if (v.getId() == R.id.button4) {
            OxWebViewActivity.open(this, TEST_STREAM_URL, false);
            Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    if (scheme.contains(CUSTOM_SCHEME)) {
                        String url = scheme.replace(CUSTOM_SCHEME, "");
                        OxWebViewActivity.navigateUrl(url);
                    }
                }
            });
        }
        //endregion

        //region orchextraWebview in app activity
        if (v.getId() == R.id.button5) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", TEST_STREAM_URL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        //endregion
    }

    //endregion
    private void startOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.start();
                //UpdateConfigUtility ONly when Start
                UpdateConfigUtility updater = new UpdateConfigUtility(MainActivity.this);
                updater.createUpdateConfigurationByTime(60000, 1000);//update if move 500m.
                enablerUpdateConfigReBootService(MainActivity.this.getApplication(), true);


            }
        });

        isRunning = true;
        button3.setText(R.string.ox_stop_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_running)));
    }

    private void stopOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //  Orchextra.stop();
            }
        });
        isRunning = false;
        button3.setText(R.string.ox_start_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_stoped)));
    }

    private void enablerUpdateConfigReBootService(Application application, boolean bState) {
        int componentEnabledState;
        if (!bState) {
            componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        } else {
            componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }
        ComponentName component = new ComponentName(application, UpdateConfigReceiver.class);
        application.getPackageManager().setComponentEnabledSetting(component, componentEnabledState, PackageManager.DONT_KILL_APP);
    }


}
