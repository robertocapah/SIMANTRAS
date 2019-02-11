package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.template.Common.tAkuisisiDetail;
import shp.template.Common.tAkuisisiHeader;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class tAkuisisiDetailRepo implements crud{
    DatabaseHelper helper;

    public tAkuisisiDetailRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        tAkuisisiDetail object = (tAkuisisiDetail) item;
        try {
            index = helper.getAkuisisiDetailDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        tAkuisisiDetail object = (tAkuisisiDetail) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getAkuisisiDetailDao().createOrUpdate(object);
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
        tAkuisisiDetail object = (tAkuisisiDetail) item;
        try {
            index = helper.getAkuisisiDetailDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        tAkuisisiDetail item = null;
        try{
            item = helper.getAkuisisiDetailDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<tAkuisisiDetail> items = null;
        try{
            items = helper.getAkuisisiDetailDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public List<tAkuisisiDetail> findByHeaderId(String intHeaderId) throws SQLException {
        tAkuisisiDetail item = new tAkuisisiDetail();
        List<tAkuisisiDetail> listData = new ArrayList<>();
        QueryBuilder<tAkuisisiDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intHeaderId, intHeaderId);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public tAkuisisiDetail findByDetailId(String txtDetailId) throws SQLException {
        tAkuisisiDetail item = new tAkuisisiDetail();
        QueryBuilder<tAkuisisiDetail, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getAkuisisiDetailDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtDetailId, txtDetailId);
            item = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public List<tAkuisisiDetail> getPushAllData(List<tAkuisisiHeader> headerList){
        List<String> headerIdList = new ArrayList<>();
        List<tAkuisisiDetail> detailList = new ArrayList<>();
        tAkuisisiDetail item = new tAkuisisiDetail();
        QueryBuilder<tAkuisisiDetail, Integer> queryBuilder = null;
        try {
            if (headerIdList!=null){
                if (headerList.size()>0){
                    for (tAkuisisiHeader header : headerList){
                        headerIdList.add(header.getTxtHeaderId());
                    }
                    queryBuilder = helper.getAkuisisiDetailDao().queryBuilder();
                    queryBuilder.where().in(item.Property_intHeaderId, headerIdList);
                    detailList = queryBuilder.query();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailList;
    }
}
