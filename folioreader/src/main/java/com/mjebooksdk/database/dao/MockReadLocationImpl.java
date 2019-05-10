package com.mjebooksdk.database.dao;

import com.mjebooksdk.database.ReadLocation;

import java.util.ArrayList;
import java.util.List;

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
        ReadLocation readLocation = new ReadLocation();
        for (int i = 0; i < 10; i++) {
            readLocation.setTitle(""+i);
            readLocation.setId(""+i);
            readLocations.add(readLocation);

        }
        return readLocations;
    }
}
