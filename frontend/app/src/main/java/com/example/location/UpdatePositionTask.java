package com.example.location;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class UpdatePositionTask extends AsyncTask<String, Void, JSONObject> {
    private JSONParser jsonParser;
    private Context con;
    public UpdatePositionTask(JSONParser jsonParser, Context con) {
        this.jsonParser = jsonParser;
        this.con = con;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        // params[0] -> URL
        // params[1] -> positionId
        // params[2] -> serialized HashMap (String representation)

        String url = params[0];
        String positionId = params[1];

        // Deserialize HashMap (You need a method to deserialize the string into a HashMap)
        HashMap<String, String> dataMap = new HashMap<>();
        String dataMapString = params[2];
        // Deserialize the dataMapString back to HashMap (e.g., using JSON)
        // You can use any suitable method for deserialization

        // For this example, assuming you're passing the serialized map as JSON
        try {
            JSONObject jsonObject = new JSONObject(dataMapString);

            for (Iterator<String> keys = jsonObject.keys(); keys.hasNext(); ) {
                String key = keys.next();
                dataMap.put(key, jsonObject.getString(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Call your method
        return jsonParser.updatePosition(url, positionId, dataMap, con);
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        if (result != null) {
            // Handle result (e.g., update the UI with new data)
        } else {
            // Handle error
        }
    }
}
