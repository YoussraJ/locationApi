package com.example.location.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.location.BuildConfig;
import com.example.location.FetchPositionsTask;
import com.example.location.JSONParser;
import com.example.location.R;
import com.example.location.databinding.FragmentHomeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private boolean isAddingPosition = false;
    JSONParser jsonParser = new JSONParser(); // Initialize the JSONParser
    String serverUrl = BuildConfig.SERVER_URL;
    private  final String BASE_URL = serverUrl+"/position";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Use View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Button to trigger adding position
        binding.btnAdd.setOnClickListener(v -> {
            isAddingPosition = true;  // Set flag to true when "Add" button is clicked
        });

        // Manually retrieve the SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* mMap = googleMap;

        // Add a marker at a sample location and move the camera
        LatLng sampleLocation = new LatLng(34.052235, -118.243683); // Example coordinates (Los Angeles)
        mMap.addMarker(new MarkerOptions().position(sampleLocation).title("Sample Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sampleLocation, 12));*/
        mMap = googleMap;
        // Set the map click listener to capture the position when "Add" is active
        mMap.setOnMapClickListener(latLng -> {
            if (isAddingPosition) {
                // Add marker and save position
                mMap.addMarker(new MarkerOptions().position(latLng).title("New Position"));
                savePosition(latLng);
                isAddingPosition = false;  // Reset the flag after saving position
            }
        });
        Context co = getActivity();
        // Fetch positions from the backend API
        new FetchPositionsTask(mMap, co).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @SuppressLint("StaticFieldLeak")
    private void savePosition(LatLng latLng) {
        // Prepare data to send to the backend
        JSONObject positionData = new JSONObject();
        try {
            positionData.put("latitude", latLng.latitude);
            positionData.put("longitude", latLng.longitude);
            positionData.put("name", "moi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a new AsyncTask to send data to the backend
        new AsyncTask<JSONObject, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(JSONObject... params) {
                // Convert the JSONObject to a HashMap<String, String>
                HashMap<String, String> paramMap = new HashMap<>();
                if (params != null && params.length > 0) {
                    JSONObject jsonObject = params[0];
                    Iterator<String> keys = jsonObject.keys();

                    while (keys.hasNext()) {
                        String key = keys.next();
                        try {
                            paramMap.put(key, jsonObject.getString(key)); // Add each key-value pair to the HashMap
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Now use the makeHttpRequest method with the HashMap
                JSONObject response = jsonParser.makeHttpRequest(BASE_URL, "POST", paramMap);

                // You can return a Boolean based on the response or handle it accordingly
                return response != null;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Log.d("HomeFragment", "Position sent successfully to the server");
                } else {
                    Log.e("HomeFragment", "Failed to send position to the server");
                }
            }
        }.execute(positionData);
    }

}
