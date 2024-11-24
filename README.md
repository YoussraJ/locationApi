# Location Tracking Application

## Description

This application consists of two main parts: the **Frontend** and the **Backend**.

### Frontend (Android Studio & Google Maps)
The frontend is built using **Android Studio** and integrates **Google Maps** to display a live map. The application tracks the user's movement, saving their location every minute or after every 10 meters of displacement. Users can view their movement on the map, add, delete, and modify their positions, and mark specific locations as "favorites."

### Backend (Node.js, Express, MongoDB)
The backend is built with **Node.js**, **Express**, and **MongoDB**. It handles storing and managing user data, including the saved locations and their preferences (e.g., favorite locations).

### Features:
- **Track User Movement**: The app logs the user’s location every minute or when they move more than 10 meters.
- **Modify Locations**: Users can add, delete, and modify their stored locations.
- **Favorites**: Users can mark locations as "favorites" for easy reference.
- **View Movement History**: A complete history of the user's movement is displayed on the map, with saved locations accessible anytime.

---

## Setup Instructions

Before running the application, follow these steps to ensure proper configuration:

### 1. **Connect Your PC and Emulator to the Same Network**
Ensure both your PC and Android emulator are connected to the same local network. This is essential for proper communication between the frontend (Android app) and the backend (API server).

### 2. **Configure `local.properties` and `network_security_config.xml`**

- **Extract your IP address** of your local machine (on the same network).
- Modify `local.properties` to include your backend's IP address.
  
  Example for `local.properties`:
  ```properties
  backend_url=http://<your-local-ip>:<backend-port>
