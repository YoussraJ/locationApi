package com.example.location;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.ui.dashboard.DashboardFragment;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {


    private List<Position> favoritePositions;
    private Context con;
    public FavoritesAdapter(List<Position> favoritePositions,  Context con) {
        this.favoritePositions = favoritePositions;
        this.con = con;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holderr, parent, false); // Ensure holderr.xml is the correct layout
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        Position currentPosition = favoritePositions.get(position);
        holder.nameTextView.setText(currentPosition.getName());
        holder.coordinatesTextView.setText(
                String.format("Lat: %.2f, Lon: %.2f", currentPosition.getLatitude(), currentPosition.getLongitude()));
        holder.createdAtTextView.setText("Created At: " + currentPosition.getCreatedAt());
        holder.updatedAtTextView.setText("Updated At: " + currentPosition.getUpdatedAt());

        // Set an OnClickListener on the card item
        holder.itemView.setOnClickListener(v -> {
            // Create a Bundle and pass the position details
            Bundle bundle = new Bundle();
            bundle.putString("positionId", currentPosition.getId());
            bundle.putString("name", currentPosition.getName());
           // bundle.putString("number", currentPosition.getNumber());
            bundle.putString("createdAt", currentPosition.getCreatedAt());
            bundle.putString("updatedAt", currentPosition.getUpdatedAt());
            bundle.putDouble("latitude", currentPosition.getLatitude());
            bundle.putDouble("longitude", currentPosition.getLongitude());
            bundle.putBoolean("isFavorite", currentPosition.isFavorite());

            // Create a new instance of DashboardFragment and set the arguments
            DashboardFragment dashboardFragment = new DashboardFragment();
            dashboardFragment.setArguments(bundle);

            Activity activity = (Activity) con;
            Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main)
                    .navigate(R.id.navigation_dashboard, bundle,
                            new NavOptions.Builder().setPopUpTo(R.id.navigation_home, true).build());

        });
    }



    @Override
    public int getItemCount() {
        return favoritePositions.size(); // Return the size of the list
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView coordinatesTextView;
        TextView createdAtTextView;  // Use this for createdAt
        TextView updatedAtTextView;  // Use this for updatedAt

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews from the holderr.xml layout
            nameTextView = itemView.findViewById(R.id.text_name);  // Replace with correct ID
            coordinatesTextView = itemView.findViewById(R.id.text_detail);  // Replace with correct ID
            createdAtTextView = itemView.findViewById(R.id.text_createdAt);  // Correctly initialize createdAtTextView
            updatedAtTextView = itemView.findViewById(R.id.text_updatedAt);  // Correctly initialize updatedAtTextView
        }
    }

}
