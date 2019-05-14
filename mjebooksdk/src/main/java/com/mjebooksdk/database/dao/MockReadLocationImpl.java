package com.mjebooksdk.database.dao;

import com.mjebooksdk.database.ReadLocation;

import java.util.ArrayList;
import java.util.List;

public class MockReadLocationImpl implements IReadLocationDao {
    List<ReadLocation> readLocations = new ArrayList<>();

    @Override
    public void addReadLocation(ReadLocation readLocation) {
        readLocations.add(readLocation);
    }

    @Override
    public void updateReadLocation(ReadLocation readLocation) {
    }

    @Override
    public void deleteReadLoctionById(String id) {

    }

    @Override
    public ReadLocation getReadLocationById(String id) {
        ReadLocation readLocation = new ReadLocation();
        readLocation.setTitle("this is test");
        readLocation.setId("123");
        readLocation.setLocation("{\"bookId\":\"e663d05e3f748dbc112ca0400915a182\",\"href\":\"/1.html\",\"created\":1557672799036,\"locations\":{\"cfi\":\"epubcfi(/0!/4/95:0)\"},\"title\":\"\"}");
        return readLocation;
    }

    @Override
    public List<ReadLocation> getAllReadLocation() {
        ReadLocation readLocation = new ReadLocation();
        for (int i = 0; i < 10; i++) {
            readLocation.setTitle("" + i);
            readLocation.setId("" + i);
            readLocation.setLocation("{\"bookId\":\"e663d05e3f748dbc112ca0400915a182\",\"href\":\"/2.html\",\"created\":1557651302551,\"locations\":{\"cfi\":\"epubcfi(/0!/4/2/1:0)\"},\"title\":\"\"}");
            readLocations.add(readLocation);
        }
        return readLocations;
    }

    @Override
    public void updateReadLocationByTitle(ReadLocation readLocation, String title) {

    }


}
