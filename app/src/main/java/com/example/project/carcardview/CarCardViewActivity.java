package com.example.project.carcardview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.Car;
import com.example.project.R;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CarCardViewActivity extends AppCompatActivity {

    private RecyclerView carrecyclerView;
    private  CarCardViewAdapter caradapter;
    private ArrayList<Car> carsarraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_card_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        InitializeCardView();


    }


    private void InitializeCardView() {
        carrecyclerView=findViewById(R.id.carrecyclerview);
        carrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carsarraylist=new ArrayList<>();

        caradapter=new CarCardViewAdapter(this, carsarraylist);
        carrecyclerView.setAdapter(caradapter);

        CreatDataForRecyclerView();
    }

    private void CreatDataForRecyclerView() {
        // Adding more cars to the list
        Car car = new Car(1, 1001, "Tesla", "Model S", 2022, "Red",
                "Electric sedan", 199.99, "Available",
                "car1", "Automatic", 5, "Personal");
        carsarraylist.add(car);

        Car car1 = new Car(1, 1001, "Tesla", "Model S", 2022, "Red",
                "Electric sedan", 199.99, "Available",
                "car2jpg", "Automatic", 5, "Personal");
        carsarraylist.add(car1);

        Car car2 = new Car(2, 1002, "BMW", "X5", 2021, "Black",
                "Luxury SUV", 149.99, "Available",
                "car3", "Automatic", 5, "Personal");
        carsarraylist.add(car2);

        Car car3 = new Car(3, 1003, "Audi", "A4", 2020, "White",
                "Compact executive car", 129.99, "Rented",
                "car4", "Automatic", 5, "Business");
        carsarraylist.add(car3);

        Car car4 = new Car(4, 1004, "Ford", "Mustang", 2019, "Blue",
                "Sports car", 159.99, "Available",
                "car1", "Manual", 4, "Personal");
        carsarraylist.add(car4);

        // Notify the adapter about the changes in the data set
        caradapter.notifyDataSetChanged();
    }


}