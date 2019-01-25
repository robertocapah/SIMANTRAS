package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Common.tProgramVisit;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class tProgramVisitRepo implements crud {
    DatabaseHelper helper;

    public tProgramVisitRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tProgramVisit object = (tProgramVisit) item;
        try {
            index = helper.gettProgramVisitDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tProgramVisit object = (tProgramVisit) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettProgramVisitDao().createOrUpdate(object);
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
        tProgramVisit object = (tProgramVisit) item;
        try {
            index = helper.gettProgramVisitDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tProgramVisit item = null;
        try{
            item = helper.gettProgramVisitDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tProgramVisit> items = null;
        try{
            items = helper.gettProgramVisitDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tProgramVisit findBytxtId(String txtId) throws SQLException {
        tProgramVisit item = new tProgramVisit();
        QueryBuilder<tProgramVisit, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettProgramVisitDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtProgramVisitId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public boolean isExistProgramVisit(Context context) throws ParseException {
        boolean valid = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        List<mUserLogin> data = new ArrayList<>();
        List<tProgramVisit> dataVisit = new ArrayList<>();
        try {
            data = (List<mUserLogin>) loginRepo.findAll();
            dataVisit = (List<tProgramVisit>) new tProgramVisitRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (tProgramVisit dtVisit : dataVisit){
            for (mUserLogin dt : data){
//                return dateFormat.format(sdf.parse(dtVisit.getDtStart())).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn()))) * dateFormat.format(sdf.parse(dt.getDtLogIn())).compareTo(dateFormat.format(sdf.parse(dtVisit.getDtEnd()))) >= 0;
                if (dateFormat.format(sdf.parse(dtVisit.getDtStart())).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn()))) * dateFormat.format(sdf.parse(dt.getDtLogIn())).compareTo(dateFormat.format(sdf.parse(dtVisit.getDtEnd()))) >= 0){
                    valid = true;
                    break;
                }
            }
        }

        return valid;
    }

    public List<tProgramVisit> getAllPushData () {
        tProgramVisit item = new tProgramVisit();
        List<tProgramVisit> listData = new ArrayList<>();
        QueryBuilder<tProgramVisit, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettProgramVisitDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tProgramVisit getProgramVisitActive(Context context) throws ParseException {
        boolean valid = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        List<mUserLogin> data = new ArrayList<>();
        tProgramVisit item = new tProgramVisit();
        List<tProgramVisit> dataVisit = new ArrayList<>();
        try {
            data = (List<mUserLogin>) loginRepo.findAll();
            dataVisit = (List<tProgramVisit>) new tProgramVisitRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (tProgramVisit dtVisit : dataVisit){
            for (mUserLogin dt : data){
//                return dateFormat.format(sdf.parse(dtVisit.getDtStart())).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn()))) * dateFormat.format(sdf.parse(dt.getDtLogIn())).compareTo(dateFormat.format(sdf.parse(dtVisit.getDtEnd()))) >= 0;
                if (dateFormat.format(sdf.parse(dtVisit.getDtStart())).compareTo(dateFormat.format(sdf.parse(dt.getDtLogIn()))) * dateFormat.format(sdf.parse(dt.getDtLogIn())).compareTo(dateFormat.format(sdf.parse(dtVisit.getDtEnd()))) >= 0){
                    item = dtVisit;
                    break;
                }
            }
        }

        return item;
    }
}
