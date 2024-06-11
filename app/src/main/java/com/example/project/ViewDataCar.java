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
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;




public class ViewDataCar extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtTypeTrans, txtModelYear, txtColor, txtCarNumber, txtNumberOfPassengers,
            txtRentalPricePerDay, txtMakeCompany, txtDescription;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_car);

        // Initialize views
        txtTypeTrans = findViewById(R.id.txtTypeTrans);
        txtModelYear = findViewById(R.id.txtModelYear);
        txtColor = findViewById(R.id.txtColor);
        txtCarNumber = findViewById(R.id.txtCarNumber);
        txtNumberOfPassengers = findViewById(R.id.txtNumberOfPassengers);
        txtRentalPricePerDay = findViewById(R.id.txtRentalPricePerDay);
        txtMakeCompany = findViewById(R.id.txtMakeCompany);
        txtDescription = findViewById(R.id.txtDescription);
        imageView=findViewById(R.id.CarimageView);


        // Fetch car data
        fetchCarData();
    }

    private void fetchCarData() {
        // Get the car ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            int carId = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found

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
                                    txtTypeTrans.setText(carData.getString("usingType"));
                                    txtModelYear.setText(carData.getString("year"));
                                    txtColor.setText(carData.getString("color"));
                                    txtCarNumber.setText(carData.getString("car_Number"));
                                    txtNumberOfPassengers.setText(carData.getString("numberOfPassengers"));
                                    txtRentalPricePerDay.setText(carData.getString("rental_price_per_day"));
                                    txtMakeCompany.setText(carData.getString("make"));
                                    txtDescription.setText(carData.getString("description"));

//                                    String imageUrl = carData.getString("image_url");

                                    Glide.with(ViewDataCar.this)
                                            .load(carData.getString("image_url")) // Assuming getImageUrl() returns the URL of the image
                                            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image
                                            .into(imageView);

//                                    Glide.with(ViewDataCar.this)
//                                            .load(imageUrl) // Assuming getImageUrl() returns the URL of the image
//                                            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image
//                                            .into(imageView);


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
}

//
//public class ViewDataCar extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_view_data_car);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        TextView txtType = findViewById(R.id.txtTypeTrans);
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("id")) {
//            int id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
//            txtType.setText(String.valueOf(id));
//        }
//
//    }
//}

