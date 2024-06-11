package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatFragment extends Fragment {

    private static final String TAG = "StatisticsFragment";
    private static final String PHP_SCRIPT_URL = "http://10.0.2.2/carrentalphp/get_statistics.php";
    private EditText totalBookingsEditText;
    private EditText totalRevenueEditText;
    private EditText totalAvailableCarsEditText;
    private DatePicker datePickerStart;
    private DatePicker datePickerEnd;
    private Button buttonCompute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);

        totalBookingsEditText = view.findViewById(R.id.editTextBookedCars);
        totalRevenueEditText = view.findViewById(R.id.editTextTotalPayment); // Corrected
        totalAvailableCarsEditText = view.findViewById(R.id.editTextAvailableCars); // Corrected
        datePickerStart = view.findViewById(R.id.datePickerStartDate);
        datePickerEnd = view.findViewById(R.id.datePickerEndDate);
        buttonCompute = view.findViewById(R.id.buttonCompute);

        buttonCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchStatistics();
            }
        });

        return view;
    }

    private void fetchStatistics() {
        String startDate = getFormattedDate(datePickerStart);
        String endDate = getFormattedDate(datePickerEnd);

        if (startDate.compareTo(endDate) > 0) {
            Log.e(TAG, "End date must be after start date");
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        String url = PHP_SCRIPT_URL + "?start_date=" + startDate + "&end_date=" + endDate;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int totalBookings = response.getInt("total_bookings");
                            double totalRevenue = response.getDouble("total_revenue");
                            int totalAvailableCars = response.getInt("total_available_cars");

                            totalBookingsEditText.setText(String.valueOf(totalBookings));
                            totalRevenueEditText.setText(String.valueOf(totalRevenue));
                            totalAvailableCarsEditText.setText(String.valueOf(totalAvailableCars));
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error: " + error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private String getFormattedDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
