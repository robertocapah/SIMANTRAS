package shp.template.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import shp.template.Database.Common.ClsmMenuData;
import shp.template.Database.DatabaseHelper;
import shp.template.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Rian Andrivani on 11/22/2017.
 */

public class RepomMenu implements CRUD {
    private DatabaseHelper helper;
    public RepomMenu(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) {
        int index = -1;
        ClsmMenuData object = (ClsmMenuData) item;
        try {
            index = helper.getMenuDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) {
        int index = -1;
        ClsmMenuData object = (ClsmMenuData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getMenuDao().createOrUpdate(object);
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
        ClsmMenuData object = (ClsmMenuData) item;
        try {
            index = helper.getMenuDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;
        ClsmMenuData object = (ClsmMenuData) item;
        try {
            index = helper.getMenuDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsmMenuData item = null;
        try{
            item = helper.getMenuDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsmMenuData> items = null;
        try{
            items = helper.getMenuDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
