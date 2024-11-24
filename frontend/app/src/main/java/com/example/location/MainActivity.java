package com.example.location;

import android.annotation.SuppressLint;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.location.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Location variables
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    String serverUrl = BuildConfig.SERVER_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Server URL", serverUrl);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up BottomNavigationView and NavController
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Initialize Location Client and start tracking
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            setupLocationTracking();
        }
    }
    private android.location.Location lastSavedLocation = null; // Dernière position sauvegardée
    private long lastSavedTime = 0; // Dernier moment où la position a été sauvegardée

    @SuppressLint("MissingPermission")
    private void setupLocationTracking() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000); // Intervalle des mises à jour (1 minute)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Haute précision

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                for (android.location.Location currentLocation : locationResult.getLocations()) {
                    double latitude = currentLocation.getLatitude();
                    double longitude = currentLocation.getLongitude();

                    long currentTime = System.currentTimeMillis(); // Temps actuel en millisecondes

                    // Vérifier si 1 minute est passée OU si l'utilisateur a parcouru plus de 100 mètres
                    boolean isTimeConditionMet = (currentTime - lastSavedTime) >= 60000; // 1 minute
                    boolean isDistanceConditionMet = lastSavedLocation == null ||
                            currentLocation.distanceTo(lastSavedLocation) >= 100; // Plus de 100 mètres

                    if (isTimeConditionMet || isDistanceConditionMet) {
                        // Mettre à jour la dernière position et l'heure
                        lastSavedLocation = currentLocation;
                        lastSavedTime = currentTime;

                        Log.d("LocationTracking", "Position sauvegardée: Lat=" + latitude + ", Long=" + longitude);

                        // Sauvegarder la position sur le serveur
                        saveLocationToServer(latitude, longitude);
                    } else {
                        Log.d("LocationTracking", "Conditions non remplies : Position non sauvegardée.");
                    }
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void saveLocationToServer(double latitude, double longitude) {
        new Thread(() -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", "Moi");
            params.put("latitude", String.valueOf(latitude));
            params.put("longitude", String.valueOf(longitude));

            JSONParser parser = new JSONParser();
            try {
                String url = serverUrl+"/position";
                parser.makeHttpRequest(serverUrl+"/position", "POST", params);
                runOnUiThread(() -> Log.d("LocationTracking", "Position envoyée avec succès au serveur"));
            } catch (Exception e) {
                runOnUiThread(() -> Log.e("LocationTracking", "Échec de l'envoi de la position: " + e.getMessage()));
            }
        }).start();
    }


   /* @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates when the activity is not visible
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        // Restart location updates when the activity becomes visible again
        if (locationRequest != null) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }*/
}
