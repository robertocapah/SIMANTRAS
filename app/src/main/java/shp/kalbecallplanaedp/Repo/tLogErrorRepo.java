package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.tLogError;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/23/2018.
 */

public class tLogErrorRepo implements crud {
    DatabaseHelper helper;

    public tLogErrorRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tLogError object = (tLogError) item;
        try {
            index = helper.gettLogErrorDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tLogError object = (tLogError) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettLogErrorDao().createOrUpdate(object);
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
        tLogError object = (tLogError) item;
        try {
            index = helper.gettLogErrorDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tLogError item = null;
        try{
            item = helper.gettLogErrorDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tLogError> items = null;
        try{
            items = helper.gettLogErrorDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tLogError findBytxtId(String txtId) throws SQLException {
        tLogError item = new tLogError();
        QueryBuilder<tLogError, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettLogErrorDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtGuiId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tLogError> getAllPushData () {
        tLogError item = new tLogError();
        List<tLogError> listData = new ArrayList<>();
        QueryBuilder<tLogError, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettLogErrorDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
