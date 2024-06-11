package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.view.View;

import android.util.Log;




public class UpdateCar extends AppCompatActivity {


    private EditText uptxtType, uptxtModelYear, uptxtColor, uptxtCarNumber, uptxtNumberOfPassengers, uptxtRentalPricePerDay, uptxtMakeCompany, uptxtDescription;
    private Button upbuttonSave, upbuttonCancel, upSelectImage;
    private RequestQueue queue;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the views
        uptxtType = findViewById(R.id.uptxtType);
        uptxtModelYear = findViewById(R.id.uptxtModelYear);
        uptxtColor = findViewById(R.id.uptxtColor);
        uptxtCarNumber = findViewById(R.id.uptxtCarNumber);
        uptxtNumberOfPassengers = findViewById(R.id.uptxtNumberOfPassengers);
        uptxtRentalPricePerDay = findViewById(R.id.uptxtRentalPricePerDay);
        uptxtMakeCompany = findViewById(R.id.uptxtMakeCompany);
        uptxtDescription = findViewById(R.id.uptxtDescription);
        upbuttonSave = findViewById(R.id.upbuttonSave);
//        upbuttonCancel = findViewById(R.id.upbuttonCancel);
        upSelectImage = findViewById(R.id.upSelectImage);

        queue = Volley.newRequestQueue(this);

        // Get the car ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            carId = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
            fetchCarData(carId);
        }


        upbuttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCarData(carId);
            }
        });

//        upbuttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Finish the activity
//                finish();
//            }
//        });

        upSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle image selection
            }
        });
    }


    private void fetchCarData(int carId) {
        String url = "http://10.0.2.2/carrentalphp/get_car.php?carId=" + carId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean("error");
                            if (!error) {
                                JSONObject carData = jsonResponse.getJSONObject("data");
                                // Populate the fields with car data
                                uptxtType.setText(carData.getString("usingType"));
                                uptxtModelYear.setText(carData.getString("year"));
                                uptxtColor.setText(carData.getString("color"));
                                uptxtCarNumber.setText(carData.getString("car_Number"));
                                uptxtNumberOfPassengers.setText(carData.getString("numberOfPassengers"));
                                uptxtRentalPricePerDay.setText(carData.getString("rental_price_per_day"));
                                uptxtMakeCompany.setText(carData.getString("make"));
                                uptxtDescription.setText(carData.getString("description"));
                            } else {
                                String message = jsonResponse.getString("msg");
                                Toast.makeText(UpdateCar.this, "Error: " + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UpdateCar.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCar.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(stringRequest);
    }

//    private void addNewCar() {
//        String url = "http://10.0.2.2/carRentalPHP/add_car.php";
////        String url = "http://192.168.0.111/add_car.php";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Parse the response from the server
//                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    boolean success = jsonResponse.getBoolean("success");
//                    String message = jsonResponse.getString("message");
//
//                    if (success) {
//                        // Car added successfully
//                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Error adding car
//                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    // JSON parsing error
//                    Toast.makeText(UpdateCar.this, "Error parsing response", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        // Log the error message
//                        Log.e("VolleyError", "Error: " + error.getMessage());
//                        // Network error
//                        Toast.makeText(UpdateCar.this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
//                    }
//
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                //  private EditText uptxtType, uptxtModelYear, uptxtColor, uptxtCarNumber,
//                //  uptxtNumberOfPassengers, uptxtRentalPricePerDay, uptxtMakeCompany, uptxtDescription;
////
//                Map<String, String> params = new HashMap<>();
//                params.put("carNumber",  uptxtCarNumber.getText().toString());
//                params.put("make", uptxtMakeCompany.getText().toString());
//                params.put("model", .getText().toString());
//                params.put("year", uptxtModelYear.getText().toString());
//                params.put("color", uptxtColor.getText().toString());
//                params.put("description", uptxtDescription.getText().toString());
//                params.put("rentalPricePerDay", uptxtRentalPricePerDay.getText().toString());
//                params.put("availabilityStatus", spinnerStatus.getSelectedItem().toString());
//                params.put("imageUrl", ""); // Add image URL if applicable
//                params.put("transmissionTypes", ""); // Add transmission type if applicable
//                params.put("numOfPassengers", uptxtNumberOfPassengers.getText().toString());
//                params.put("usingTypes", uptxtType.getText().toString()); // Add using type if applicable
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }
//}
    private void updateCarData(int carId) {
        String url = "http://10.0.2.2/carrentalphp/update_car.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");

                    if (success) {
                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateCar.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("VolleyError", "Error: " + error.getMessage());
                Toast.makeText(UpdateCar.this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(carId));
                params.put("type", uptxtType.getText().toString());
                params.put("model_year", uptxtModelYear.getText().toString());
                params.put("color", uptxtColor.getText().toString());
                params.put("car_number", uptxtCarNumber.getText().toString());
                params.put("num_of_passengers", uptxtNumberOfPassengers.getText().toString());
                params.put("rental_price_per_day", uptxtRentalPricePerDay.getText().toString());
                params.put("make_company", uptxtMakeCompany.getText().toString());
                params.put("description", uptxtDescription.getText().toString());
                return params;
            }
        };

        queue.add(stringRequest);
    }
}

//
//public class UpdateCar extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_update_car);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        TextView txtType = findViewById(R.id.uptxtType);
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("id")) {
//            int id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
//            txtType.setText(String.valueOf(id));
//        }
//
//    }
//}