package com.example.location;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeletePositionTask extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private Context context;
    public DeletePositionTask(JSONParser jsonParser, Context context) {
        this.jsonParser = jsonParser;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String url = params[0];
        String positionId = params[1];  // Get the positionId from params
        JSONObject result = null;

        // Calling the deletePosition method from JSONParser
        JSONParser jsonParser = new JSONParser();
        result = jsonParser.deletePosition(url, positionId, context);  // Ensure context is passed correctly here

        // Check if result is null or an error occurred
        if (result == null) {
            Log.e("DeletePositionTask", "Failed to delete position");
        } else {
            Log.d("DeletePositionTask", "Position deleted successfully");
        }

        return result;  // Returning the result after deletion
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            Log.d("DeletePositionTask", "Delete successful: " + result.toString());
        } else {
            Log.e("DeletePositionTask", "Failed to delete position.");
        }
    }
}
