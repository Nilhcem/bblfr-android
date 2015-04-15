package com.nilhcem.bblfr.model;

import android.database.sqlite.SQLiteDatabase;

import ollie.Ollie;

public abstract class JsonToDatabaseDao<T> {

    public void deleteExistingData() {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            deleteExistingData(database);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public abstract void saveJsonToDatabase(T data);

    public abstract void deleteExistingData(SQLiteDatabase database);
}
