package com.example.project;

import static com.example.project.R.id.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DeletCar extends AppCompatActivity {

    private Button buttonConfirm;
    private TextView carInfo;
    private int id;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet_car);

        buttonConfirm = findViewById(R.id.buttonConfirm);
        carInfo = findViewById(R.id.carInfo);
        queue = Volley.newRequestQueue(this);


        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id"); // Restore relevant data

        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
            carInfo.setText(String.valueOf(id));
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar(id);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("id", id); // Save relevant data
        super.onSaveInstanceState(outState);
    }


    private void deleteCar(int carId) {
        String url = "http://10.0.2.2/carrentalphp/delet_car.php?carId=" + carId;

        // Create a StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean("error");
                            String message = jsonResponse.getString("msg");

                            // Handle response accordingly
                            if (error) {
                                // Show error message
                                Toast.makeText(DeletCar.this, "Error: " + message, Toast.LENGTH_LONG).show();
                            } else {
                                // Show success message
                                Toast.makeText(DeletCar.this, "Success: " + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeletCar.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeletCar.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}

//
//public class DeletCar extends AppCompatActivity {
//
//        private Button buttonConfirm;
//        private TextView carInfo;
//        private int id;
//        private RequestQueue queue;
//
//        private  Button btnConfirm;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            EdgeToEdge.enable(this);
//            setContentView(R.layout.activity_delet_car);
//
//            buttonConfirm = findViewById(R.id.buttonConfirm);
//            carInfo = findViewById(R.id.carInfo);
//            queue = Volley.newRequestQueue(this);
//
//
//            Intent intent = getIntent();
//            if (intent != null && intent.hasExtra("id")) {
//                id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
//                carInfo.setText(String.valueOf(id));
//            }
//            Toast.makeText(DeletCar.this, "Error: ", Toast.LENGTH_LONG).show();
//            buttonConfirm.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(DeletCar.this, " buttonConfirm with id :"+ id , Toast.LENGTH_LONG).show();
//                    deleteCar(id);
//                    Toast.makeText(DeletCar.this, " buttonConfirm : Done" , Toast.LENGTH_LONG).show();
//                }
//            });
//        }
////    public void OnClick_btnConfirm(View view) {
////        Toast.makeText(DeletCar.this, " buttonConfirm with id :"+ id , Toast.LENGTH_LONG).show();
////        deleteCar(id);
////        Toast.makeText(DeletCar.this, " buttonConfirm : Done" , Toast.LENGTH_LONG).show();
////
////    }
//    private void deleteCar(int carId) {
//        String url = "http://10.0.2.2/carrentalphp/delet_car.php?carId="  + carId;
//
//        // Create a RequestQueue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        // Create a StringRequest
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean error = jsonResponse.getBoolean("error");
//                            String message = jsonResponse.getString("msg");
//
//                            // Handle response accordingly
//                            if (error) {
//                                // Show error message
//                                Toast.makeText(DeletCar.this, "Error: " + message, Toast.LENGTH_LONG).show();
//                            } else {
//                                // Show success message
//                                Toast.makeText(DeletCar.this, "Success: " + message, Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(DeletCar.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DeletCar.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        // Add the request to the RequestQueue
//        requestQueue.add(stringRequest);
//    }
//
//    public void OnClick_btnConfirm(View view) {
//        Toast.makeText(DeletCar.this, "Error: ", Toast.LENGTH_LONG).show();
//
//    }
//}






//        private void deleteCar(int carId) {
//            //carrentalphp/delet_car.php?carId
//            String deleteUrl = "http://10.0.2.2/carrentalphp/delet_car.php?carId=" + carId;
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, deleteUrl,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Parse the JSON response
//                            try {
//                                JSONObject jsonResponse = new JSONObject(response);
//                                boolean error = jsonResponse.getBoolean("error");
//                                String msg = jsonResponse.getString("msg");
//
//                                if (!error) {
//                                    // Car deleted successfully, display a toast message
//                                    Toast.makeText(DeletCar.this, msg, Toast.LENGTH_SHORT).show();
//                                } else {
//                                    // Failed to delete car, display a toast message
//                                    Toast.makeText(DeletCar.this, msg, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Toast.makeText(DeletCar.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // Handle error response, display an error message
//                            Toast.makeText(DeletCar.this, "Failed to delete car", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//            // Add the request to the RequestQueue
//            queue.add(stringRequest);
//        }
//    }


//    RequestQueue queue;
//    Button buttonConfirm;
//    TextView carInfo;
//    int id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_delet_car);
//
//        buttonConfirm = findViewById(R.id.buttonConfirm);
//        carInfo = findViewById(R.id.carInfo);
//
//        // Initialize the RequestQueue
//        queue = Volley.newRequestQueue(this);
//
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("id")) {
//            id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
//            carInfo.setText(String.valueOf(id));
//        }
//
//        buttonConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteCar(id);
//            }
//        });
//    }
//
//    private void deleteCar(int carId) {
//
//        String deleteUrl = " http://10.0.2.2/carrentalphp/delet_car.php?carId=" + carId;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, deleteUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Toast.makeText(DeletCar.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(DeletCar.this, "Failed to delete car", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        // Add the request to the RequestQueue
//        queue.add(stringRequest);
//    }
//
//
//}


//public class DeletCar extends AppCompatActivity {
//
//    private TextView carInfo;
//    private Button buttonConfirm;
//    int id=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_delet_car);
//
//        buttonConfirm=findViewById(R.id.buttonConfirm);
//        carInfo=findViewById(R.id.carInfo);
//
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("id")) {
//            id = intent.getIntExtra("id", 0); // 0 is the default value if "id" is not found
//            carInfo.setText(String.valueOf(id));
//        }
//        buttonConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteCar(id);
//            }
//        });
//
//    }
//    private void deleteCar(int carId) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String deleteUrl = "http://10.0.2.2/carRentalPHP/deleteCar.php?carId=" + carId;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, deleteUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Car deleted successfully, handle the response if needed
//                        // For example, you can display a toast message
//                        Toast.makeText(DeletCar.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error response, display an error message if needed
//                        Toast.makeText(DeletCar.this, "Failed to delete car", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        // Add the request to the RequestQueue
//        queue.add(stringRequest);
//    }
//}