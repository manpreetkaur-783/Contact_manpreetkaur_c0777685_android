package com.manpreet.tovisit_manpreetkaur_c0777685_android.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDb extends RoomDatabase {

    private static final String DB_NAME = "user_room_db";

    public abstract UserDao userDao();

    private static volatile UserRoomDb INSTANCE;

    public static UserRoomDb getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserRoomDb.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        return INSTANCE;
    }
}