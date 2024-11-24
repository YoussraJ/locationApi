package com.example.location;
public class Position {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String createdAt;
    private String updatedAt;
    private boolean isFavorite;

    // Constructor with default value for isFavorite
    public Position(String name, double latitude, double longitude, String id, String createdAt, String updatedAt) {
        this(name, latitude, longitude, id, createdAt, updatedAt, false); // Call the second constructor with default isFavorite value
    }

    // Constructor with all parameters, including isFavorite
    public Position(String name, double latitude, double longitude, String id, String createdAt, String updatedAt, boolean isFavorite) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getName() {
        return name;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
