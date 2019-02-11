package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import shp.template.Common.ClstLogError;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;
import shp.template.Data.ClsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/23/2018.
 */

public class RepotLogError implements CRUD {
    DatabaseHelper helper;

    public RepotLogError(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClstLogError object = (ClstLogError) item;
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
        ClstLogError object = (ClstLogError) item;
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
        ClstLogError object = (ClstLogError) item;
        try {
            index = helper.gettLogErrorDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClstLogError item = null;
        try{
            item = helper.gettLogErrorDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClstLogError> items = null;
        try{
            items = helper.gettLogErrorDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public ClstLogError findBytxtId(String txtId) throws SQLException {
        ClstLogError item = new ClstLogError();
        QueryBuilder<ClstLogError, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettLogErrorDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtGuiId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<ClstLogError> getAllPushData () {
        ClstLogError item = new ClstLogError();
        List<ClstLogError> listData = new ArrayList<>();
        QueryBuilder<ClstLogError, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettLogErrorDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new ClsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
