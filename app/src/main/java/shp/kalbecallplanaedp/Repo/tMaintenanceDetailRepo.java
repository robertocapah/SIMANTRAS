package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
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

public class tMaintenanceDetailRepo implements crud {
    DatabaseHelper helper;

    public tMaintenanceDetailRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tMaintenanceDetail object = (tMaintenanceDetail) item;
        try {
            index = helper.gettMaintenanceDetailDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tMaintenanceDetail object = (tMaintenanceDetail) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettMaintenanceDetailDao().createOrUpdate(object);
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
        tMaintenanceDetail object = (tMaintenanceDetail) item;
        try {
            index = helper.gettMaintenanceDetailDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tMaintenanceDetail item = null;
        try{
            item = helper.gettMaintenanceDetailDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tMaintenanceDetail> items = null;
        try{
            items = helper.gettMaintenanceDetailDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public tMaintenanceDetail findByDetailId(String txtDetailId) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtDetailId, txtDetailId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tMaintenanceDetail> findByHeaderId(String intHeaderId) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        List<tMaintenanceDetail> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tMaintenanceDetail> findByHeaderPushId(String intHeaderId) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        List<tMaintenanceDetail> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId).and().eq(item.Property_intFlagPush, new clsHardCode().Save);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tMaintenanceDetail> findByHeaderDraftId(String intHeaderId, int intSubDetailActivityId) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        List<tMaintenanceDetail> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId).and().eq(item.Property_intFlagPush, new clsHardCode().Draft).and().eq(item.Property_intSubDetailActivityId, intSubDetailActivityId);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tMaintenanceDetail> findByHeaderIdandSubsubId(String intHeaderId, int intSubSubActivity) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        List<tMaintenanceDetail> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtHeaderId, intHeaderId).and().eq(item.Property_intSubDetailActivityId, intSubSubActivity);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<tMaintenanceDetail> findByListHeaderIdandSubsubId(List<String> intHeaderId, int intSubSubActivity) throws SQLException {
        tMaintenanceDetail item = new tMaintenanceDetail();
        List<tMaintenanceDetail> listData = new ArrayList<>();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
            queryBuilder.where().in(item.Property_txtHeaderId, intHeaderId).and().eq(item.Property_intSubDetailActivityId, intSubSubActivity);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }


    public List<tMaintenanceDetail> getPushAllData(List<tMaintenanceHeader> headerList){
        List<String> headerIdList = new ArrayList<>();
        List<tMaintenanceDetail> detailList = new ArrayList<>();
        tMaintenanceDetail item = new tMaintenanceDetail();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            if (headerIdList!=null){
                if (headerList.size()>0){
                    for (tMaintenanceHeader header : headerList){
                        headerIdList.add(header.getTxtHeaderId());
                    }
                    queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
                    queryBuilder.where().in(item.Property_txtHeaderId, headerIdList);
                    detailList = queryBuilder.query();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailList;
    }

    public List<tMaintenanceDetail> getPushAllDataDetail(List<tMaintenanceHeader> headerList){
        List<String> headerIdList = new ArrayList<>();
        List<tMaintenanceDetail> detailList = new ArrayList<>();
        tMaintenanceDetail item = new tMaintenanceDetail();
        QueryBuilder<tMaintenanceDetail, Integer> queryBuilder = null;
        try {
            if (headerIdList!=null){
                if (headerList.size()>0){
                    for (tMaintenanceHeader header : headerList){
                        headerIdList.add(header.getTxtHeaderId());
                    }
                    queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
                    queryBuilder.where().in(item.Property_txtHeaderId, headerIdList).and().eq(item.Property_intFlagPush, new clsHardCode().Save);
                    detailList = queryBuilder.query();
                }
            }
//            queryBuilder = helper.gettMaintenanceDetailDao().queryBuilder();
//            queryBuilder.where().eq(item.Property_intFlagPush, new clsHardCode().Save);
//            detailList = queryBuilder.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailList;
    }
}
