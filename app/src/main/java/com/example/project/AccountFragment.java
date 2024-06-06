package com.example.project;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        displayPersonalInformation(view);

        ImageView personalInfoImageView = view.findViewById(R.id.buttonPersonalInfo);
        ImageView settingsImageView = view.findViewById(R.id.buttonSettings);
        ImageView notificationsImageView = view.findViewById(R.id.buttonNotifications);

        personalInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPersonalInformation(view); // Pass the view obtained in onCreateView
            }
        });

        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = view.findViewById(R.id.resultsLayout); // Replace "resultsLayout" with the ID of your LinearLayout

                // Clear existing views in the LinearLayout (optional, depending on your requirement)
                linearLayout.removeAllViews();

                // Create a LinearLayout for the language label and spinner
                LinearLayout languageLayout = new LinearLayout(getContext());
                languageLayout.setOrientation(LinearLayout.HORIZONTAL);
                languageLayout.setPadding(20, 20, 20, 20); // Adjust as needed

                // Create TextView for language label
                TextView languageLabel = new TextView(getContext());
                languageLabel.setText("Language:");
                languageLabel.setTextColor(getResources().getColor(R.color.black));
                // Reduce padding to minimize space between TextView and Spinner
                languageLabel.setPadding(40, 40, 250, 40);

                // Create a Spinner (ComboBox) for language selection
                Spinner languageSpinner = new Spinner(getContext());
                ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"Arabic", "English", "Abri"});
                languageSpinner.setAdapter(languageAdapter);


                // Set layout parameters for the spinner to ensure it is near the TextView
                LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                languageSpinner.setLayoutParams(spinnerParams);

                // Add TextView and Spinner to the LinearLayout
                languageLayout.addView(languageLabel);
                languageLayout.addView(languageSpinner);

                // Create a TableLayout for changing password
                TableLayout passwordTableLayout = new TableLayout(getContext());
                TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                passwordTableLayout.setLayoutParams(tableParams);

                // Add rows for changing password
                addRowToTable(passwordTableLayout, "Old Password:", " ", true);
                addRowToTable(passwordTableLayout, "New Password", " ", true);
                addRowToTable(passwordTableLayout, "Confirm New Password:", "", true);
                // Create a Button for confirming password change
                Button confirmButton = new Button(getContext());
                confirmButton.setText("Confirm and Apply");
                confirmButton.setAllCaps(false);

                // Add the OnClickListener for the Confirm button
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the click event for the Confirm button
                    }
                });

                // Add the language LinearLayout, TableLayout, and Button to the LinearLayout
                linearLayout.addView(languageLayout);
                linearLayout.addView(passwordTableLayout);
                linearLayout.addView(confirmButton);
            }
        });

        notificationsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show list view of notifications
                showNotificationListView(v.getContext());
            }
        });

        return view;
    }
// Inside your activity or fragment where notificationsImageView is defined

    private void displayPersonalInformation(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.resultsLayout);
        linearLayout.removeAllViews();

        TableLayout tableLayout = new TableLayout(getContext());

        addRowToTable(tableLayout, "Full Name:", "John Doe", true);
        addRowToTable(tableLayout, "Mobile:", "+1234567890", true);
        addRowToTable(tableLayout, "Tel:", "+987654321", true);
        addRowToTable(tableLayout, "Address:", "123 Street, City", true);
        addRowToTable(tableLayout, "Password:", "77777", true);

        Button editProfileButton = new Button(getContext());
        editProfileButton.setText("Edit Profile");
        editProfileButton.setAllCaps(false);

        // Add OnClickListener for the "Edit Profile" button if needed

        linearLayout.addView(tableLayout);
        linearLayout.addView(editProfileButton);
    }
    private void showNotificationListView(Context context) {
        // Create dummy notification data
        List<String> dummyNotifications = createDummyNotifications();

        // Create and set up the list view
        ListView listView = new ListView(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, dummyNotifications);
        listView.setAdapter(adapter);

        // Show the list view in a dialog
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

    // Method to create dummy notification data
    private List<String> createDummyNotifications() {
        List<String> notifications = new ArrayList<>();
        Random rand = new Random();

        // Generate random dummy notifications
        for (int i = 0; i < 5; i++) { // Change 5 to the desired number of notifications
            String type = rand.nextBoolean() ? "Rent" : "Return";
            String id = String.valueOf(rand.nextInt(1000));
            String message = rand.nextBoolean() ? "Your car rental (ID: " + id + ") is confirmed. Enjoy your ride!" : "Reminder: Your car rental (ID: " + id + ") is due tomorrow. Please return it on time.";
            notifications.add(type + ": " + message);
        }
        return notifications;
    }



    // Method to add a row to the table layout with a TextView and an EditText
    private void addRowToTable(TableLayout tableLayout, String label, String value, boolean isEditable) {
        // Create TableRow
        TableRow row = new TableRow(getContext());

        // Set padding and margins for the row
        row.setPadding(10, 10, 10, 10);


        TextView textViewLabel = new TextView(getContext());
        textViewLabel.setText(label);
        textViewLabel.setPadding(40, 40, 40, 40);
        textViewLabel.setTextColor(getResources().getColor(R.color.black));

        EditText editTextValue = new EditText(getContext());
        editTextValue.setText(value);
        editTextValue.setPadding(40, 40, 40, 40);
        editTextValue.setTextColor(getResources().getColor(R.color.black));
        editTextValue.setBackgroundResource(R.drawable.table_cell_bg);
        editTextValue.setEnabled(isEditable);
        editTextValue.setWidth(400);

        row.addView(textViewLabel);
        row.addView(editTextValue);


        tableLayout.addView(row);
    }
    private void addPasswordRowToTable(TableLayout tableLayout, String label, String value, boolean isEditable) {
        // Create TableRow
        TableRow row = new TableRow(getContext());

        // Set padding and margins for the row
        row.setPadding(30, 30, 30, 30);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 30, 30, 30);
        row.setLayoutParams(params);

        // Create TextView for label
        TextView textViewLabel = new TextView(getContext());
        textViewLabel.setText(label);
        textViewLabel.setPadding(50, 50, 50, 50);
        textViewLabel.setTextColor(getResources().getColor(R.color.black));

        // Create EditText for value
        EditText editTextValue = new EditText(getContext());
        editTextValue.setText(value);
        editTextValue.setPadding(50, 50, 50, 50);
        editTextValue.setTextColor(getResources().getColor(R.color.black));
        editTextValue.setBackgroundResource(R.drawable.table_cell_bg);
        editTextValue.setEnabled(isEditable); // Set whether EditText is editable or not
        editTextValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Add TextView and EditText to TableRow
        row.addView(textViewLabel);
        row.addView(editTextValue);

        tableLayout.addView(row);
    }


}
