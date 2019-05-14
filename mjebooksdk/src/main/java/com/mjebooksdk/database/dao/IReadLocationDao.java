package com.mjebooksdk.database.dao;

import com.mjebooksdk.database.ReadLocation;

import java.util.List;

public interface IReadLocationDao {
    void addReadLocation(ReadLocation readLocation);

    void updateReadLocation(ReadLocation readLocation);

    void deleteReadLoctionById(String id);

    ReadLocation getReadLocationById(String id);

    List<ReadLocation> getAllReadLocation();

    void updateReadLocationByTitle(ReadLocation readLocation, String title);


}
