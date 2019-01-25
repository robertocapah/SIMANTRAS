package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class mDokterRepo implements crud {
    DatabaseHelper helper;

    public mDokterRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mDokter object = (mDokter) item;
        try {
            index = helper.getmDokterDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mDokter object = (mDokter) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmDokterDao().createOrUpdate(object);
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
        mDokter object = (mDokter) item;
        try {
            index = helper.getmDokterDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mDokter item = null;
        try{
            item = helper.getmDokterDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mDokter> items = null;
        try{
            items = helper.getmDokterDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public mDokter findBytxtId(String txtId) throws SQLException {
        mDokter item = new mDokter();
        QueryBuilder<mDokter, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getmDokterDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
