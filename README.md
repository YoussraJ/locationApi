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

- **Step 1: Configure `local.properties`**
  - Extract your IP address of your local machine (on the same network).
  - Modify the `local.properties` file in the root of your Android project to include your backend's IP address.
  ```properties
  backend_url=http://<your-local-ip>:<backend-port>


## Setup Instructions (Continued)

### 4. **Running the Backend Locally**

To run the backend locally, follow these steps:

1. **Clone the backend repository:**
   - If you haven't already, clone the repository for the backend:
     ```bash
     git clone https://github.com/YoussraJ/locationApi.git
     cd locationApi
     ```

2. **Install dependencies:**
   - Install required dependencies using npm:
     ```bash
     npm install
     ```

3. **Set up environment variables:**
   - Create a `.env` file in the root directory and add your MongoDB URI and other necessary configuration:
     ```bash
     MONGODB_URI=mongodb://localhost:27017/locationTracker
     PORT=5000
     ```

4. **Start the backend server:**
   - Run the server:
     ```bash
     npm start
     ```
   - The backend server should now be running on your local machine at the specified port (e.g., http://localhost:5000).

---

### 5. **Running the Frontend (Android App)**

1. **Clone the frontend repository:**
   - If you haven't already, clone the repository for the frontend:
     ```bash
     git clone https://github.com/YoussraJ/LocationFrontend.git
     cd LocationFrontend
     ```

2. **Open the project in Android Studio:**
   - Launch Android Studio, and open the project folder where you cloned the frontend repository.

3. **Sync project dependencies:**
   - Android Studio will automatically prompt you to sync dependencies. Allow it to do so, and ensure that all necessary libraries and APIs are downloaded.

4. **Set up the Google Maps API Key:**
   - Open `AndroidManifest.xml` and replace `"YOUR_GOOGLE_MAPS_API_KEY"` with your actual API key.
   - If you don't have a Google Maps API key, you can get one from the [Google Cloud Console](https://console.cloud.google.com/).

5. **Configure local properties:**
   - Set the backend URL in `local.properties`:
     ```properties
     backend_url=http://<your-local-ip>:<backend-port>
     ```

6. **Run the Android app:**
   - Connect an emulator or Android device to your PC.
   - Press the **Run** button in Android Studio to launch the app on the emulator/device.
   - Once the app is running, it should display a Google Map with the user's movement tracked.

---

## Features

1. **Track User Movement:**
   - The app tracks the user’s movement in real-time and logs their location either every minute or when they have moved more than 10 meters.
   - The app can display the path of the user's movement on the map and update in real-time as they move.

2. **Modify Saved Locations:**
   - Users can view saved locations, add new ones, modify details of existing locations, or delete them as needed.
   - A location’s name, description, and favorite status can be changed.

3. **Mark Locations as Favorite:**
   - Users can mark locations as favorites for easy reference later. These locations are highlighted differently on the map.

4. **View Movement History:**
   - A complete history of the user’s movement is stored and can be viewed on the map.
   - Users can track back to previously visited locations and see their past movements.

---

## Technologies Used

- **Frontend (Android App)**:
  - Android Studio
  - Google Maps API
  - Retrofit (for API requests)
  - Gson (for parsing JSON responses)
  - Firebase (for user authentication)
  - Location Services (for tracking movement)

- **Backend (API Server)**:
  - Node.js
  - Express.js
  - MongoDB (with Mongoose for object modeling)
  - JWT (for user authentication)
  - CORS (for handling cross-origin requests)
  - Bcrypt.js (for password hashing)
  - dotenv (for managing environment variables)

---

## Troubleshooting

### 1. **Emulator Not Displaying Map:**
   - If the Google Map is not showing, ensure that your Google Maps API key is correctly configured in `AndroidManifest.xml`.
   - Check your internet connection; Google Maps requires an active connection to load the map data.

### 2. **Backend API Not Connecting:**
   - Double-check that your local IP address is correctly set in `local.properties`.
   - Ensure the backend server is running on the expected port (e.g., `5000`).
   - Ensure that both the Android emulator and your local machine are on the same network.

### 3. **Location Not Updating:**
   - Make sure your device or emulator has location services enabled.
   - Check your app's permissions to ensure the app has access to the device's location.

---

## Contributing

If you'd like to contribute to this project, feel free to fork the repository and submit pull requests. Here are some ways you can contribute:

- **Bug Fixes**: Report any bugs you encounter and fix them.
- **New Features**: Suggest and implement new features or improvements.
- **Documentation**: Help us improve the documentation or translate it into other languages.
- **Testing**: Contribute by testing and reporting any issues.

To contribute, follow these steps:

1. Fork the repository on GitHub.
2. Clone your forked repository to your local machine.
3. Create a new branch for your feature/bugfix.
4. Implement your changes and commit them with clear messages.
5. Push your changes to your fork and submit a pull request.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact

For any questions or feedback, feel free to contact:
- **GitHub Repository**: [YoussraJ/locationApi](https://github.com/YoussraJ/locationApi)
- **Email**: youssraj@example.com

---

## Future Plans
- Improve location tracking accuracy with GPS sensors.
- Integrate real-time location sharing with friends or colleagues.
- Add advanced features like geofencing, route planning, and points of interest.
- Implement push notifications for location-based alerts.
- Add a web version to view location history on a desktop interface.

---

## Acknowledgments
- Thanks to the Google Maps API team for providing a reliable map service.
- Special thanks to the Node.js, Express, and MongoDB communities for providing the necessary tools and libraries for the backend.
- Thanks to the contributors of Android Studio and all open-source libraries that helped in building the frontend.
