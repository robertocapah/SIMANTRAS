package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class mUserLoginRepo implements crud {
    DatabaseHelper helper;

    public mUserLoginRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mUserLogin object = (mUserLogin) item;
        try {
            index = helper.getmUserLoginsDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mUserLogin object = (mUserLogin) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmUserLoginsDao().createOrUpdate(object);
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
        mUserLogin item = null;
        try{
            item = helper.getmUserLoginsDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mUserLogin> items = null;
        try{
            items = helper.getmUserLoginsDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public int getContactCount(Context context){
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        int count = 0;
        List<mUserLogin> data = null;
        try {
            data = (List<mUserLogin>) loginRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data!=null){
            count = data.size();
        }
        return count;
    }

    public boolean CheckLoginNow(Context context) throws ParseException {
        boolean valid = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        List<mUserLogin> data = null;
        try {
            data = (List<mUserLogin>) loginRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (mUserLogin dt : data){
            if (dateFormat.format(cal.getTime()).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn())))==0){
                valid = true;
                break;
            }
        }
        return valid;
    }

    public mUserLogin findByTxtId(String txtId) throws SQLException {
        mUserLogin item = new mUserLogin();
        QueryBuilder<mUserLogin, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getmUserLoginsDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtGuID, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
