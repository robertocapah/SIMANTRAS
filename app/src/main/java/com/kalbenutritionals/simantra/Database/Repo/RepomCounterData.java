package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class RepomCounterData implements CRUD {
    private DatabaseHelper helper;

    public RepomCounterData(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmCounterData object = (ClsmCounterData) item;
        try {
            index = helper.getmCounterDataDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmCounterData object = (ClsmCounterData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getmCounterDataDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Object item) throws SQLException {
        return 0;
    }

    @Override
    public Object findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<?> findAll() throws SQLException {
        return null;
    }
}
