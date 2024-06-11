package com.example.project.carcardview;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.project.Car;
import com.example.project.DeletCar;
import com.example.project.R;
import com.example.project.UpdateCar;
import com.example.project.ViewDataCar;

import java.util.ArrayList;
import java.util.Locale;

    public class CarCardViewAdapter extends RecyclerView.Adapter<CarCardViewAdapter.CarHolder> {

        private Context carcontext;
        private ArrayList<Car> cars;

        public CarCardViewAdapter(Context carcontext, ArrayList<Car> cars) {
            this.carcontext = carcontext;
            this.cars = cars;
        }


        @NonNull
        @Override
        public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(carcontext).inflate(R.layout.car_item_card,parent,false);
            return new CarHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull CarHolder holder, int position) {

            Car car=cars.get(position);
            holder.setDetails(car);

        }

        @Override
        public int getItemCount() {
            return cars.size();
        }


        //viewholder : CarHolder
        public class CarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView txtMode,txtMake ,  txtrentalPricePerDay ,txttransmissionTypes;
            private ImageView imageView,viewImageView,deletImageView,updateImageView;
            CarHolder(View itemView){
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                txtMake=itemView.findViewById(R.id.txtMake);
                txtMode=itemView.findViewById(R.id.txtModel);
                txtrentalPricePerDay=itemView.findViewById(R.id.txtrentalPricePerDay);
                txttransmissionTypes=itemView.findViewById(R.id.txtrentalPricePerDay);

                viewImageView=itemView.findViewById(R.id.viewImageView);
                deletImageView=itemView.findViewById(R.id.deletImageView);
                updateImageView=itemView.findViewById(R.id.updateImageView);

                viewImageView.setOnClickListener(this);
                updateImageView.setOnClickListener(this);
                deletImageView.setOnClickListener(this);

            }


            void setDetails(Car car){

//                int resourceId = carcontext.getResources().getIdentifier(car.getImageUrl(), "drawable", carcontext.getPackageName());
//                imageView.setImageResource(resourceId);

                Glide.with(carcontext)
                        .load(car.getImageUrl()) // Assuming getImageUrl() returns the URL of the image
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image
                        .into(imageView);

                txtMake.setText(car.getMake());
                txtMode.setText(car.getModel());
                txttransmissionTypes.setText(car.getTransmissionTypes());
//            txtrentalPricePerDay.setText(String.valueOf(car.getRentalPricePerDay()));
                txtrentalPricePerDay.setText(String.format(Locale.US, "Price: $%.2f per day", car.getRentalPricePerDay()));

            }


            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();

                Car clickedCar = cars.get(position);
                int id=clickedCar.getCarId();

                // Handle the clicks
                if (v.getId() == R.id.deletImageView) {

                    Intent intent = new Intent(carcontext, DeletCar.class);
                    intent.putExtra("id",id);
                    carcontext.startActivity(intent);
                } else if (v.getId() == R.id.updateImageView) {

                    Intent intent = new Intent(carcontext, UpdateCar.class);
                    intent.putExtra("id",id);
                    carcontext.startActivity(intent);
                } else if (v.getId() == R.id.viewImageView) {

                    Intent intent = new Intent(carcontext, ViewDataCar.class);
                    intent.putExtra("id",id);
                    carcontext.startActivity(intent);
                }
            }
        }
    }

