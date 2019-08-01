package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepomPertanyaan implements CRUD {
    DatabaseHelper helper;
    public RepomPertanyaan(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsmPertanyaan object = (ClsmPertanyaan) item;
        try {
            index = helper.getmPertanyaanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmPertanyaan object = (ClsmPertanyaan) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmPertanyaanDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        int index = -1;
        ClsmPertanyaan object = (ClsmPertanyaan) item;
        try {
            index = helper.getmPertanyaanDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        ClsmPertanyaan object = (ClsmPertanyaan) item;
        try {
            index = helper.getmPertanyaanDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        ClsmPertanyaan item = null;
        try {
            item = helper.getmPertanyaanDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<ClsmPertanyaan> findAll() throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            items = helper.getmPertanyaanDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmPertanyaan> findQuestion(int intValidateId) throws SQLException {
        List<ClsmPertanyaan> items = null;
        if (intValidateId == ClsHardCode.BASIC){
            try {
                QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(intValidateId == ClsHardCode.HEADER){
            try {
                QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.HEADER).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(intValidateId == ClsHardCode.BODY){
            try {
                QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BODY).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }/*else if(intValidateId == ClsHardCode.FOOTER){
            try {
                QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.FOOTER).query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        return items;
    }
    public List<ClsmPertanyaan> findQuestionGeneralInfoAll() throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).query();
        } catch (Exception ex) {

        }
        return items;
    }
    public List<ClsmPertanyaan> findQuestionGeneralInfo(String txtInfoCode) throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            if (txtInfoCode.equals(ClsHardCode.TXT_DEFAULT)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_PLAN_DELIVERY_DATE).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_EXPEDITION_NAME).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_FIND_DETAIL_HCD).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_ITEM_TYPE).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_SPM_NO).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_VEHICLE_TYPE).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.DRIVER_NAME).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.KERANI_NAME).and().ne(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.VEHICLE_NUMBER).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_CREATION_DATE)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_CREATION_DATE).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_EXPEDITION_NAME)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_EXPEDITION_NAME).query();
            }else if(txtInfoCode.equals(ClsHardCode.TXT_FIND_DETAIL)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_FIND_DETAIL).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_FIND_DETAIL_HCD)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_FIND_DETAIL_HCD).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_ITEM_TYPE)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_ITEM_TYPE).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_SPM_NO)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_SPM_NO).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_VEHICLE_TYPE)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_VEHICLE_TYPE).query();

            }else if(txtInfoCode.equals(ClsHardCode.TXT_PLAN_DELIVERY_DATE)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.TXT_PLAN_DELIVERY_DATE).query();

            }else if(txtInfoCode.equals(ClsHardCode.DRIVER_NAME)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.DRIVER_NAME).query();

            }else if(txtInfoCode.equals(ClsHardCode.KERANI_NAME)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.KERANI_NAME).query();

            }else if(txtInfoCode.equals(ClsHardCode.VEHICLE_NUMBER)){

                items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().eq(ClsmPertanyaan.TXTMAPCOL,ClsHardCode.VEHICLE_NUMBER).query();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    /*public List<ClsmPertanyaan> findQuestionOptional(int intValidateId) throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            items = builder.where().ne(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.HEADER).and().eq(ClsmPertanyaan.INTVALIDATEID,ClsHardCode.OPTIONAL).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmPertanyaan> findQuestionBodyAndFooter(int intValidateId) throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BODY).and().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.FOOTER).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmPertanyaan> findGenerateInfo() throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,ClsHardCode.BASIC).and().ne(ClsmPertanyaan.TXTJENISPERTANYAAN,"7").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmPertanyaan> findGenerateNestedInfo() throws SQLException {
        List<ClsmPertanyaan> items = null;
        try {
            QueryBuilder<ClsmPertanyaan, Integer> builder = helper.getmPertanyaanDao().queryBuilder();
            items = builder.where().eq(ClsmPertanyaan.TXTINTLOCATIONID,"1").and().eq(ClsmPertanyaan.TXTJENISPERTANYAAN,"7").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }*/
    public boolean deleteAllData(){
        return helper.clearDataNotif();
    }
    public boolean deleteDataPertanyaanJawaban(){
        return helper.clearDataNotif();
    }
}
