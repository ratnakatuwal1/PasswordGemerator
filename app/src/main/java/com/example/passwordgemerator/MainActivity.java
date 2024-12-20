package com.example.passwordgemerator;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.passwordgemerator.Model.passwordModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {
    // Declaring the UI Element
    SeekBar characterLengthSeekBar;
    TextView characterLengthValue, generatedPassword;
    EditText websiteNameInput, emailInput;
    Button generateButton, button, viewPassword;
    RadioGroup optionsGroup;
    CheckBox includeUpperCase, includeLowerCase, includeNumbers, includeSymbols;
private DatabaseReference databaseReference;

    //Defining the character sets
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz"; //lowercase letter
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //uppercase letter
    private static final String NUMBERS = "0123456789"; //numbers
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?"; //symbols
    private SecureRandom random = new SecureRandom(); // Secure random generator for better randomness

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initializing UI components
        characterLengthSeekBar = findViewById(R.id.characterLengthSeekBar);
        characterLengthValue = findViewById(R.id.characterLengthValue);
        generateButton = findViewById(R.id.generateButton);
        optionsGroup = findViewById(R.id.optionsGroup);
        includeUpperCase = findViewById(R.id.includeUpperCase);
        includeLowerCase = findViewById(R.id.includeLowerCase);
        includeNumbers = findViewById(R.id.includeNumbers);
        includeSymbols = findViewById(R.id.includeSymbols);
        generatedPassword = findViewById(R.id.generatedPassword);
        button = findViewById(R.id.button);
        viewPassword = findViewById(R.id.viewPassword);
        websiteNameInput = findViewById(R.id.websiteNameInput);
        emailInput = findViewById(R.id.emailInput);
        databaseReference = FirebaseDatabase.getInstance().getReference("ViewPassword");

        characterLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the displayed character length based on SeekBar value
                characterLengthValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateButton.setOnClickListener(v -> {
            generatedPassword();
        });

        button.setOnClickListener(v -> {
            copyPassword();
        });

        viewPassword.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PasswordView.class));
        });
    }

    private void copyPassword() {
        String password = generatedPassword.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "No password to copy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the ClipboardManager system service
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a ClipData object to hold the copied text
        android.content.ClipData clip = android.content.ClipData.newPlainText("Password", password);

        // Set the clipboard content
        clipboard.setPrimaryClip(clip);

        // Show a confirmation message
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void generatedPassword() {
        int passwordLength = characterLengthSeekBar.getProgress();
        StringBuilder password = new StringBuilder();
        StringBuilder characterSet = new StringBuilder();

        if (includeLowerCase.isChecked()) {
            characterSet.append(LOWERCASE);
        }

        if (includeUpperCase.isChecked()) {
            characterSet.append(UPPERCASE);
        }

        if (includeNumbers.isChecked()) {
            characterSet.append(NUMBERS);
        }

        if (includeSymbols.isChecked()) {
            characterSet.append(SYMBOLS);
        }

        if (characterSet.length() == 0) {
            generatedPassword.setText("Please select at least one character type");
            return;
        }

        int requiredLength = passwordLength;
        if (includeSymbols.isChecked()) {
            // If symbols are selected, ensure all symbols appear at least once
            password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));  // Add one random symbol

            // Update password length to account for the symbol
            passwordLength--;
        }

        // Generate the remaining part of the password randomly from the character pool
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(randomIndex));
        }

        String finalPassword = shufflePassword(password.toString());
        generatedPassword.setText(finalPassword);

        savePasswordToFirebase(finalPassword);
    }

    private void savePasswordToFirebase(String Password) {
        String websiteName = websiteNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        
        if (websiteName.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "App name and email are requires", Toast.LENGTH_SHORT).show();
            return;
        }

        passwordModel model = new passwordModel(websiteName, email, Password);
        databaseReference.push().setValue(model).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Password save successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save password", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String shufflePassword(String password) {
        char[] passwordArray = password.toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        return new String(passwordArray);
    }
}