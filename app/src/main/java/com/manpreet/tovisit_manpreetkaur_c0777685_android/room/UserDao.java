package com.manpreet.tovisit_manpreetkaur_c0777685_android.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertEmployee(User employee);

    @Query("DELETE FROM user")
    void deleteAllEmployees();

    @Query("DELETE FROM user WHERE id = :id")
    int deleteEmployee(int id);

    @Query("UPDATE user SET firstName = :name, lastName = :lastName, email = :email, phone = :phn, address = :address WHERE id = :id")
    int updateEmployee(int id, String name, String lastName, String email, String phn, String address);

    @Query("SELECT * FROM user ORDER BY firstName")
    List<User> getAllEmployees();

    @Query("SELECT * FROM user where firstName LIKE :name or lastName LIKE:name or email LIKE:name or phone LIKE:name or address LIKE:name ORDER BY firstName")
    List<User> search(String name);

}