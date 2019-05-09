package com.mjebooksdk.model.dao;

import com.mjebooksdk.model.ReadLocation;

import java.util.List;

public interface IReadLocationDao {
    void add(ReadLocation readLocation);
    void update(ReadLocation readLocation);
    void delete(String id);
    List<ReadLocation> getAll();
}
