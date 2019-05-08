package com.mjebooksdk.model.dao;

import com.mjebooksdk.model.ReadLocation;

public interface IReadLocationDao {
    void add(ReadLocation readLocation);
    void update(ReadLocation readLocation);
    void delete(String id);
    void getAll();
}
