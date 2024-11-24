package com.example.location.ui.notifications;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.BuildConfig;
import com.example.location.FavoritesAdapter;
import com.example.location.JSONParser;
import com.example.location.Position;
import com.example.location.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<Position> favoritePositions;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recycler_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoritePositions = new ArrayList<>();
        Context co = getActivity();
        adapter = new FavoritesAdapter(favoritePositions,co);
        recyclerView.setAdapter(adapter);

        // Fetch favorite positions
        new FetchFavoritePositionsTask().execute();

        return root;
    }

    private class FetchFavoritePositionsTask extends AsyncTask<Void, Void, JSONArray> {
        String serverUrl = BuildConfig.SERVER_URL;
        String url = serverUrl+"/position/favorites" ;

        @Override
        protected JSONArray doInBackground(Void... voids) {
            JSONParser jsonParser = new JSONParser();
            return jsonParser.makeArrayRequest(url);
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            if (result != null) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject position = result.getJSONObject(i);
                        String id = position.getString("_id");
                        String name = position.getString("name");
                        double latitude = position.getDouble("latitude");
                        double longitude = position.getDouble("longitude");
                        String createdAt = position.getString("createdAt");  // Parse createdAt
                        String updatedAt = position.getString("updatedAt");  // Parse updatedAt

                        favoritePositions.add(new Position(name, latitude, longitude, id, createdAt, updatedAt));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh RecyclerView
            }
        }
    }
        }


