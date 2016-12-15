package gigigo.com.orchextrasdk.adonservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by nubor on 16/11/2016.
 */
//lo creo aqki para q el copy y paste sea aun mas sencillo, ya q si lo meto como metodo al app q es dnd acabará
//el tema de imports y todo eso queda menos claro.Simplemente x ello, no more ;)

public class UpdateConfigUtility {
    //todo este contexto se debe referir a una Appcompat ya que lo utiliza para preguntar por los permisos
    Context mContext;
    private AlarmManager alarm;
    public final static String ACTION_REFRESH_CONFIG = "android.intent.action.REFRESH_CONFIG";

    public UpdateConfigUtility(Context mContext) {
        this.mContext = mContext;
        alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    /***
     * @param eachMiliseconds long: minimum time interval between location updates, in milliseconds, recomendede >60000ms
     * @param mindistance     float: minimum distance between location updates, in meters
     */
    public void createUpdateConfigurationByTime(long eachMiliseconds, float mindistance) {
        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            Intent intent = new Intent(mContext, UpdateConfigReceiver.class);
            intent.setAction(ACTION_REFRESH_CONFIG);
            PendingIntent BroadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "PROBLEMA CON LOS PERMISOS!!, NO ESTÁN HABILITADOS, QUIZÁS NUNCA SE HIZO UN START", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mContext, "Servicio suscrito correctamente", Toast.LENGTH_SHORT).show();
            //todo podemos agregar otros provider mas, xo ello tb implica la peition de permisos de momento recae en la app, asi
            //no more por el momento tiramos   con--> GPS_PROVIDER
            //passive provider
            System.out.println("BROADCAST Suscribe!!");

            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, eachMiliseconds, mindistance, BroadCastPending);

        } catch (Exception e) {
            e.printStackTrace();

            // Toast.makeText(mContext, "OUCH! algo ha ido malamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void createUpdateConfigurationByTime(long time) {
        Intent intent = new Intent(mContext, UpdateConfigReceiver.class);
        intent.setAction(ACTION_REFRESH_CONFIG);
        PendingIntent broadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, broadCastPending); // cada 30 segundos
        mContext.startService(intent);

    }
}
