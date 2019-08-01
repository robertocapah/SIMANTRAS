package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kalbenutritionals.simantra.Database.Common.ClsTConfigHelper;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Roberto on 7/11/2019
 */
public class RepoClsTConfigHelper implements CRUD {
    DatabaseHelper helper;
    public RepoClsTConfigHelper(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        return 0;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsTConfigHelper object = (ClsTConfigHelper) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getClsTCommonRejectionDao().createOrUpdate(object);
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
        return null;
    }

    @Override
    public List<ClsTConfigHelper> findAll() throws SQLException {
        List<ClsTConfigHelper> items = null;
        try{
            items = helper.getClsTCommonRejectionDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
