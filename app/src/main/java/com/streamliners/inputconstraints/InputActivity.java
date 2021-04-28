package com.streamliners.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.streamliners.inputconstraints.databinding.ActivityInputBinding;

public class InputActivity extends AppCompatActivity {
    ActivityInputBinding binding;

    // use to store the regex generated
    private StringBuilder regex = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set title of the activity
        setTitle("Input Activity");

        // Get the constraints coming from other activity
        getConstraints();

        // To hide the error when text changes
        setupHideErrorForEditText();
    }

    /**
     * to hide the error of the text field when text changes
     */
    private void setupHideErrorForEditText() {
        binding.inputTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.inputTextField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * to get the constraints which is coming from it's parent activity
     */
    private void getConstraints() {
        // array for the constraints
        int[] type = getIntent().getExtras().getIntArray(Constants.CONSTRAINTS);

        // making regex on the basis of constraints selected
        regex.append("^[");
        if (type[0] == 1) {
            regex.append("A-Z");
        }
        if (type[1] == 1) {
            regex.append("a-z");
        }
        if (type[2] == 1) {
            regex.append("0-9");
        }
        if (type[3] == 1) {
            regex.append("+\\-*/%");
        }
        if (type[4] == 1) {
            regex.append("@#$&!*");
        }
        regex.append("]*");
    }

    /**
     * send the entered input back to the parent activity
     * @param view view of the user interface
     */
    public void sendInput(View view) {
        // text entered for the result
        String text = binding.inputTextField.getEditText().getText().toString().trim();

        // check that the text is not empty
        if (text.isEmpty()){
            binding.inputTextField.setError("Please enter text");
            return;
        }
        // matching the text with the regex generated
        else if (!text.matches(regex.toString())) {
            binding.inputTextField.setError("Please enter valid text");
            return;
        }

        // sending the result back to the activity
        Intent intent = new Intent();
        intent.putExtra(Constants.RESULT_STRING, text);
        setResult(RESULT_OK, intent);

        // to close this activity
        finish();
    }
}