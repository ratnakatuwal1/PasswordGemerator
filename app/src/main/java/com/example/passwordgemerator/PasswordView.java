package com.example.passwordgemerator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordgemerator.Adapter.PasswordAdapter;
import com.example.passwordgemerator.Model.passwordModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PasswordView extends AppCompatActivity {
    private PasswordAdapter passwordAdapter;
    private RecyclerView recyclerView;
    private List<passwordModel> passwordList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        passwordAdapter = new PasswordAdapter(this, passwordList);
        passwordList = new ArrayList<>();
        recyclerView.setAdapter(passwordAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("SavePassword");
    }
}