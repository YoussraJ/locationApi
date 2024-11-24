package com.example.location;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public  JSONObject makeRequest(String url)
    {

        try {
            urlObj = new URL(url);


        conn = (HttpURLConnection) urlObj.openConnection();
        } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                //Receive the response from the server
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.d("JSON Parser", "result: " + result.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.disconnect();

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(result.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON Object
            return jObj;
        }
    public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> params) {
        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            // Set request method and headers
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json"); // Set Content-Type to JSON
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);

            // Send data in the body for POST method
            if (method.equals("POST")) {
                conn.setDoOutput(true);

                // Create a JSON object from params
                JSONObject jsonParams = new JSONObject();
                for (String key : params.keySet()) {
                    try {
                        jsonParams.put(key, params.get(key)); // Add each parameter to the JSON object
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Write the JSON object to the request body
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(jsonParams.toString());  // Send the JSON as the request body
                wr.flush();
                wr.close();
            }

            // Get response code
            int responseCode = conn.getResponseCode();
            Log.d("HTTP Response", "Response Code: " + responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("HTTP Error", "Error: " + responseCode);
                return null;
            }

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("JSON Parser", "Result: " + result.toString());

            if (result.length() == 0) {
                Log.e("JSON Parser", "Received empty response from server.");
                return null;
            }

            // Parse the result as a JSON object
            try {
                return new JSONObject(result.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data: " + e.toString());
                return null;
            }

        } catch (IOException e) {
            Log.e("IOException", "Network error: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }


    public JSONArray makeArrayRequest(String url) {
        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            // Set request method
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "Result: " + result.toString());

            if (result.length() == 0) {
                Log.e("JSON Parser", "Received empty response from server.");
                return null;
            }

            // Parse the result as a JSONArray
            return new JSONArray(result.toString());

        } catch (IOException | JSONException e) {
            Log.e("JSON Parser", "Error parsing data: " + e.toString());
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }



    // Method to update the position
    public JSONObject updatePosition(String url, String positionId, HashMap<String, String> updatedData, Context context) {
        try {
            // Build the complete URL for the request, including the position ID
            url = url + "/" + positionId;

            // Open a connection to the server
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            // Set request method to PUT
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);

            // Create a JSON object from the updated data
            JSONObject jsonParams = new JSONObject();
            for (String key : updatedData.keySet()) {
                try {
                    jsonParams.put(key, updatedData.get(key)); // Add each parameter to the JSON object
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Send the JSON object in the body of the PUT request
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(jsonParams.toString());  // Send the JSON as the request body
            wr.flush();
            wr.close();

            // Get the response code
            int responseCode = conn.getResponseCode();
            Log.d("HTTP Response", "Response Code: " + responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("HTTP Error", "Error: " + responseCode);
                return null;
            }

            // Read the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("JSON Parser", "Result: " + result.toString());

            if (result.length() == 0) {
                Log.e("JSON Parser", "Received empty response from server.");
                return null;
            }

            // Update Toast on the main thread
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context, "Position updated successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);  // Adjust '50' to set the vertical offset from the top
                        toast.show();
                    }
                });
            }



            // Parse the result as a JSON object and return it
            return new JSONObject(result.toString());

        } catch (IOException | JSONException e) {
            Log.e("JSON Parser", "Error updating position: " + e.toString());
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }



    // Method to delete a position
    public JSONObject deletePosition(String url, String positionId, Context context) {
        try {
            // Build the complete URL for the request, including the position ID
            url = url  + positionId;

            // Open a connection to the server
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();

            // Set request method to DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(10000);

            // Get the response code
            int responseCode = conn.getResponseCode();
            Log.d("HTTP Response", "Response Code: " + responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("HTTP Error", "Error: " + responseCode);
                return null;
            }

            // Read the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.d("JSON Parser", "Result: " + result.toString());

            if (result.length() == 0) {
                Log.e("JSON Parser", "Received empty response from server.");
                return null;
            }

            // Update Toast on the main thread
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context, "Position deleted successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);  // Adjust '50' to set the vertical offset from the top
                        toast.show();
                    }
                });
            }


            // Parse the result as a JSON object and return it
            return new JSONObject(result.toString());


        } catch (IOException | JSONException e) {
            Log.e("JSON Parser", "Error deleting position: " + e.toString());
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


}
