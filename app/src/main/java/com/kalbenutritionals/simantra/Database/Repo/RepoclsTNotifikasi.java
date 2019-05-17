package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kalbenutritionals.simantra.Database.Common.ClsTNotifikasi;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepoclsTNotifikasi implements CRUD {
    private DatabaseHelper helper;

    public RepoclsTNotifikasi(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsTNotifikasi object = (ClsTNotifikasi) item;
        try {
            index = helper.gettNotifikasisDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsTNotifikasi object = (ClsTNotifikasi) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.gettNotifikasisDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        }catch (SQLException e){
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
        ClsTNotifikasi item = null;
        try{
            item = helper.gettNotifikasisDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<ClsTNotifikasi> findAll() throws SQLException {
        List<ClsTNotifikasi> items = null;
        try{
            items = helper.gettNotifikasisDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
