package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.mSubSubActivity;
import shp.kalbecallplanaedp.Common.tMaintenanceDetail;
import shp.kalbecallplanaedp.Common.tMaintenanceHeader;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/24/2018.
 */

public class tMaintenanceHeaderRepo implements crud {
    DatabaseHelper helper;

    public tMaintenanceHeaderRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            index = helper.gettMaintenanceHeaderDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettMaintenanceHeaderDao().createOrUpdate(object);
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
        tMaintenanceHeader object = (tMaintenanceHeader) item;
        try {
            index = helper.gettMaintenanceHeaderDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tMaintenanceHeader item = null;
        try{
            item = helper.gettMaintenanceHeaderDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tMaintenanceHeader> items = null;
        try{
            items = helper.gettMaintenanceHeaderDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tMaintenanceHeader findByHeaderId(String intHeaderId) throws SQLException {
        tMaintenanceHeader item = new tMaintenanceHeader();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public tMaintenanceHeader findByRealisasiId(String intRealisasiId) throws SQLException {
        tMaintenanceHeader item = new tMaintenanceHeader();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtRealisasiVisitId, intRealisasiId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<mSubSubActivity> getIntSubDetailActivityId(String intRealisasiId) throws SQLException {
        tMaintenanceHeader item = new tMaintenanceHeader();
        tMaintenanceDetail detail = new tMaintenanceDetail();
        mSubSubActivity subSubActivity = new mSubSubActivity();
        List<Integer> listId = new ArrayList<>();
        List<mSubSubActivity> listData = new ArrayList<>();
        List<tMaintenanceDetail> listDetail = new ArrayList<>();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilderHeader = null;
        QueryBuilder<mSubSubActivity, Integer> queryBuilderData = null;
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilderHeader = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilderHeader.where().eq(item.Property_txtRealisasiVisitId, intRealisasiId);
            item = queryBuilderHeader.queryForFirst();

            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(detail.Property_txtHeaderId, item.getTxtHeaderId());
            listDetail = queryBuilder.groupBy(detail.Property_intSubDetailActivityId).query();
            for (tMaintenanceDetail data : listDetail){
                listId.add(data.getIntSubDetailActivityId());
            }
            queryBuilderData = helper.getmSubSubActivityDao().queryBuilder();
            queryBuilderData.where().in(subSubActivity.Property_intSubSubActivityid, listId);
            listData = queryBuilderData.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tMaintenanceHeader findByOutletId(String intOutletId, int intActivityId) throws SQLException {
        tMaintenanceHeader item = new tMaintenanceHeader();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            if (intActivityId==new clsHardCode().VisitDokter){
                queryBuilder.where().eq(item.Property_intDokterId, intOutletId);
            }else if (intActivityId==new clsHardCode().VisitApotek){
                queryBuilder.where().eq(item.Property_intApotekID, intOutletId);
            }
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<String> findIdByOutletId(String intOutletId, int intActivityId) throws SQLException {
        List<String> listId = new ArrayList<>();
        List<tMaintenanceHeader> listData = new ArrayList<>();
        tMaintenanceHeader item = new tMaintenanceHeader();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            if (intActivityId==new clsHardCode().VisitDokter){
                queryBuilder.where().eq(item.Property_intDokterId, intOutletId);
            }else if (intActivityId==new clsHardCode().VisitApotek){
                queryBuilder.where().eq(item.Property_intApotekID, intOutletId);
            }
            listData = queryBuilder.query();
            for (tMaintenanceHeader data : listData){
                listId.add(data.getTxtHeaderId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listId;
    }

    public List<tMaintenanceHeader> getAllPushData () {
        tMaintenanceHeader item = new tMaintenanceHeader();
        List<tMaintenanceHeader> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tMaintenanceHeader getDraft() {
        tMaintenanceHeader item = new tMaintenanceHeader();
//        List<tMaintenanceHeader> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceHeaderDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Draft);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
