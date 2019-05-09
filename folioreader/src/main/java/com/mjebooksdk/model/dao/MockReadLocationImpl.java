package com.mjebooksdk.model.dao;

import com.mjebooksdk.model.ReadLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockReadLocationImpl implements IReadLocationDao {
    List<ReadLocation> readLocations = new ArrayList<>();
    @Override
    public void add(ReadLocation readLocation) {
        readLocations.add(readLocation);
    }

    @Override
    public void update(ReadLocation readLocation) {
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ReadLocation> getAll() {
        return readLocations;
    }
}
