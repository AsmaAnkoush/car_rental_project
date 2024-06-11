package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.view.View;

import android.util.Log;




public class UpdateCar extends AppCompatActivity {


    private EditText uptxtType, uptxtModelYear, uptxtColor, uptxtCarNumber,uptext_Image,
            uptxtRentalPricePerDay, uptxtMakeCompany, uptxtDescription,uptextModel,upeditPassengers;
    private Button upbuttonSave, upbuttonCancel, upSelectImage;
    private RequestQueue queue;
    private int carId;
    private ImageView upCarimageView;
    private Spinner upspinnerTranType, upspinnerStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_car);


        upCarimageView = findViewById(R.id.upCarimageView);
        upspinnerTranType = findViewById(R.id.upspinnerBenz);
        upspinnerStatus = findViewById(R.id.upspinnerStatus);
        uptextModel=findViewById(R.id.upeditModel);
        uptext_Image=findViewById(R.id.uptext_Image);
        upeditPassengers=findViewById(R.id.upeditPassengers);

        uptxtType = findViewById(R.id.upeditType);
        uptxtModelYear = findViewById(R.id.editModelYear);
        uptxtColor = findViewById(R.id.upeditColor);
        uptxtCarNumber = findViewById(R.id.upeditCarNumber);
//        uptxtNumberOfPassengers = findViewById(R.id.upeditPassengers);
        uptxtRentalPricePerDay = findViewById(R.id.upeditRentalPrice);
        uptxtMakeCompany = findViewById(R.id.upeditMakeCompany);
        uptxtDescription = findViewById(R.id.upeditDescription);

        upbuttonSave = findViewById(R.id.upbuttonSave);
        upbuttonCancel = findViewById(R.id.upbuttonCancel);
        upSelectImage = findViewById(R.id.upbuttonSelectImage);
        fetchSpinnerData();
        queue = Volley.newRequestQueue(this);

        if (savedInstanceState != null) {
            carId = savedInstanceState.getInt("carId"); // Restore relevant data

        }
        // Get the car ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            carId = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
            fetchCarData();

        }


        upbuttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                updateCarData(carId);
