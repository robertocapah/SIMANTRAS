package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kalbenutritionals.simantra.Database.Common.ClsmJenisPertanyaan;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepomJenisPertanyaan implements CRUD{
    DatabaseHelper helper;
    public RepomJenisPertanyaan(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmJenisPertanyaan object = (ClsmJenisPertanyaan) item;
        try {
            index = helper.getmJenisPertanyaanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmJenisPertanyaan object = (ClsmJenisPertanyaan) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmJenisPertanyaanDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        int index = -1;
        ClsmJenisPertanyaan object = (ClsmJenisPertanyaan) item;
        try {
            index = helper.getmJenisPertanyaanDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        ClsmJenisPertanyaan object = (ClsmJenisPertanyaan) item;
        try {
            index = helper.getmJenisPertanyaanDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsmJenisPertanyaan item = null;
        try {
            item = helper.getmJenisPertanyaanDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsmJenisPertanyaan> items = null;
        try {
            items = helper.getmJenisPertanyaanDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
