package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class AccountFragment extends Fragment {

    private RequestQueue requestQueue;
    private LinearLayout resultsLayout;
    private TextView managerName;
    private TextView managerEmail;
    private ImageView managerImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        resultsLayout = view.findViewById(R.id.resultsLayout);
        managerName = view.findViewById(R.id.managerName);
        managerEmail = view.findViewById(R.id.managerEmail);
        managerImage = view.findViewById(R.id.managerImage);

        ImageView personalInfoImageView = view.findViewById(R.id.buttonPersonalInfo);
        ImageView settingsImageView = view.findViewById(R.id.buttonSettings);
        ImageView notificationsImageView = view.findViewById(R.id.buttonNotifications);

        personalInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchManagerDetails();
            }
        });

        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySettings(view);
            }
        });

        notificationsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationListView(v.getContext());
            }
        });

        fetchManagerDetails();

        return view;
    }

    private void fetchManagerDetails() {
        String url = "http://10.0.2.2/carrentalphp/get_manager_details.php"; // تعديل رابط السيرفر الخاص بك

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    // In the fetchManagerDetails() method
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("username");
                            String email = response.getString("email");
                            String mobile = response.getString("mobile");
                            String tel = response.getString("tel");
                            String address = response.getString("address");
                            String password = response.getString("password");

                            managerName.setText(name);
                            managerEmail.setText(email);

                            displayPersonalInformation(name, mobile, tel, address, password);
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

        requestQueue.add(jsonObjectRequest);
    }

    private void displayPersonalInformation(String name, String mobile, String tel, String address, String password) {
        resultsLayout.removeAllViews();

        TableLayout tableLayout = new TableLayout(getContext());

        addRowToTable(tableLayout, "Full Name:", name, true);
        addRowToTable(tableLayout, "Mobile:", mobile, true);
        addRowToTable(tableLayout, "Tel:", tel, true);
        addRowToTable(tableLayout, "Address:", address, true);
        addRowToTable(tableLayout, "Password:", password, true);

        Button editProfileButton = new Button(getContext());
        editProfileButton.setText("Edit Profile");
        editProfileButton.setAllCaps(false);

        resultsLayout.addView(tableLayout);
        resultsLayout.addView(editProfileButton);
    }


    private void showNotificationListView(Context context) {
        List<String> dummyNotifications = createDummyNotifications();

        ListView listView = new ListView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, dummyNotifications);
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Notifications");
        builder.setView(listView);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<String> createDummyNotifications() {
        List<String> notifications = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            String type = rand.nextBoolean() ? "Rent" : "Return";
            String id = String.valueOf(rand.nextInt(1000));
            String message = rand.nextBoolean() ? "Your car rental (ID: " + id + ") is confirmed. Enjoy your ride!" : "Reminder: Your car rental (ID: " + id + ") is due tomorrow. Please return it on time.";
            notifications.add(type + ": " + message);
        }
        return notifications;
    }

    private void addRowToTable(TableLayout tableLayout, String label, String value, boolean isEditable) {
        TableRow row = new TableRow(getContext());
        row.setPadding(10, 10, 10, 10);

        TextView textViewLabel = new TextView(getContext());
        textViewLabel.setText(label);
        textViewLabel.setPadding(40, 40, 40, 40);
        textViewLabel.setTextColor(getResources().getColor(R.color.black));

        EditText editTextValue = new EditText(getContext());
        editTextValue.setText(value);
        editTextValue.setEnabled(isEditable);

        row.addView(textViewLabel);
        row.addView(editTextValue);

        tableLayout.addView(row);
    }
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmedPasswordEditText;

    private void displaySettings(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.resultsLayout);
        linearLayout.removeAllViews();

        LinearLayout languageLayout = new LinearLayout(getContext());
        languageLayout.setOrientation(LinearLayout.HORIZONTAL);
        languageLayout.setPadding(20, 20, 20, 20);

        TextView languageLabel = new TextView(getContext());
        languageLabel.setText("Change Password :");
        languageLabel.setTextColor(getResources().getColor(R.color.black));
        languageLabel.setPadding(20, 40, 250, 40);

        languageLayout.addView(languageLabel);

        TableLayout passwordTableLayout = new TableLayout(getContext());
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        passwordTableLayout.setLayoutParams(tableParams);

        // Add EditTexts for old password, new password, and confirmed password
        oldPasswordEditText = createEditText("Old Password");
        newPasswordEditText = createEditText("New Password");
        confirmedPasswordEditText = createEditText("Confirm New Password");

        addRowToTable(passwordTableLayout, "Old Password:", oldPasswordEditText, true);
        addRowToTable(passwordTableLayout, "New Password", newPasswordEditText, true);
        addRowToTable(passwordTableLayout, "Confirm New Password:", confirmedPasswordEditText, true);

        Button confirmButton = new Button(getContext());
        confirmButton.setText("Confirm and Apply");
        confirmButton.setAllCaps(false);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to change password
                changePassword();
            }
        });

        linearLayout.addView(languageLayout);
        linearLayout.addView(passwordTableLayout);
        linearLayout.addView(confirmButton);
    }

    // Helper method to create EditText fields
    private EditText createEditText(String hint) {
        EditText editText = new EditText(getContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText.setHint(hint);
        return editText;
    }

    private void changePassword() {
        String oldPassword = getOldPasswordFromEditText();
        String newPassword = getNewPasswordFromEditText();
        String confirmedPassword = getConfirmedPasswordFromEditText();

        // Check if the new password matches the confirmed password
        if (!newPassword.equals(confirmedPassword)) {
            // Passwords do not match, display an error message or handle accordingly
            return;
        }

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Define the URL for changing password
        String url = "http://10.0.2.2/carrentalphp/change_password.php";

        // Create a JSONObject with old password, new password, and confirmed password
        JSONObject params = new JSONObject();
        try {
            params.put("old_password", oldPassword);
            params.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // Password changed successfully, display a success message
                                showToast("Password changed successfully!");
                            } else {
                                // Password change failed, display an error message
                                showToast("Failed to change password. Please try again.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e(TAG, "Error changing password: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }
    private String getOldPasswordFromEditText() {
        return oldPasswordEditText.getText().toString();
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private String getNewPasswordFromEditText() {
        return newPasswordEditText.getText().toString();
    }

    private String getConfirmedPasswordFromEditText() {
        return confirmedPasswordEditText.getText().toString();
    }



    private void addRowToTable(TableLayout tableLayout, String label, EditText editTextValue, boolean isEditable) {
        TableRow row = new TableRow(getContext());
        row.setPadding(10, 10, 10, 10);

        TextView textViewLabel = new TextView(getContext());
        textViewLabel.setText(label);
        textViewLabel.setPadding(40, 40, 40, 40);
        textViewLabel.setTextColor(getResources().getColor(R.color.black));

        // No need to create a new EditText, use the one passed as a parameter
        editTextValue.setEnabled(isEditable);

        row.addView(textViewLabel);
        row.addView(editTextValue);

        tableLayout.addView(row);
    }


    private EditText createEditText(boolean isEditable) {
        EditText editText = new EditText(getContext());
        editText.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        editText.setEnabled(isEditable);
        return editText;
    }

}
