package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.template.Common.mSubSubActivity;
import shp.template.Common.tAkuisisiHeader;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;
import shp.template.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class tAkuisisiHeaderRepo implements crud {
    DatabaseHelper helper;

    public tAkuisisiHeaderRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tAkuisisiHeader object = (tAkuisisiHeader) item;
        try {
            index = helper.getAkuisisiHeaderDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tAkuisisiHeader object = (tAkuisisiHeader) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getAkuisisiHeaderDao().createOrUpdate(object);
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
        tAkuisisiHeader object = (tAkuisisiHeader) item;
        try {
            index = helper.getAkuisisiHeaderDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tAkuisisiHeader item = null;
        try{
            item = helper.getAkuisisiHeaderDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tAkuisisiHeader> items = null;
        try{
            items = helper.getAkuisisiHeaderDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tAkuisisiHeader findBySubSubId(int intSubSubId, int intFlag) throws SQLException {
        tAkuisisiHeader item = new tAkuisisiHeader();
        tAkuisisiHeader listData = new tAkuisisiHeader();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intSubSubActivityId, intSubSubId).and().eq(item.Property_intFlagPush, intFlag);
            listData = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tAkuisisiHeader findBySubSubIdAndDokterId(int intSubSubId, String intDokterId, int intFlag) throws SQLException {
        tAkuisisiHeader item = new tAkuisisiHeader();
        tAkuisisiHeader listData = new tAkuisisiHeader();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intSubSubActivityId, intSubSubId).and().eq(item.Property_intFlagSow, intFlag).and().eq(item.Property_intDokterId, intDokterId);
            listData = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tAkuisisiHeader findBySubSubIdAndApotekId(int intSubSubId, String intApotekId, int intFlag) throws SQLException {
        tAkuisisiHeader item = new tAkuisisiHeader();
        tAkuisisiHeader listData = new tAkuisisiHeader();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intSubSubActivityId, intSubSubId).and().eq(item.Property_intFlagSow, intFlag).and().eq(item.Property_intApotekID, intApotekId);
            listData = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tAkuisisiHeader findByHeaderId(String intHeaderId) throws SQLException {
        tAkuisisiHeader item = new tAkuisisiHeader();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tAkuisisiHeader> getAllPushData () {
        tAkuisisiHeader item = new tAkuisisiHeader();
       List<tAkuisisiHeader> listData = new ArrayList<>();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tAkuisisiHeader> findByRealisasi (String txtRealisasiId) {
        tAkuisisiHeader item = new tAkuisisiHeader();
        List<tAkuisisiHeader> listData = new ArrayList<>();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtRealisasiVisitId, txtRealisasiId);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<Integer> getIntSubSubActivityId (int intActivity, String intOutlet) {
        tAkuisisiHeader item = new tAkuisisiHeader();
        List<Integer> listId = new ArrayList<>();
        List<tAkuisisiHeader> listData = new ArrayList<>();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            if (intActivity==new clsHardCode().VisitDokter){
                queryBuilder.where().eq(item.Property_intFlagSow, new clsHardCode().Save).and().eq(item.Property_intDokterId, intOutlet);
            }else if (intActivity==new clsHardCode().VisitApotek){
                queryBuilder.where().eq(item.Property_intFlagSow, new clsHardCode().Save).and().eq(item.Property_intApotekID, intOutlet);
            }
            listData = queryBuilder.query();
            for (tAkuisisiHeader data : listData){
                listId.add(data.getIntSubSubActivityId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listId;
    }

    public List<mSubSubActivity> getIntSubDetailActivityId (String txtRealisasiId) {
        tAkuisisiHeader item = new tAkuisisiHeader();
        mSubSubActivity subSubActivity = new mSubSubActivity();
        List<Integer> listId = new ArrayList<>();
        List<mSubSubActivity> listData = new ArrayList<>();
        List<tAkuisisiHeader> listAkuisisi = new ArrayList<>();
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder = null;
        QueryBuilder<mSubSubActivity, Integer> queryBuilderData = null;
        try {
            queryBuilder = helper.getAkuisisiHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtRealisasiVisitId, txtRealisasiId);
            listAkuisisi = queryBuilder.groupBy(item.Property_intSubSubActivityId).query();
            for (tAkuisisiHeader data : listAkuisisi){
                listId.add(data.getIntSubSubActivityId());
            }

            queryBuilderData = helper.getmSubSubActivityDao().queryBuilder();
            queryBuilderData.where().in(subSubActivity.Property_intSubSubActivityid, listId);
            listData = queryBuilderData.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }
}
