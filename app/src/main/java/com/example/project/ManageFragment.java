package com.example.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ManageFragment extends Fragment {

    private EditText editType, editModelYear, editColor, editCarNumber, editPassengers, editRentalPrice, editMakeCompany, editDescription;
    private Spinner spinnerBenz, spinnerStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        spinnerBenz = view.findViewById(R.id.spinnerBenz);
        spinnerStatus = view.findViewById(R.id.spinnerStatus);

        editType = view.findViewById(R.id.editType);
        editModelYear = view.findViewById(R.id.editModelYear);
        editColor = view.findViewById(R.id.editColor);
        editCarNumber = view.findViewById(R.id.editCarNumber);
        editPassengers = view.findViewById(R.id.editPassengers);
        editRentalPrice = view.findViewById(R.id.editRentalPrice);
        editMakeCompany = view.findViewById(R.id.editMakeCompany);
        editDescription = view.findViewById(R.id.editDescription);

        fetchSpinnerData();

        Button buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCar();
            }
        });

        return view;
    }

    private void fetchSpinnerData() {
        // Retrieve the array for status and type of Benz from strings.xml
        String[] statusArray = getResources().getStringArray(R.array.car_statuses);
        String[] benzTypesArray = getResources().getStringArray(R.array.car_benz_types);

        // Convert the arrays to ArrayLists
        ArrayList<String> statusList = new ArrayList<>(Arrays.asList(statusArray));
        ArrayList<String> benzTypesList = new ArrayList<>(Arrays.asList(benzTypesArray));

        // Create ArrayAdapter for status spinner
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        // Create ArrayAdapter for type of Benz spinner
        ArrayAdapter<String> benzTypesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, benzTypesList);
        benzTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBenz.setAdapter(benzTypesAdapter);
    }

    private void addNewCar() {
        String url = "http://10.0.2.2/carRentalPHP/add_car.php";
//        String url = "http://192.168.0.111/add_car.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Parse the response from the server
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");

                    if (success) {
                        // Car added successfully
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        // Error adding car
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // JSON parsing error
                    Toast.makeText(getActivity(), "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Log the error message
                        Log.e("VolleyError", "Error: " + error.getMessage());
                        // Network error
                        Toast.makeText(getActivity(), "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("carNumber",  editCarNumber.getText().toString());
                params.put("make", spinnerBenz.getSelectedItem().toString());
                params.put("model", editType.getText().toString());
                params.put("year", editModelYear.getText().toString());
                params.put("color", editColor.getText().toString());
                params.put("description", editDescription.getText().toString());
                params.put("rentalPricePerDay", editRentalPrice.getText().toString());
                params.put("availabilityStatus", spinnerStatus.getSelectedItem().toString());
                params.put("imageUrl", ""); // Add image URL if applicable
                params.put("transmissionTypes", ""); // Add transmission type if applicable
                params.put("numOfPassengers", editPassengers.getText().toString());
                params.put("usingTypes", ""); // Add using type if applicable
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
