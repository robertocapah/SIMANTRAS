package shp.template.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Database.DatabaseHelper;
import shp.template.Database.DatabaseManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class RepomUserLogin implements CRUD {
    DatabaseHelper helper;

    public RepomUserLogin(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmUserLogin object = (ClsmUserLogin) item;
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
        ClsmUserLogin object = (ClsmUserLogin) item;
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
        ClsmUserLogin item = null;
        try{
            item = helper.getmUserLoginsDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<ClsmUserLogin> items = null;
        try{
            items = helper.getmUserLoginsDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public int getContactCount(Context context){
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        int count = 0;
        List<ClsmUserLogin> data = null;
        try {
            data = (List<ClsmUserLogin>) loginRepo.findAll();
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
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        List<ClsmUserLogin> data = null;
        try {
            data = (List<ClsmUserLogin>) loginRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (ClsmUserLogin dt : data){
            if (dateFormat.format(cal.getTime()).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn())))==0){
                valid = true;
                break;
            }
        }
        return valid;
    }

    public ClsmUserLogin findByTxtId(String txtId) throws SQLException {
        ClsmUserLogin item = new ClsmUserLogin();
        QueryBuilder<ClsmUserLogin, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getmUserLoginsDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtGuID, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    public ClsmUserLogin getUserLogin(Context context)throws SQLException{
        List <ClsmUserLogin> dtList = new ArrayList<>();
        RepomUserLogin dtRepo= new RepomUserLogin(context);
        try {
            dtList = (List<ClsmUserLogin>) dtRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dtList.size()>0){
            return dtList.get(0);
        }else {
            return null;
        }
    }
}
