package com.example.project;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.carcardview.CarCardViewAdapter;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Car;

import com.example.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarCardViewAdapter adapter;
    private ArrayList<Car> carsArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.carrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carsArrayList = new ArrayList<>();


        adapter = new CarCardViewAdapter(getContext(), carsArrayList);
        recyclerView.setAdapter(adapter);

        fetchCarData();

        return rootView;
    }

    private void fetchCarData() {

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // URL of the API endpoint to fetch car data
        //"C:\xampp\htdocs\carrentalphp\getcars.php"
//        String url = "http://localhost/carrentalphp/getcars.php";
        //"C:\xampp\htdocs\carrentalphp"
//        String url = "http://10.10.2.2/carrentalphp/getCars.php";

//        String url = "http://192.168.56.1/carrentalphp/getcars.php";
//"C:\xampp\htdocs\carrentalphp\getcars.php"
//        String url = "http://localhost:80/carrentalphp/getcars.php";
//        String url = "http://localhost/carrentalphp/getcars.php";
//        String url = "http://10.0.2.2:80/carrentalphp/getcars.php";
//        String url = "http://localhost/carrentalphp/getcars.php";
//        String url="http://192.168.56.1:80/carrentalphp/getcars.php";

        String url = "http://10.0.2.2/carRentalPHP/viewAllData.php";
        // Request a JSON response from the provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            carsArrayList.clear();

                            // Parse JSON response and add cars to the ArrayList
                            for (int i = 0; i < response.length(); i++) {
                                Log.i("string", "onResponse: i :"+i);
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.d(TAG, "fetchCarData: i : "+ i);

                                int carId = jsonObject.getInt("car_id");

                                int carNumber = jsonObject.getInt("car_Number");
                                String make = jsonObject.getString("make");
                                String model = jsonObject.getString("model");
                                int year = jsonObject.getInt("year");
                                String color = jsonObject.getString("color");
                                String description = jsonObject.getString("description");
                                double rentalPricePerDay = jsonObject.getDouble("rental_price_per_day");
                                String availabilityStatus = jsonObject.getString("availability_status");
                                String imageUrl = jsonObject.getString("image_url");
                                String transmissionTypes = jsonObject.getString("transmissionTypes");
                                int numOfPassengers = jsonObject.getInt("numOfPassengers");
                                String usingType = jsonObject.getString("usingType");

                                // Create a Car object and add it to the ArrayList
                                Car car = new Car(carId, carNumber, make, model, year, color, description,
                                        rentalPricePerDay, availabilityStatus, imageUrl, transmissionTypes,
                                        numOfPassengers, usingType);
                                carsArrayList.add(car);
                            }

                            // Notify the adapter about the changes in the data set
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }

    private void CreatDataForRecyclerView() {
        // Adding more cars to the list
        Car car = new Car(1, 1001, "Tesla", "Model S", 2022, "Red",
                "Electric sedan", 199.99, "Available",
                "car1", "Automatic", 5, "Personal");
        carsArrayList.add(car);

        Car car1 = new Car(1, 1001, "Tesla", "Model S", 2022, "Red",
                "Electric sedan", 199.99, "Available",
                "car2jpg", "Automatic", 5, "Personal");
        carsArrayList.add(car1);

        Car car2 = new Car(2, 1002, "BMW", "X5", 2021, "Black",
                "Luxury SUV", 149.99, "Available",
                "car3", "Automatic", 5, "Personal");
        carsArrayList.add(car2);

        Car car3 = new Car(3, 1003, "Audi", "A4", 2020, "White",
                "Compact executive car", 129.99, "Rented",
                "car4", "Automatic", 5, "Business");
        carsArrayList.add(car3);

        Car car4 = new Car(4, 1004, "Ford", "Mustang", 2019, "Blue",
                "Sports car", 159.99, "Available",
                "car1", "Manual", 4, "Personal");
        carsArrayList.add(car4);

        // Notify the adapter about the changes in the data set
        adapter.notifyDataSetChanged();
    }


}
