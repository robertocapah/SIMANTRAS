package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kalbenutritionals.simantra.Database.Common.ClsmLocationPertanyaan;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepomLocationDocument implements CRUD {
    DatabaseHelper helper;
    public RepomLocationDocument(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmLocationPertanyaan object = (ClsmLocationPertanyaan) item;
        try {
            index = helper.getmLocationDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmLocationPertanyaan object = (ClsmLocationPertanyaan) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmLocationDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        int index = -1;
        ClsmLocationPertanyaan object = (ClsmLocationPertanyaan) item;
        try {
            index = helper.getmLocationDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        ClsmLocationPertanyaan object = (ClsmLocationPertanyaan) item;
        try {
            index = helper.getmLocationDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsmLocationPertanyaan item = null;
        try {
            item = helper.getmLocationDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsmLocationPertanyaan> items = null;
        try {
            items = helper.getmLocationDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
