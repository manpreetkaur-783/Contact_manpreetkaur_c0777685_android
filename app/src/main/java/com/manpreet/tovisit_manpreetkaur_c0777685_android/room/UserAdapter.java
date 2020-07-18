package com.manpreet.tovisit_manpreetkaur_c0777685_android.room;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.manpreet.tovisit_manpreetkaur_c0777685_android.R;

import java.util.Arrays;
import java.util.List;

public class UserAdapter extends ArrayAdapter {

    private static final String TAG = "EmployeeAdapter";

    Context context;
    int layoutRes;
    List<User> employeeList;
    //    SQLiteDatabase sqLiteDatabase;
//    DatabaseHelper sqLiteDatabase;
    UserRoomDb employeeRoomDb;


//    public EmployeeAdapter(@NonNull Context context, int resource, List<Employee> employeeList, DatabaseHelper sqLiteDatabase) {
//        super(context, resource, employeeList);
//        this.employeeList = employeeList;
//        this.sqLiteDatabase = sqLiteDatabase;
//        this.context = context;
//        this.layoutRes = resource;
//    }

    public UserAdapter(@NonNull Context context, int resource, List<User> employeeList) {
        super(context, resource, employeeList);
        this.employeeList = employeeList;
        this.context = context;
        this.layoutRes = resource;
        employeeRoomDb = UserRoomDb.getInstance(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutRes, null);
        TextView nameTV = v.findViewById(R.id.first_name);
        TextView lastNameTV = v.findViewById(R.id.last_name);
        TextView emailTV = v.findViewById(R.id.tv_email);
        TextView addressTV = v.findViewById(R.id.address);
        TextView phnTV = v.findViewById(R.id.tv_phn);

        final User employee = employeeList.get(position);
        nameTV.setText(employee.getFirstName());
        lastNameTV.setText(employee.getLastName());
        emailTV.setText(employee.getEmail());
        addressTV.setText(employee.getAddress());
        phnTV.setText(employee.getPhone());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }

            private void updateEmployee(final User employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_user, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText etName = view.findViewById(R.id.et_first_name);
                final EditText etLastName = view.findViewById(R.id.last_name);
                final EditText etEmail = view.findViewById(R.id.et_email);
                final EditText etPhn = view.findViewById(R.id.et_phn);
                final EditText etAddress = view.findViewById(R.id.et_address);


                etName.setText(employee.getFirstName());
                etLastName.setText(employee.getLastName());
                etEmail.setText(employee.getEmail());
                etAddress.setText(employee.getAddress());
                etPhn.setText(employee.getPhone());


                view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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


                        /*String sql = "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new String[]{name, department, salary, String.valueOf(employee.getId())});*/

                        /*if (sqLiteDatabase.updateEmployee(employee.getId(), name, department, Double.parseDouble(salary)))
                            loadEmployees();*/

                        // Room
                        employeeRoomDb.userDao().updateEmployee(employee.getId(),
                                name, lastName, email, phn, address);
                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee);
            }

            private void deleteEmployee(final User employee) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String sql = "DELETE FROM employee WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});*/
                        /*if (sqLiteDatabase.deleteEmployee(employee.getId()))
                            loadEmployees();*/
                        // Room

                        employeeRoomDb.userDao().deleteEmployee(employee.getId());
                        loadEmployees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The employee (" + employee.getFirstName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Log.d(TAG, "getView: " + getCount());
        return v;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    private void loadEmployees() {
        /*String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);*/

        /*Cursor cursor = sqLiteDatabase.getAllEmployees();
        employeeList.clear();
        if (cursor.moveToFirst()) {
            do {
                // create an employee instance
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }*/

        employeeList = employeeRoomDb.userDao().getAllEmployees();
        notifyDataSetChanged();
    }
}