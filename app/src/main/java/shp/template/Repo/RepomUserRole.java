package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import shp.template.Common.ClsmUserRole;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class RepomUserRole implements CRUD {
    DatabaseHelper helper;

    public RepomUserRole(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmUserRole object = (ClsmUserRole) item;
        try {
            index = helper.getmUserRolesDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmUserRole object = (ClsmUserRole) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmUserRolesDao().createOrUpdate(object);
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
        return 0;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsmUserRole item = null;
        try{
            item = helper.getmUserRolesDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsmUserRole> items = null;
        try{
            items = helper.getmUserRolesDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
