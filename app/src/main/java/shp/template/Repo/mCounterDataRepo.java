package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import shp.template.Common.mCounterData;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class mCounterDataRepo implements crud{
    private DatabaseHelper helper;

    public mCounterDataRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mCounterData object = (mCounterData) item;
        try {
            index = helper.getmCounterDataDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mCounterData object = (mCounterData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getmCounterDataDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        } catch (SQLException e) {
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
    public List<?> findAll() throws SQLException {
        return null;
    }
}
