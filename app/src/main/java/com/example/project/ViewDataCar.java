package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewDataCar extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtModel, txtStatus, txtTypeTrans, txtModelYear, txtColor, txtCarNumber, txtNumberOfPassengers,
            txtRentalPricePerDay, txtMakeCompany, txtDescription;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_car);


        // Initialize views
        txtModel = findViewById(R.id.txtModel);
        txtStatus = findViewById(R.id.txtStatus);
        txtTypeTrans = findViewById(R.id.txtTypeTrans);
        txtModelYear = findViewById(R.id.txtModelYear);
        txtColor = findViewById(R.id.txtColor);
        txtCarNumber = findViewById(R.id.txtCarNumber);
        txtNumberOfPassengers = findViewById(R.id.txtNumberOfPassengers);
        txtRentalPricePerDay = findViewById(R.id.txtRentalPricePerDay);
        txtMakeCompany = findViewById(R.id.txtMakeCompany);
        txtDescription = findViewById(R.id.txtDescription);
        imageView=findViewById(R.id.CarimageView);
        if (savedInstanceState != null) {
            carId = savedInstanceState.getInt("carId"); // Restore relevant data

        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            carId = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
        }
        // Fetch car data
        fetchCarData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("carId", carId); // Save relevant data
        super.onSaveInstanceState(outState);
    }
    int carId=0;
    private void fetchCarData() {
        // Get the car ID from the intent


            // Construct URL for fetching car data
            String url = "http://10.0.2.2/carrentalphp/get_car.php?carId=" + carId;

            // Create a new request queue
            requestQueue = Volley.newRequestQueue(this);

            // Create a string request to fetch the car data
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // Parse the JSON response
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean error = jsonResponse.getBoolean("error");

                                if (!error) {
                                    // Retrieve car data from JSON response
                                    JSONObject carData = jsonResponse.getJSONObject("data");

                                    // Populate TextViews with car data
                                    txtModel.setText(carData.getString("model"));
                                    txtStatus.setText(carData.getString("availability_status"));
                                    txtTypeTrans.setText(carData.getString("transmissionTypes"));
                                    txtModelYear.setText(String.valueOf(carData.getInt("year")));
                                    txtColor.setText(carData.getString("color"));
                                    txtCarNumber.setText(String.valueOf(carData.getInt("car_Number")));
                                    txtNumberOfPassengers.setText(String.valueOf(carData.getInt("numOfPassengers")));
                                    txtRentalPricePerDay.setText(String.valueOf(carData.getDouble("rental_price_per_day")));
                                    txtMakeCompany.setText(carData.getString("make"));
                                    txtDescription.setText(carData.getString("description"));

                                    // Load image using Glide
                                    Glide.with(ViewDataCar.this)
                                            .load(carData.getString("image_url"))
                                            .into(imageView);
                                } else {
                                    // Display error message if there's an error fetching car data
                                    String message = jsonResponse.getString("msg");
                                    Toast.makeText(ViewDataCar.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                e.printStackTrace();
                                Toast.makeText(ViewDataCar.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            Toast.makeText(ViewDataCar.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the request to the request queue
            requestQueue.add(stringRequest);

    }
}
