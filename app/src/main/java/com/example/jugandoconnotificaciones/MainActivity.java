package com.example.jugandoconnotificaciones;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Button buttonnotificacion, button2, button3, buttonImagen;
    String ID_CANAL = "mi canal favorito";
    int CODIGO_RESPUESTA = 1;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonnotificacion = findViewById(R.id.buttonNotificacion);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonImagen = findViewById(R.id.buttonImagen);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorManager.SENSOR_DELAY_NORMAL);


        buttonnotificacion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //lanzarNotificacion("Ejemplo de texto simple");
                prueba("Ejemplo de texto simple");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNotificacionConTextoLargo();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNotificacionConMuchoTexto();
            }
        });
        buttonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNotificacionConImagen();
            }
        });

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("mensaje1")) {
                String msg = extras.getString("mensaje1");

                //tvNotify.setText(msg) ;
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void lanzarNotificacionConImagen() {

        int notifyId = 4;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Ejemplo de notificación con más Foto")
                        .setAutoCancel(true)
                        .setContentText("Texto inicial para la foto");


        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.barco)).build();

        builder.setStyle(bigPictureStyle);

        Intent intent = getIntent();
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "4";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(notifyId, builder.build());
    }

    private void lanzarNotificacionConMuchoTexto() {

        int notifyId = 3;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Ejemplo de notificación con más texto")
                        .setAutoCancel(true)
                        .setContentText("Texto inicial.");


        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

        bigTextStyle.bigText("La conquista del Petén fue la última etapa de la conquista de Guatemala, un conflicto prolongado que se produjo durante la colonización española de América. El Petén es una amplia planicie de tierras bajas cubiertas de una densa selva tropical, e incluye una cuenca hidrográfica central con varios lagos y algunas zonas de sabana. La llanura está atravesada por una serie de colinas kársticas bajas, que se elevan hacia el sur al acercarse al altiplano de Guatemala. La conquista del Petén, una región ahora incorporada a la república de Guatemala, culminó en 1697 con la captura de Nojpetén (también conocido como Tayasal), la capital del reino itzá, por Martín de Urzúa y Arizmendi. Con la derrota de los itzaes, los colonizadores europeos sometieron al último reino nativo independiente e invicto del continente americano.\n" +
                "\n" +
                "Antes de la conquista, el Petén contaba con una población considerable, conformada de diferentes pueblos mayas, en particular alrededor de los lagos centrales y a lo largo de los ríos. Petén estaba dividido en diferentes señoríos mayas envueltos en una compleja red de alianzas y enemistades. Los grupos más importantes alrededor de los lagos centrales eran los itzaes, yalain y couohes. Otros grupos cuyos territorios se encontraban en el Petén eran los quejaches, acalas, choles del Lacandón, xocmós, chinamitas, icaichés y choles del Manché.");
        bigTextStyle.setSummaryText("La conquista del petén");


        builder.setStyle(bigTextStyle);

        Intent intent = getIntent();
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "3";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(notifyId, builder.build());


    }

    private void lanzarNotificacionConTextoLargo() {

        int notifyId = 2;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Ejemplo de notificación con más texto")
                        .setAutoCancel(true)
                        .setContentText("Texto inicial.");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Este mensaje tiene más cosas");

        inboxStyle.addLine("una línea nueva");
        inboxStyle.addLine("Otro mensajito de mi amiguito");
        inboxStyle.addLine("Pedro estate atento");
        inboxStyle.addLine("Pedro deja de bostezar");
        inboxStyle.addLine("Sergio te estoy vigilando");

        builder.setStyle(inboxStyle);

        Intent intent = getIntent();
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, CODIGO_RESPUESTA, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "2";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(notifyId, builder.build());
    }


    private void lanzarNotificacion(String cadena) {

        //Id para la notificación
        int notifyId = 1;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Esto
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent, 0);
        //Hasta aquí he metido yo

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, ID_CANAL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Notificación simple")
                        .setAutoCancel(true)
                        .setContentText(cadena)
                        .setContentIntent(pendingIntent2);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        } else {
            String idChanel = "1";
            String nombreCanal = "micanal";

            NotificationChannel channel = new NotificationChannel(idChanel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            builder.setChannelId(idChanel);
            notificationManager.createNotificationChannel(channel);

        }


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
        taskStackBuilder.addNextIntent(getIntent());
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(CODIGO_RESPUESTA, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);


        notificationManager.notify(notifyId, builder.build());
    }

    private void prueba(String cadena) {

        int notifyId = 1;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("mensaje1", "Notificación simple");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent resultIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder
                (MainActivity.this, ID_CANAL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notificación simple")
                .setContentText(cadena)
                .setContentIntent(resultIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new
                    NotificationChannel(ID_CANAL, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(ID_CANAL);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (event.values[0] == 0) {
                lanzarNotificacion("Sensor de luz tapado");

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
