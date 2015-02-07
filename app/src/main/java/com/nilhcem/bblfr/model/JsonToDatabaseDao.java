package com.nilhcem.bblfr.model;

public interface JsonToDatabaseDao<T> {

    void saveJsonToDatabase(T data);
}
