package com.example.location.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.location.BuildConfig;
import com.example.location.DeletePositionTask;
import com.example.location.UpdatePositionTask;
import com.example.location.JSONParser;
import com.example.location.databinding.FragmentDashboardBinding;

import org.json.JSONObject;

import java.util.HashMap;
import org.json.JSONException;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private JSONParser jsonParser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context con = getActivity();
        jsonParser = new JSONParser(); // Initialize JSONParser for API requests

        Bundle args = getArguments();
        if (args != null) {
            final String positionId = args.getString("positionId");
            String name = args.getString("name");
            String number = args.getString("number");
            String createdAt = args.getString("createdAt");
            String updatedAt = args.getString("updatedAt");
            final double latitude = args.getDouble("latitude");
            final double longitude = args.getDouble("longitude");
            boolean isFavorite = args.getBoolean("isFavorite");
            String serverUrl = BuildConfig.SERVER_URL;

            // Set current position details in the UI
            binding.nameId.setText(name);
            binding.latitudeId.setText(String.valueOf(latitude));
            binding.longitudeId.setText(String.valueOf(longitude));
            binding.createdAtId.setText(createdAt);
            binding.updatedAtId.setText(updatedAt);

            Button updateButton = binding.updateButton;
            updateButton.setOnClickListener(v -> {
                String updatedName = binding.nameId.getText().toString();
                String updatedLatitude = binding.latitudeId.getText().toString();
                String updatedLongitude = binding.longitudeId.getText().toString();

                // Create a HashMap with updated data
                HashMap<String, String> updatedData = new HashMap<>();
                updatedData.put("name", updatedName);
                updatedData.put("latitude", updatedLatitude);
                updatedData.put("longitude", updatedLongitude);

                // Serialize HashMap to JSON String
                JSONObject jsonObject = new JSONObject(updatedData);
                String dataMapString = jsonObject.toString();

                // Prepare URL
                String url = serverUrl+"/position";

                // Execute the AsyncTask with all parameters
                new UpdatePositionTask(jsonParser, con).execute(url, positionId, dataMapString);
            });

            // Add delete button functionality
            Button deleteButton = binding.deleteButton;
            deleteButton.setOnClickListener(v -> {
                // Prepare URL for DELETE request
                String deleteUrl = serverUrl+"/position/";

                // Execute the DELETE request
                new DeletePositionTask(jsonParser,con).execute(deleteUrl,positionId);
            });

            // Set the favorite button text based on current status
            Button favoriteButton = binding.favoriteButton;
            Log.d("isFavoriteeeeeeeee", "Favorite status: " + isFavorite);
            if (isFavorite) {
                favoriteButton.setText("Unfavorite");
            } else {
                favoriteButton.setText("Favorite");
            }

            // Use a mutable wrapper for isFavorite
            final boolean[] isFavoriteWrapper = {isFavorite};

            favoriteButton.setOnClickListener(v -> {
                // Toggle the favorite status
                boolean updatedFavoriteStatus = !isFavoriteWrapper[0]; // Access the mutable wrapper
                //Log.d("DashboardFragment", "Favorite status: " + updatedFavoriteStatus);
                // Update the favorite button text immediately
                favoriteButton.setText(updatedFavoriteStatus ? "Unfavorite" : "Favorite");

                // Create a HashMap with the updated favorite status
                HashMap<String, String> updatedData = new HashMap<>();
                updatedData.put("isFavorite", String.valueOf(updatedFavoriteStatus));
                System.out.println(String.valueOf(updatedFavoriteStatus));
                // Serialize HashMap to JSON String
                JSONObject jsonObject = new JSONObject(updatedData);
                String dataMapString = jsonObject.toString();

                // Prepare URL for the specific position
                String url = serverUrl+"/position/" ;

                // Execute the AsyncTask to update the position as favorite/unfavorite
                new UpdatePositionTask(jsonParser,con).execute(url, positionId, dataMapString);

                // Update the local state of isFavorite in the wrapper
                isFavoriteWrapper[0] = updatedFavoriteStatus;
            });

        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
