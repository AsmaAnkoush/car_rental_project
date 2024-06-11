package com.example.project;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ManageFragment extends Fragment {

    private EditText editType, editModelYear, editModel, editColor, editCarNumber, editPassengers,editimage,
            editRentalPrice, editMakeCompany, editDescription;
    private Spinner spinnerTranType, spinnerStatus;
    private Button btnPick, buttonCancel;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        spinnerTranType = view.findViewById(R.id.spinnerBenz);
        spinnerStatus = view.findViewById(R.id.spinnerStatus);

        editType = view.findViewById(R.id.editType);
        editModelYear = view.findViewById(R.id.editModelYear);
        editModel = view.findViewById(R.id.editModel);
        editColor = view.findViewById(R.id.editColor);
        editCarNumber = view.findViewById(R.id.editCarNumber);
        editPassengers = view.findViewById(R.id.editPassengers);
        editRentalPrice = view.findViewById(R.id.editRentalPrice);
        editMakeCompany = view.findViewById(R.id.editMakeCompany);
        editDescription = view.findViewById(R.id.editDescription);
        btnPick = view.findViewById(R.id.buttonSelectImage);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        imageView = view.findViewById(R.id.imageViewSelected);
        editimage = view.findViewById(R.id.editimage);

        fetchSpinnerData();
        registerResult();

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        buttonCancel.setOnClickListener(v -> cleanData());

        Button buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCar();
            }
        });

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editType", editType.getText().toString());
        outState.putString("editModelYear", editModelYear.getText().toString());
        outState.putString("editModel", editModel.getText().toString());
        outState.putString("editColor", editColor.getText().toString());
        outState.putString("editCarNumber", editCarNumber.getText().toString());
        outState.putString("editPassengers", editPassengers.getText().toString());
        outState.putString("editRentalPrice", editRentalPrice.getText().toString());
        outState.putString("editMakeCompany", editMakeCompany.getText().toString());
        outState.putString("editDescription", editDescription.getText().toString());
        outState.putInt("spinnerTranType", spinnerTranType.getSelectedItemPosition());
        outState.putInt("spinnerStatus", spinnerStatus.getSelectedItemPosition());
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        editType.setText(savedInstanceState.getString("editType"));
        editModelYear.setText(savedInstanceState.getString("editModelYear"));
        editModel.setText(savedInstanceState.getString("editModel"));
        editColor.setText(savedInstanceState.getString("editColor"));
        editCarNumber.setText(savedInstanceState.getString("editCarNumber"));
        editPassengers.setText(savedInstanceState.getString("editPassengers"));
        editRentalPrice.setText(savedInstanceState.getString("editRentalPrice"));
        editMakeCompany.setText(savedInstanceState.getString("editMakeCompany"));
        editDescription.setText(savedInstanceState.getString("editDescription"));
        spinnerTranType.setSelection(savedInstanceState.getInt("spinnerTranType"));
        spinnerStatus.setSelection(savedInstanceState.getInt("spinnerStatus"));
    }

    private void cleanData() {
        editType.setText("");
        editModelYear.setText("");
        editModel.setText("");
        editColor.setText("");
        editCarNumber.setText("");
        editPassengers.setText("");
        editRentalPrice.setText("");
        editMakeCompany.setText("");
        editDescription.setText("");
        spinnerTranType.setSelection(0);
        spinnerStatus.setSelection(0);
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == FragmentActivity.RESULT_OK) {
                            if (result.getData() != null) {
                                Uri imageUrl = result.getData().getData();
                                imageView.setImageURI(imageUrl);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void fetchSpinnerData() {
        String[] statusArray = getResources().getStringArray(R.array.car_statuses);
        String[] TranTypesArray = getResources().getStringArray(R.array.car_tran_types);

        ArrayList<String> statusList = new ArrayList<>(Arrays.asList(statusArray));
        ArrayList<String> transTypesList = new ArrayList<>(Arrays.asList(TranTypesArray));

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        ArrayAdapter<String> benzTypesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, transTypesList);
        benzTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTranType.setAdapter(benzTypesAdapter);
    }

    private void addNewCar() {
        String url = "http://10.0.2.2/carRentalPHP/add_car.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");

                    if (success) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("VolleyError", "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("carNumber", editCarNumber.getText().toString());
                params.put("model", editModel.getText().toString());
                params.put("year", editModelYear.getText().toString());
                params.put("color", editColor.getText().toString());
                params.put("description", editDescription.getText().toString());
                params.put("rentalPricePerDay", editRentalPrice.getText().toString());
                params.put("availabilityStatus", spinnerStatus.getSelectedItem().toString());

                params.put("transmissionTypes", spinnerTranType.getSelectedItem().toString());
                params.put("numOfPassengers", editPassengers.getText().toString());
                params.put("usingTypes", editType.getText().toString());
                params.put("make", editMakeCompany.getText().toString());
                if (! editimage.getText().toString().isEmpty()){
                    params.put("imageUrl", editimage.getText().toString());
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}


//package com.example.project;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ManageFragment extends Fragment {
//
//    private EditText editType, editModelYear,editModel, editColor, editCarNumber, editPassengers, editRentalPrice, editMakeCompany, editDescription;
//    private Spinner spinnerTranType, spinnerStatus;
//    private Button btnPick ,buttonCancel;
//    ImageView imageView;
//    ActivityResultLauncher<Intent> resultLauncher;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_manage, container, false);
//
//        spinnerTranType = view.findViewById(R.id.spinnerBenz);
//        spinnerStatus = view.findViewById(R.id.spinnerStatus);
//
//        editType = view.findViewById(R.id.editType);
//        editModelYear = view.findViewById(R.id.editModelYear);
//        editModel = view.findViewById(R.id.editModel);
//        editColor = view.findViewById(R.id.editColor);
//        editCarNumber = view.findViewById(R.id.editCarNumber);
//        editPassengers = view.findViewById(R.id.editPassengers);
//        editRentalPrice = view.findViewById(R.id.editRentalPrice);
//        editMakeCompany = view.findViewById(R.id.editMakeCompany);
//        editDescription = view.findViewById(R.id.editDescription);
//        btnPick=view.findViewById(R.id.buttonSelectImage);
//        buttonCancel=view.findViewById(R.id.buttonCancel);
//        imageView=view.findViewById(R.id.imageViewSelected);
//
//
//        fetchSpinnerData();
//        registerResult();
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pickImage();
//            }
//        });        Button buttonSave = view.findViewById(R.id.buttonSave);
//        buttonCancel.setOnClickListener(v->{
//            cleanDta();
//        });
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewCar();
//            }
//        });
//
//        return view;
//    }
//
//    private void cleanDta() {
//        // Initialize the spinners
//
//
//
//        editModelYear.setText("");
//        editModel.setText("");
//        editColor.setText("");
//
//        editCarNumber.setText("");
//        editPassengers.setText("");
//        editRentalPrice.setText("");
//        editMakeCompany.setText("");
//        editDescription.setText("");
//
//        // Reset the Spinners
//        spinnerTranType.setSelection(0);
//        spinnerStatus.setSelection(0);
//    }
//
//
//    private void pickImage (){
//        Intent intent = new Intent (MediaStore.ACTION_PICK_IMAGES);
//        resultLauncher.launch(intent);
//    }
//    private void registerResult() {
//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == FragmentActivity.RESULT_OK) {
//                            if (result.getData() != null) {
//                                Uri imageUrl = result.getData().getData();
//                                imageView.setImageURI(imageUrl);
//                                // Make the ImageView visible
//                                imageView.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//    }
//
//
//    private void fetchSpinnerData() {
//        // Retrieve the array for status and type of Benz from strings.xml
//        String[] statusArray = getResources().getStringArray(R.array.car_statuses);
//        String[] TranTypesArray = getResources().getStringArray(R.array.car_tran_types);
//
//        // Convert the arrays to ArrayLists
//        ArrayList<String> statusList = new ArrayList<>(Arrays.asList(statusArray));
//        ArrayList<String> transTypesList = new ArrayList<>(Arrays.asList(TranTypesArray));
//
//        // Create ArrayAdapter for status spinner
//        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, statusList);
//        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerStatus.setAdapter(statusAdapter);
//
//        // Create ArrayAdapter for type of Benz spinner
//        ArrayAdapter<String> benzTypesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, transTypesList);
//        benzTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTranType.setAdapter(benzTypesAdapter);
//    }
//
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
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Error adding car
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    // JSON parsing error
//                    Toast.makeText(getActivity(), "Error parsing response", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getActivity(), "Network error. Please try again later.", Toast.LENGTH_SHORT).show();
//                    }
//
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("carNumber",  editCarNumber.getText().toString());
//                params.put("model", editModel.getText().toString());
//                params.put("year", editModelYear.getText().toString());
//                params.put("color", editColor.getText().toString());
//                params.put("description", editDescription.getText().toString());
//                params.put("rentalPricePerDay", editRentalPrice.getText().toString());
//                params.put("availabilityStatus", spinnerStatus.getSelectedItem().toString());
//                params.put("imageUrl", ""); // Add image URL if applicable
//                params.put("transmissionTypes", spinnerTranType.getSelectedItem().toString()); // Add transmission type if applicable
//                params.put("numOfPassengers", editPassengers.getText().toString());
//                params.put("usingTypes", editType.getText().toString()); // Add using type if applicable
//
//                params.put("make", editMakeCompany.getText().toString());
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }
//}
