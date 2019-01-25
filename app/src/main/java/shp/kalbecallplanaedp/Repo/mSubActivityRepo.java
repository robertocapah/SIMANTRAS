package shp.kalbecallplanaedp.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import shp.kalbecallplanaedp.Common.mSubActivity;
import shp.kalbecallplanaedp.Common.tAkuisisiHeader;
import shp.kalbecallplanaedp.Common.tInfoProgramHeader;
import shp.kalbecallplanaedp.Common.tMaintenanceHeader;
import shp.kalbecallplanaedp.Data.DatabaseHelper;
import shp.kalbecallplanaedp.Data.DatabaseManager;
import shp.kalbecallplanaedp.Data.clsHardCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/15/2018.
 */

public class mSubActivityRepo implements crud {
    DatabaseHelper helper;

    public mSubActivityRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mSubActivity object = (mSubActivity) item;
        try {
            index = helper.getmSubActivityDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mSubActivity object = (mSubActivity) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmSubActivityDao().createOrUpdate(object);
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
        mSubActivity object = (mSubActivity) item;
        try {
            index = helper.getmSubActivityDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mSubActivity item = null;
        try{
            item = helper.getmSubActivityDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mSubActivity> items = null;
        try{
            items = helper.getmSubActivityDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public List<mSubActivity> findByActivityId(int intActivityId) throws SQLException {
        mSubActivity item = new mSubActivity();
        List<mSubActivity> listData = new ArrayList<>();
        QueryBuilder<mSubActivity, Integer> queryBuilder = null;
        try {
            queryBuilder = helper.getmSubActivityDao().queryBuilder();
            queryBuilder.where().eq(item.Property_intActivityid, intActivityId);
            listData = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public List<mSubActivity> findSubActivityId(int intActivity, String txtRealisasiId) throws SQLException {
        List<tAkuisisiHeader> ListData1 = new ArrayList<>();
        List<tMaintenanceHeader> ListData2 = new ArrayList<>();
        List<tInfoProgramHeader> ListData3 = new ArrayList<>();
        List<mSubActivity> ListDataSubActivity = new ArrayList<>();
        List<Integer> listId = new ArrayList<>();
        mSubActivity item = new mSubActivity();
        tAkuisisiHeader akusisi = new tAkuisisiHeader();
        tMaintenanceHeader maintenance = new tMaintenanceHeader();
        tInfoProgramHeader info = new tInfoProgramHeader();
        QueryBuilder<mSubActivity, Integer> queryBuilder = null;
        QueryBuilder<tAkuisisiHeader, Integer> queryBuilder1 = null;
        QueryBuilder<tMaintenanceHeader, Integer> queryBuilder2 = null;
        QueryBuilder<tInfoProgramHeader, Integer> queryBuilder3 = null;
        try {
            if (intActivity == new clsHardCode().VisitDokter){
                queryBuilder1 = helper.getAkuisisiHeaderDao().queryBuilder();
                queryBuilder1.where().eq(akusisi.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData1 = queryBuilder1.query();
                if (ListData1!=null){
                    if (ListData1.size()>0){
                        listId.add(new clsHardCode().int_akuisisi_dokter);
                    }
                }
                queryBuilder2 = helper.gettMaintenanceHeaderDao().queryBuilder();
                queryBuilder2.where().eq(maintenance.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData2 = queryBuilder2.query();
                if (ListData2!=null){
                    if (ListData2.size()>0){
                        listId.add(new clsHardCode().int_maintenance_dokter);
                    }
                }
                queryBuilder3 = helper.gettInfoProgramHeaderDao().queryBuilder();
                queryBuilder3.where().eq(info.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData3 = queryBuilder3.query();
                if (ListData3!=null){
                    if (ListData3.size()>0){
                        listId.add(new clsHardCode().int_infoprogram_dokter);
                    }
                }
            }else {
                queryBuilder1 = helper.getAkuisisiHeaderDao().queryBuilder();
                queryBuilder1.where().eq(akusisi.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData1 = queryBuilder1.query();
                if (ListData1!=null){
                    if (ListData1.size()>0){
                        listId.add(new clsHardCode().int_akuisisi_apotek);
                    }
                }
                queryBuilder2 = helper.gettMaintenanceHeaderDao().queryBuilder();
                queryBuilder2.where().eq(maintenance.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData2 = queryBuilder2.query();
                if (ListData2!=null){
                    if (ListData2.size()>0){
                        listId.add(new clsHardCode().int_maintenance_apotek);
                    }
                }
                queryBuilder3 = helper.gettInfoProgramHeaderDao().queryBuilder();
                queryBuilder3.where().eq(info.Property_txtRealisasiVisitId, txtRealisasiId);
                ListData3 = queryBuilder3.query();
                if (ListData3!=null){
                    if (ListData3.size()>0){
                        listId.add(new clsHardCode().int_infoprogram_apotek);
                    }
                }
            }


            queryBuilder = helper.getmSubActivityDao().queryBuilder();
            queryBuilder.where().in(item.Property_intSubActivityid, listId);
            ListDataSubActivity = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListDataSubActivity;
    }
}
