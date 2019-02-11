package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;
import shp.template.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

public class tRealisasiVisitPlanRepo implements crud { DatabaseHelper helper;

    public tRealisasiVisitPlanRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            index = helper.gettRealisasiVisitPlanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettRealisasiVisitPlanDao().createOrUpdate(object);
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
        tRealisasiVisitPlan object = (tRealisasiVisitPlan) item;
        try {
            index = helper.gettRealisasiVisitPlanDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tRealisasiVisitPlan item = null;
        try{
            item = helper.gettRealisasiVisitPlanDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tRealisasiVisitPlan> items = null;
        try{
            items = helper.gettRealisasiVisitPlanDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tRealisasiVisitPlan findBytxtId(String txtId) throws SQLException {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtRealisasiVisitId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public tRealisasiVisitPlan findBytxtPlanId(String txtVisitId) throws SQLException {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtProgramVisitSubActivityId, txtVisitId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public tRealisasiVisitPlan findBytxtDokterId(String txtDokterId) throws SQLException {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtDokterId, txtDokterId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public tRealisasiVisitPlan findBytxtApotekId(String txtApotekId) throws SQLException {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtApotekId, txtApotekId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tRealisasiVisitPlan> getAllPushData () {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        List<tRealisasiVisitPlan> listData = new ArrayList<>();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tRealisasiVisitPlan> getAllRealisasi () {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        List<tRealisasiVisitPlan> listData = new ArrayList<>();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intStatusRealisasi, new clsHardCode().Realisasi);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tRealisasiVisitPlan> getAllPlan () {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
        List<tRealisasiVisitPlan> listData = new ArrayList<>();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intStatusRealisasi, new clsHardCode().VisitPlan);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tRealisasiVisitPlan getDataCheckinActive () {
        tRealisasiVisitPlan item = new tRealisasiVisitPlan();
//        List<tRealisasiVisitPlan> listData = new ArrayList<>();
        QueryBuilder<tRealisasiVisitPlan, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettRealisasiVisitPlanDao().queryBuilder();
//            Where<tRealisasiVisitPlan, Integer> where = queryBuilder.where();
//                where.eq(item.Property_intFlagPush, new clsHardCode().Draft).and().eq(item.Property_intStatusRealisasi, new clsHardCode().CheckIn);
//            where.and();
//                where.eq(item.Property_intFlagPush, new clsHardCode().Draft).and().eq(item.Property_intStatusRealisasi, new clsHardCode().Realisasi);
//            PreparedQuery<tRealisasiVisitPlan> preparedQuery = queryBuilder.prepare();
            queryBuilder.where().isNotNull(item.Property_dtCheckIn).and().isNull(item.Property_dtCheckOut);
            item = queryBuilder.queryForFirst();
//            listData = helper.gettRealisasiVisitPlanDao().query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
