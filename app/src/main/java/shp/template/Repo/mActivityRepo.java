package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RawRowMapper;
import shp.template.Common.mActivity;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

public class mActivityRepo implements crud {
    DatabaseHelper helper;

    public mActivityRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mActivity object = (mActivity) item;
        try {
            index = helper.getmActivityDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mActivity object = (mActivity) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmActivityDao().createOrUpdate(object);
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
        mActivity object = (mActivity) item;
        try {
            index = helper.getmActivityDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mActivity item = null;
        try{
            item = helper.getmActivityDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mActivity> items = null;
        try{
            items = helper.getmActivityDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
    public List<Integer> findOneColumnList() throws SQLException{
        List<Integer> items = new ArrayList<>();
        List<String> hehe = new ArrayList<>();
        try{
            hehe = helper.getmActivityDao().queryRaw("select intActivityId from mActivity", new RawRowMapper<String>() {
                @Override
                public String mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    return resultColumns[0];
                }
            }).getResults();
        }catch (SQLException e){
            e.printStackTrace();
        }
        List<Integer> haha = new ArrayList<>();
        for (String heha : hehe){
            haha.add(Integer.parseInt(heha));
        }
        return haha;
    }
}
