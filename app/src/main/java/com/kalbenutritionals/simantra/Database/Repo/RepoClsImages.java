package com.kalbenutritionals.simantra.Database.Repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto on 7/9/2019
 */
public class RepoClsImages implements CRUD {
    DatabaseHelper helper;
    public RepoClsImages(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        ClsImages object = (ClsImages) item;
        try {
            index = helper.gettClsImagesDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        ClsImages object = (ClsImages) item;
        try {
            Dao.CreateOrUpdateStatus status  = helper.gettClsImagesDao().createOrUpdate(object);
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


    public List<ClsImages>  findByFillDtlId(int id) throws SQLException {
        List<ClsImages> items = null;
        try {
            QueryBuilder<ClsImages, Integer> builder = helper.gettClsImagesDao().queryBuilder();
            items = builder.where().eq(ClsImages.TXTFILLDTLID, id).query();
        } catch (Exception ex) {

        }
        return items;
    }

    @Override
    public List<ClsImages> findAll() throws SQLException {
        List<ClsImages> items = null;
        try {
            items = helper.gettClsImagesDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ClsImages> findLinkIssuesByDtlId(int intFlag, int dtlId) throws SQLException {
        List<ClsImages> items = null;
        List<String> txtItems = new ArrayList<>();
        try {
            QueryBuilder<ClsImages, Integer> builder = helper.gettClsImagesDao().queryBuilder();
            items = builder.where().eq(ClsImages.TXTintFlag, intFlag).and().eq(ClsImages.TXTFILLDTLID,dtlId).query();
            /*for (ClsImages img :
                    items) {
                String link = img.getTxtLinkImages();
                txtItems.add(link);
            }*/
        } catch (Exception ex) {

        }
        return items;
    }
}
