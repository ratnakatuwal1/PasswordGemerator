package com.example.passwordgemerator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordgemerator.Model.passwordModel;
import com.example.passwordgemerator.R;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.passwordViewHolder> {
    private Context context;
    private List<passwordModel> passwordList;

    public PasswordAdapter(Context context, List<passwordModel> passwordList) {
        this.context = context;
        this.passwordList = passwordList;
    }

    @NonNull
    @Override
    public passwordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.password, parent, false);
        return new passwordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull passwordViewHolder holder, int position) {
        passwordModel model = passwordList.get(position);
       holder.appName.setText(model.getAppName());
       holder.email.setText(model.getEmail());
       holder.password.setText(model.getPassword());
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public class passwordViewHolder extends RecyclerView.ViewHolder {
        TextView appName, email, password;
        public passwordViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.appName);
            email = itemView.findViewById(R.id.email);
            password = itemView.findViewById(R.id.password);
        }
    }
}
