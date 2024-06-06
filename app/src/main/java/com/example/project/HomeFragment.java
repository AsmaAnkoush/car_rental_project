package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarCardViewAdapter adapter;
    private ArrayList<Car> carsArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.carrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ArrayList to hold car data
        carsArrayList = new ArrayList<>();

        // Initialize RecyclerView Adapter
        adapter = new CarCardViewAdapter(getContext(), carsArrayList);
        recyclerView.setAdapter(adapter);

        // Populate RecyclerView with data
        CreatDataForRecyclerView();

        return rootView;
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