//                updateCarData();
                updateCar(carId);
            }
        });

        upbuttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity
                fetchCarData();
            }
        });

        upSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle image selection
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("carId", carId); // Save relevant data
        super.onSaveInstanceState(outState);
    }
    private void fetchSpinnerData() {
        // Retrieve the array for status and type of Benz from strings.xml
        String[] statusArray = getResources().getStringArray(R.array.car_statuses);
        String[] TranTypesArray = getResources().getStringArray(R.array.car_tran_types);

        // Convert the arrays to ArrayLists
        ArrayList<String> statusList = new ArrayList<>(Arrays.asList(statusArray));
        ArrayList<String> transTypesList = new ArrayList<>(Arrays.asList(TranTypesArray));

        // Create ArrayAdapter for status spinner
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(UpdateCar.this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upspinnerStatus.setAdapter(statusAdapter);

        // Create ArrayAdapter for type of Benz spinner
        ArrayAdapter<String> benzTypesAdapter = new ArrayAdapter<>(UpdateCar.this, android.R.layout.simple_spinner_item, transTypesList);
        benzTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upspinnerTranType.setAdapter(benzTypesAdapter);
    }

    private void fetchCarData() {
        // Get the car ID from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            int carId = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found

            // Construct URL for fetching car data
            String url = "http://10.0.2.2/carrentalphp/get_car.php?carId=" + carId;

            // Create a new request queue
          RequestQueue  requestQueue = Volley.newRequestQueue(this);

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

//                                    private EditText uptxtType, uptxtModelYear, uptxtColor, uptxtCarNumber, uptxtNumberOfPassengers,
//                                            uptxtRentalPricePerDay, uptxtMakeCompany, uptxtDescription,uptextModel,upeditPassengers;
//

                                    // Populate TextViews with car data
                                    uptextModel.setText(carData.getString("model"));
//                                    txtStatus.setText(carData.getString("availability_status"));
                                    setSpinnerValue(upspinnerStatus,carData.getString("availability_status"));
//                                    upspinnerTranType.setText(carData.getString("transmissionTypes"));
                                    setSpinnerValue(upspinnerTranType,carData.getString("transmissionTypes"));
                                    uptxtModelYear.setText(String.valueOf(carData.getInt("year")));
                                    uptxtColor.setText(carData.getString("color"));
                                    uptxtCarNumber.setText(String.valueOf(carData.getInt("car_Number")));
                                    upeditPassengers.setText(String.valueOf(carData.getInt("numOfPassengers")));
                                    uptxtRentalPricePerDay.setText(String.valueOf(carData.getDouble("rental_price_per_day")));
                                    uptxtMakeCompany.setText(carData.getString("make"));
                                    uptxtDescription.setText(carData.getString("description"));

                                    // Load image using Glide
                                    Glide.with(UpdateCar.this)
                                            .load(carData.getString("image_url"))
                                            .into(upCarimageView);
                                } else {
                                    // Display error message if there's an error fetching car data
                                    String message = jsonResponse.getString("msg");
                                    Toast.makeText(UpdateCar.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                e.printStackTrace();
                                Toast.makeText(UpdateCar.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            Toast.makeText(UpdateCar.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the request to the request queue
            requestQueue.add(stringRequest);
        }
    }


    private void setSpinnerValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }


    private void updateCar(int carId) {
        String url = "http://10.0.2.2/carRentalPHP/updatecar.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");

                    if (success) {
                        // Car updated successfully
                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        // Error updating car
                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON parsing error
                    Toast.makeText(UpdateCar.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Log the error message
                Log.e("VolleyError", "Error: " + error.getMessage());
                // Network error
                Toast.makeText(UpdateCar.this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Include updated car details and carId in the request parameters
//                private EditText uptxtType, uptxtModelYear, uptxtColor, uptxtCarNumber,
//                        uptxtRentalPricePerDay, uptxtMakeCompany, uptxtDescription,uptextModel,upeditPassengers;



                params.put("carId", String.valueOf(carId));
                params.put("carNumber",  uptxtCarNumber.getText().toString());
                params.put("model", uptextModel.getText().toString());
                params.put("year", uptxtModelYear.getText().toString());
                params.put("color", uptxtColor.getText().toString());
                params.put("description", uptxtDescription.getText().toString());
                params.put("rentalPricePerDay", uptxtRentalPricePerDay.getText().toString());
                params.put("availabilityStatus", upspinnerStatus.getSelectedItem().toString());

                params.put("transmissionTypes", upspinnerTranType.getSelectedItem().toString());
                params.put("numOfPassengers", upeditPassengers.getText().toString());
                params.put("usingTypes", uptxtType.getText().toString());
                params.put("make", uptxtMakeCompany.getText().toString());
                if (!uptext_Image.getText().toString().isEmpty()){
                    params.put("imageUrl", uptext_Image.getText().toString());
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateCar.this);
        requestQueue.add(stringRequest);
    }


//    private void updateCar(int carId) {
//        String url = "http://10.0.2.2/carrentalphp/update_car.php";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    boolean success = jsonResponse.getBoolean("success");
//                    String message = jsonResponse.getString("message");
//
//                    if (success) {
//                        Toast.makeText(UpdateCar.this, message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(UpdateCar.this, "Failed to update car: " + message, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(UpdateCar.this, "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText(UpdateCar.this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("carId", String.valueOf(carId));
//                params.put("carNumber", uptxtCarNumber.getText().toString());
//                params.put("model", uptextModel.getText().toString());
//                params.put("year", uptxtModelYear.getText().toString());
//                params.put("color", uptxtColor.getText().toString());
//                params.put("description", uptxtDescription.getText().toString());
//                params.put("rentalPricePerDay", uptxtRentalPricePerDay.getText().toString());
//                params.put("availabilityStatus", upspinnerStatus.getSelectedItem().toString());
//                params.put("imageUrl", ""); // Add image URL if applicable
//                params.put("transmissionTypes", upspinnerTranType.getSelectedItem().toString()); // Add transmission type if applicable
//                params.put("numOfPassengers", upeditPassengers.getText().toString());
//                params.put("usingTypes", uptxtType.getText().toString()); // Add using type if applicable
//                params.put("make", uptxtMakeCompany.getText().toString());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(UpdateCar.this);
//        requestQueue.add(stringRequest);
//    }

//    private void updateCar(int carId) {
////?carId=" + carId;
//        String url = "http://10.0.2.2/carrentalphp/update_car.php";
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
//                Map<String, String> params = new HashMap<>();
//
////?carId=" + carId;
//                params.put("carId", String.valueOf(carId));
//
//                params.put("carNumber",  uptxtCarNumber.getText().toString());
//                params.put("model", uptextModel.getText().toString());
//                params.put("year", uptxtModelYear.getText().toString());
//                params.put("color", uptxtColor.getText().toString());
//                params.put("description", uptxtDescription.getText().toString());
//                params.put("rentalPricePerDay", uptxtRentalPricePerDay.getText().toString());
//                params.put("availabilityStatus", upspinnerStatus.getSelectedItem().toString());
//                params.put("imageUrl", ""); // Add image URL if applicable
//                params.put("transmissionTypes", upspinnerTranType.getSelectedItem().toString()); // Add transmission type if applicable
//                params.put("numOfPassengers", upeditPassengers.getText().toString());
//                params.put("usingTypes", uptxtType.getText().toString()); // Add using type if applicable
//
//                params.put("make", uptxtMakeCompany.getText().toString());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(UpdateCar.this);
//        requestQueue.add(stringRequest);
//    }
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