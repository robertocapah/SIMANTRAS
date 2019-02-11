package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import shp.template.Common.mTypeSubSubActivity;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

public class mTypeSubSubActivityRepo implements crud {
    DatabaseHelper helper;

    public mTypeSubSubActivityRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mTypeSubSubActivity object = (mTypeSubSubActivity) item;
        try {
            index = helper.getmTypeSubSubActivityDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mTypeSubSubActivity object = (mTypeSubSubActivity) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmTypeSubSubActivityDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
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
        int index = -1;
        mTypeSubSubActivity object = (mTypeSubSubActivity) item;
        try {
            index = helper.getmTypeSubSubActivityDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mTypeSubSubActivity item = null;
        try{
            item = helper.getmTypeSubSubActivityDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mTypeSubSubActivity> items = null;
        try{
            items = helper.getmTypeSubSubActivityDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
