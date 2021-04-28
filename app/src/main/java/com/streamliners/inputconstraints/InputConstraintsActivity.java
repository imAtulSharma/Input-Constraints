package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.streamliners.inputconstraints.databinding.ActivityInputConstraintsBinding;

public class InputConstraintsActivity extends AppCompatActivity {
    // request code of data transfer
    private static final int REQUEST_CODE = 0;

    ActivityInputConstraintsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputConstraintsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set title of the activity
        setTitle("SelectConstraints Activity");

        // make the text field gone
        binding.resultTextView.setVisibility(View.GONE);

        setupTextFieldErrorHide();
    }

    /**
     * To hide the error when any of the check box selected
     */
    private void setupTextFieldErrorHide() {
        // Initializing the state changed listener
        CompoundButton.OnCheckedChangeListener myListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.resultTextView.setVisibility(View.GONE);
            }
        };

        // attach the listener to all check boxes
        binding.uppercaseCheckBox.setOnCheckedChangeListener(myListener);
        binding.lowercaseCheckBox.setOnCheckedChangeListener(myListener);
        binding.digitCheckBox.setOnCheckedChangeListener(myListener);
        binding.mathematicalCheckBox.setOnCheckedChangeListener(myListener);
        binding.symbolCheckBox.setOnCheckedChangeListener(myListener);
    }

    /**
     * to open the input activity based on constraints
     * @param view view of the user interface
     */
    public void takeInput(View view) {
        int[] type = {0, 0, 0, 0, 0};

        // Check for at least one check box selected
        if (!(binding.uppercaseCheckBox.isChecked() || binding.lowercaseCheckBox.isChecked() || binding.digitCheckBox.isChecked() || binding.mathematicalCheckBox.isChecked() || binding.symbolCheckBox.isChecked())) {
            // set color to red
            binding.resultTextView.setTextColor(getResources().getColor(R.color.red));
            // make the text field visible
            binding.resultTextView.setVisibility(View.VISIBLE);
            // set text
            binding.resultTextView.setText("Please select at least one constraint");
            return;
        }

        // make the array according to the check box selected
        if (binding.uppercaseCheckBox.isChecked())
            type[0] += 1;
        if (binding.lowercaseCheckBox.isChecked())
            type[1] += 1;
        if (binding.digitCheckBox.isChecked())
            type[2] += 1;
        if (binding.mathematicalCheckBox.isChecked())
            type[3] += 1;
        if (binding.symbolCheckBox.isChecked())
            type[4] += 1;

        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra(Constants.CONSTRAINTS, type);

        // start input activity for the result
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check the result status
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // set the color to green and set result string
            binding.resultTextView.setTextColor(Color.GREEN);
            binding.resultTextView.setText("Result is: " + data.getStringExtra(Constants.RESULT_STRING));
        } else {
            // set the color to red and set the error string
            binding.resultTextView.setTextColor(Color.RED);
            binding.resultTextView.setText("No data received!");
        }
            binding.resultTextView.setVisibility(View.VISIBLE);
    }
}