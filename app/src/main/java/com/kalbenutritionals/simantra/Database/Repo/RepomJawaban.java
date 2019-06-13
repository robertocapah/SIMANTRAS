package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class RepomJawaban implements CRUD {
    DatabaseHelper helper;
    public RepomJawaban(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        return 0;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsmJawaban object = (ClsmJawaban) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmJawabanDao().createOrUpdate(object);
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
        return null;
    }

    @Override
    public List<ClsmJawaban> findAll() throws SQLException {
        List<ClsmJawaban> items = null;
        try {
            items = helper.getmJawabanDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmJawaban> findByHeader(int intHeaderId) throws SQLException{
        List<ClsmJawaban> items = null;
        try {
            items = helper.getmJawabanDao().queryForEq(ClsmJawaban.ID_PERTANYAAN,intHeaderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public List<ClsmJawaban> findByHeadertoFindDetail(int intHeaderId, String detailCode) throws SQLException{
        List<ClsmJawaban> items = null;
        QueryBuilder<ClsmJawaban, Integer> builder = helper.getmJawabanDao().queryBuilder();
        try {
            items = builder.where().eq(ClsmJawaban.ID_PERTANYAAN, intHeaderId).and().eq(ClsmJawaban.TXTMAPCOL,detailCode).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
