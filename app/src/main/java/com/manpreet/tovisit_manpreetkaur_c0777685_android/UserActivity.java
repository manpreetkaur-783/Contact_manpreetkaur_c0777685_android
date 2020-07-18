package com.manpreet.tovisit_manpreetkaur_c0777685_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.User;
import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.UserAdapter;
import com.manpreet.tovisit_manpreetkaur_c0777685_android.room.UserRoomDb;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private UserRoomDb employeeRoomDb;
    EditText editQuery;
    Button search;
    List<User> userList;
    ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        usersListView = findViewById(R.id.lv_user);
        editQuery = findViewById(R.id.edit_query);
        search = findViewById(R.id.search);
        userList = new ArrayList<>();

        employeeRoomDb = UserRoomDb.getInstance(this);
        loadUser();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = employeeRoomDb.userDao().search("%" + editQuery.getText().toString() + "%");
                UserAdapter employeeAdapter = new UserAdapter(UserActivity.this, R.layout.list_layout_user, userList);
                usersListView.setAdapter(employeeAdapter);
            }
        });
    }

    private void loadUser() {

        userList = employeeRoomDb.userDao().getAllEmployees();

        UserAdapter employeeAdapter = new UserAdapter(this, R.layout.list_layout_user, userList);
        usersListView.setAdapter(employeeAdapter);
    }
}
