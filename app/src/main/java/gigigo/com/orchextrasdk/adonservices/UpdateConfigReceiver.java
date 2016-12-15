package gigigo.com.orchextrasdk.adonservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.gigigo.orchextra.Orchextra;

/**
 * Created by nubor on 16/11/2016.
 */
public class UpdateConfigReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("onReceive", "onReceive");
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
           // Toast.makeText(context, "hola post reinicio registro el LocationUpdater, para q sea sensible a eio", Toast.LENGTH_LONG).show();
            UpdateConfigUtility updater = new UpdateConfigUtility(context);
            updater.createUpdateConfigurationByTime(60*1000*60);
        } else {
            if(UpdateConfigUtility.ACTION_REFRESH_CONFIG.equals(intent.getAction())) {
                System.out.println("ACTION" + intent.getAction());
                System.out.println("All intent" + intent.toString());
                Toast.makeText(context, "Llamada al configuration", Toast.LENGTH_LONG).show();
                System.out.println("refreshConfigurationInBackground");
                Orchextra.refreshConfigurationInBackground(context);
            }
            else
            {
                System.out.println("Llamada a UpdateConfigReceiver ACTION->" + intent.getAction());
                Toast.makeText(context, "NO LLAMADA, ESTO QUIZA SEA POR EL STYCKY", Toast.LENGTH_LONG).show();
            }
        }
    }
}
