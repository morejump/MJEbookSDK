package com.mjebooksdk.database.dao;

import com.mjebooksdk.database.ReadLocation;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.List;

public class ReadLocationImpl implements IReadLocationDao {

    Realm realm = Realm.getDefaultInstance();

    @Override
    public void addReadLocation(final ReadLocation readLocation) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(readLocation);
            }
        });
    }

    @Override
    public void updateReadLocation(final ReadLocation readLocation) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(readLocation);
            }
        });

    }

    @Override
    public void deleteReadLoctionById(String id) {
        final ReadLocation readLocation = realm.where(ReadLocation.class).equalTo("id", id).findFirst();
        if (readLocation != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                 readLocation.deleteFromRealm();
                }
            });
        }
    }

    @Override
    public ReadLocation getReadLocationById(String id) {
        ReadLocation readLocation = realm.where(ReadLocation.class).equalTo("id", id).findFirst();
        return readLocation;
    }

    @Override
    public List<ReadLocation> getAllReadLocation() {
        final RealmResults<ReadLocation> results = realm.where(ReadLocation.class).findAll();
        return results;
    }

    @Override
    public void updateReadLocationByTitle(final ReadLocation readLocation, final String title) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                readLocation.setTitle(title);
                bgRealm.copyToRealmOrUpdate(readLocation);
            }
        });
    }
}
