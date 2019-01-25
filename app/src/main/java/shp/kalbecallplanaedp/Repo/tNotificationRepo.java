package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.tNotification;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

public class tNotificationRepo implements crud {
    DatabaseHelper helper;

    public tNotificationRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tNotification object = (tNotification) item;
        try {
            index = helper.gettNotificationDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tNotification object = (tNotification) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettNotificationDao().createOrUpdate(object);
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
        tNotification object = (tNotification) item;
        try {
            index = helper.gettNotificationDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tNotification item = null;
        try{
            item = helper.gettNotificationDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tNotification> items = null;
        try{
            items = helper.gettNotificationDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tNotification findById(String txtId) throws SQLException {
        tNotification item = new tNotification();
        QueryBuilder<tNotification, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettNotificationDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intHeaderAkuisisiId, txtId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    public List<tNotification> findByOutletId(String intOutletId, int intActivityId) throws SQLException {
        List<tNotification> ListData = new ArrayList<>();
        tNotification item = new tNotification();
        QueryBuilder<tNotification, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettNotificationDao().queryBuilder();
            if (intActivityId==new clsHardCode().VisitDokter){
                queryBuilder.where().eq(item.Property_intDokterId, intOutletId);
            }else if (intActivityId==new clsHardCode().VisitApotek){
                queryBuilder.where().eq(item.Property_intApotekId, intOutletId);
            }
            ListData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListData;
    }

    public List<tNotification> findOutletId() throws SQLException {
        List<tNotification> ListData1 = new ArrayList<>();
        List<tNotification> ListData2 = new ArrayList<>();
        List<tNotification> newListData = null;
        tNotification item = new tNotification();
        QueryBuilder<tNotification, Integer> queryBuilder1 = null;
        QueryBuilder<tNotification, Integer> queryBuilder2 = null;
        try {
            queryBuilder1 = helper.gettNotificationDao().queryBuilder();
            queryBuilder1.where().eq(item.Proporty_intActivityId, new clsHardCode().VisitDokter);
            queryBuilder1.groupBy(item.Property_intDokterId);
            ListData1 = queryBuilder1.query();

            queryBuilder2 = helper.gettNotificationDao().queryBuilder();
            queryBuilder2.where().eq(item.Proporty_intActivityId, new clsHardCode().VisitApotek);
            queryBuilder2.groupBy(item.Property_intApotekId);
            ListData2 = queryBuilder2.query();

            newListData = new ArrayList<>(ListData1);
            newListData.addAll(ListData2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newListData;
    }
}
