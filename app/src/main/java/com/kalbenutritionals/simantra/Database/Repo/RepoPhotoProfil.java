package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import com.kalbenutritionals.simantra.Database.Common.ClsPhotoProfile;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepoPhotoProfil implements CRUD {
    private DatabaseHelper helper;
    public RepoPhotoProfil(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) {
        int index = -1;
        ClsPhotoProfile object = (ClsPhotoProfile) item;
        try {
            index = helper.getProfileDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) {
        int index = -1;
        ClsPhotoProfile object = (ClsPhotoProfile) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getProfileDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) {
        int index = -1;
        ClsPhotoProfile object = (ClsPhotoProfile) item;
        try {
            index = helper.getProfileDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;
        ClsPhotoProfile object = (ClsPhotoProfile) item;
        try {
            index = helper.getProfileDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsPhotoProfile item = null;
        try{
            item = helper.getProfileDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsPhotoProfile> items = null;
        try{
            items = helper.getProfileDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
