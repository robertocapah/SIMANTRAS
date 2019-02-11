package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.template.Common.mApotek;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class mApotekRepo implements crud {
    DatabaseHelper helper;

    public mApotekRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mApotek object = (mApotek) item;
        try {
            index = helper.getmApotekDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mApotek object = (mApotek) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmApotekDao().createOrUpdate(object);
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
        mApotek object = (mApotek) item;
        try {
            index = helper.getmApotekDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mApotek item = null;
        try{
            item = helper.getmApotekDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mApotek> items = null;
        try{
            items = helper.getmApotekDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public mApotek findBytxtId(String txtId) throws SQLException {
        mApotek item = new mApotek();
        QueryBuilder<mApotek, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getmApotekDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtCode, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
