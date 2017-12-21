package com.example.gregoryjones.project2;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final long TIME_INTERVAL_UPDATES =1000;
    private static final float DISTANCE_FOR_UPDATES = 1;
    private LocationManager gpsManager;




    final String key_two = "gh43P+CBJRREW21+HJ21233";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gpsManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            Log.d(getClass().getSimpleName(), "Requested for permission");

            return;
        }
        gpsManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                TIME_INTERVAL_UPDATES,
                DISTANCE_FOR_UPDATES,
                new MyGPSListener());
        final TextView tv = (TextView) findViewById(R.id.textView_GpsInformationDisplayed);
        final String[] gps_data = new String[1];
        Button bt = (Button) findViewById(R.id.button_UpdateGPSInformation);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

                    return;
                }

                Location location = gpsManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    gps_data[0] = String.format("Last Known GPS: \n Longitude: %1$s \n Latitude: %2$s \n Altitude: %2$s",
                            location.getLongitude(), location.getLatitude(), location.getAltitude());
                    tv.setText(gps_data[0]);
                }
            }

        });
        Button bt2 = (Button) findViewById(R.id.button_EncryptAndDecryptByAES);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("GPS DATA", gps_data[0]);
                startActivity(intent);
            }
        });

        Button bt3 = (Button) findViewById(R.id.button_EncryptAndDecryptByXOR);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                intent.putExtra("GPS DATA", gps_data[0]);
                startActivity(intent);
            }
        });
        Button bt4 = (Button) findViewById(R.id.button_AesWithKeyModifier);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)  findViewById(R.id.editText_TypeString);
                String Users = et1.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                intent.putExtra("Users", Users);
                // intent.putExtra("text", et.getText().toString());
                intent.putExtra("GPS DATA", gps_data[0]);
                startActivity(intent);
            }
        });
        Button bt5 = (Button) findViewById(R.id.button_XorWithKeyModifier);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)  findViewById(R.id.editText_TypeString);
                String theText = et.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity5.class);
                intent.putExtra("text", theText);
                intent.putExtra("GPS DATA", gps_data[0]);
                startActivity(intent);

                ;
                //intent.putExtra("GPS DATA", gps_data[0]);

            }
        });


    }

    private class MyGPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            String gps_data = String.format("Last Known GPS: \n Longitude: %1$s \n Latitude: %2$s \n Altitude: %2$s",
                    location.getLongitude(),
                    location.getLatitude(),
                    location.getAltitude());
            Toast.makeText(MainActivity.this, gps_data, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(MainActivity.this, "Status Changed", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, " GPS is enabled", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "GPS is disabled", Toast.LENGTH_LONG).show();

        }
    }
}


