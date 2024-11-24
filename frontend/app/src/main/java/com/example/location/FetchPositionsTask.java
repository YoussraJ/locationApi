package com.example.location;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FetchPositionsTask extends AsyncTask<Void, Void, JSONArray> {
    String serverUrl = BuildConfig.SERVER_URL;
    private GoogleMap mMap;
    private  final String BASE_URL = serverUrl+"/position";  // Backend API URL
    private JSONParser jsonParser;
    private Context con;
    public FetchPositionsTask(GoogleMap map, Context con) {
        this.mMap = map;
        this.jsonParser = new JSONParser(); // Initialize the JSONParser
        this.con = con;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {

        Log.d("FetchPositionsTask", "Base URL: " + BASE_URL);

        // Use JSONParser to fetch the positions as a JSONArray
        return jsonParser.makeArrayRequest(BASE_URL);
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);

        if (result != null) {
            BitmapDescriptor customCircleIcon = createCircleMarker(15, Color.RED);
            List<LatLng> routePoints = new ArrayList<>();

            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject position = result.getJSONObject(i);
                    String positionName = position.getString("name");
                    String positionId = position.getString("_id");
                    double latitude = position.getDouble("latitude");
                    double longitude = position.getDouble("longitude");
                    String createdAt = position.getString("createdAt");  // Assuming the backend provides these
                    String updatedAt = position.getString("updatedAt");  // Assuming the backend provides these
                    boolean isFavorite1 = position.getBoolean("isFavorite");
                    LatLng positionLatLng = new LatLng(latitude, longitude);
                    routePoints.add(positionLatLng);

                    // Create a Marker and associate the positionId using setTag
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(positionLatLng)
                            .title(positionName)
                            .icon(customCircleIcon);


                    // Add the marker to the map
                    com.google.android.gms.maps.model.Marker marker = mMap.addMarker(markerOptions);
                    if (marker != null) {
                        Bundle markerData = new Bundle();
                        markerData.putString("positionId", positionId);
                        markerData.putString("createdAt", createdAt);
                        markerData.putString("updatedAt", updatedAt);
                        //boolean isFavorite = markerData.getBoolean(isFavorite);
                        markerData.putBoolean("isFavorite", isFavorite1);
                        // Set the marker's tag to the Bundle containing additional data
                        marker.setTag(markerData);

                    }

                    if (i == 0) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 8));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Draw the route polyline
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(routePoints)
                    .color(Color.BLUE)
                    .width(5);
            mMap.addPolyline(polylineOptions);
        } else {
            Log.e("FetchPositionsTask", "No positions data found.");
        }

        mMap.setOnMarkerClickListener(marker -> {
            // Retrieve the tag, which contains the Bundle with all data
            Bundle markerData = (Bundle) marker.getTag();
            if (markerData != null) {
                // Extract data from the Bundle
                String positionId = markerData.getString("positionId");
                String createdAt = markerData.getString("createdAt");
                String updatedAt = markerData.getString("updatedAt");
                String name = marker.getTitle();
                double latitude = marker.getPosition().latitude;
                double longitude = marker.getPosition().longitude;
                boolean isFavorite = markerData.getBoolean("isFavorite");
                //boolean isFavorite = Boolean.parseBoolean(isFavoritee);

                Log.d("isFavoriteeeee1111eeee", "Favorite status: " + isFavorite);
                // Create a new Bundle to pass data
                Bundle bundle = new Bundle();
                bundle.putString("positionId", positionId);
                bundle.putString("name", name);
                bundle.putString("createdAt", createdAt);
                bundle.putString("updatedAt", updatedAt);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putBoolean("isFavorite", isFavorite);
                // Cast the context to Activity for Navigation
                Activity activity = (Activity) con;
                Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main)
                        .navigate(R.id.navigation_dashboard, bundle,
                                new NavOptions.Builder().setPopUpTo(R.id.navigation_home, true).build());

            }
            return true; // Return true to indicate the event is handled
        });
    }


    private BitmapDescriptor createCircleMarker(int radius, int color) {
        Bitmap bitmap = Bitmap.createBitmap(2 * radius, 2 * radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        canvas.drawCircle(radius, radius, radius, paint);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
