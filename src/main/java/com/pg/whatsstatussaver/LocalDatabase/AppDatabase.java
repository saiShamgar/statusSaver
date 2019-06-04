package com.pg.whatsstatussaver.LocalDatabase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ImagesUrlTable.class}, version = 2 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DatabaseOperations operations();
}
