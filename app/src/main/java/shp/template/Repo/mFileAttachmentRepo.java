package shp.template.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import shp.template.Common.mFileAttachment;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/15/2018.
 */

public class mFileAttachmentRepo implements crud {
    DatabaseHelper helper;

    public mFileAttachmentRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        mFileAttachment object = (mFileAttachment) item;
        try {
            index = helper.getmFileAttachmentDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        mFileAttachment object = (mFileAttachment) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.getmFileAttachmentDao().createOrUpdate(object);
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
        mFileAttachment object = (mFileAttachment) item;
        try {
            index = helper.getmFileAttachmentDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        mFileAttachment item = null;
        try{
            item = helper.getmFileAttachmentDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<mFileAttachment> items = null;
        try{
            items = helper.getmFileAttachmentDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

//    public mFileAttachment findByDetailId(String txtDetailId) throws SQLException {
//        mFileAttachment item = new mFileAttachment();
//        QueryBuilder<mFileAttachment, Integer> queryBuilder = null;
//        try {
//            queryBuilder = helper.getmFileAttachmentDao().queryBuilder();
//            queryBuilder.where().eq(item.Property_intFileAttachmentId, txtDetailId);
//            item = queryBuilder.queryForFirst();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return item;
//    }
}
