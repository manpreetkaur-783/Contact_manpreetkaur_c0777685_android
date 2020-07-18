package com.manpreet.tovisit_manpreetkaur_c0777685_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.User;
import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.UserAdapter;
import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.UserRoomDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private UserRoomDb employeeRoomDb;

    EditText etName, etLastName, etEmail, etPhn, etAddress;
    Spinner spinnerDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.last_name);
        etEmail = findViewById(R.id.et_email);
        etPhn = findViewById(R.id.et_phn);
        etAddress = findViewById(R.id.et_address);


        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_view_employees).setOnClickListener(this);

        employeeRoomDb = UserRoomDb.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_employee:
                addEmployee();
                break;
            case R.id.tv_view_employees:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }

    }

    private void addEmployee() {
        String name = etName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phn = etPhn.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("name field cannot be empty");
            etName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            etLastName.setError("lastName field cannot be empty");
            etLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("name field cannot be empty");
            etEmail.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            etAddress.setError("name field cannot be empty");
            etAddress.requestFocus();
            return;
        }
        if (phn.isEmpty()) {
            etPhn.setError("name field cannot be empty");
            etPhn.requestFocus();
            return;
        }

        // Insert into room db
        User employee = new User(name, lastName, email, phn, address);
        employeeRoomDb.userDao().insertEmployee(employee);
        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();

    }

}
