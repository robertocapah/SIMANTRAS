package com.kalbenutritionals.simantra.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kalbenutritionals.simantra.Database.Common.ClsPhotoProfile;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmConfigData;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmMenuData;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserMappingArea;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserRole;
import com.kalbenutritionals.simantra.Database.Common.ClstLogError;
import com.kalbenutritionals.simantra.Data.ClsHardCode;

import java.sql.SQLException;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static ClsHardCode _path = new ClsHardCode();
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    protected Dao<ClsmConfigData, Integer> mConfigDao;

    protected Dao<ClsmMenuData, Integer> menuDao;
    protected RuntimeExceptionDao<ClsmMenuData, Integer> menuRuntimeDao;

    protected Dao<ClsPhotoProfile, Integer> profileDao;
    protected RuntimeExceptionDao<ClsPhotoProfile, Integer> profileRuntimeDao;

    protected Dao<ClsToken, Integer> tokenDao;
    protected RuntimeExceptionDao<ClsToken, Integer> tokenRuntimeDao;

    protected Dao<ClsmUserRole, Integer> mUserRolesDao;
    protected RuntimeExceptionDao<ClsToken, Integer> mUserRolesRuntimeDao;

    protected Dao<ClsmUserLogin, Integer> mUserLoginsDao;
    protected RuntimeExceptionDao<ClsToken, Integer> mUserLoginsRuntimeDao;

    protected Dao<ClsmUserMappingArea, Integer> mUserMappingAreaDao;
    protected Dao<ClsmCounterData, Integer> mCounterDataDao;
    protected Dao<ClstLogError, Integer> tLogErrorDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ClsmConfigData.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsToken.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmMenuData.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsPhotoProfile.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmUserRole.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmUserLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmUserMappingArea.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmCounterData.class);
            TableUtils.createTableIfNotExists(connectionSource, ClstLogError.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        Dao<clsLogin, Integer> dao = null;
        try {
//            dao = getLoginDao();

//            if (oldVersion < 2) {
//                dao.executeRaw("ALTER TABLE `clsLogin` ADD COLUMN txtRefreshToken TEXT;");
//            }
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, ClsmConfigData.class, true);
            TableUtils.dropTable(connectionSource, ClsmMenuData.class, true);
            TableUtils.dropTable(connectionSource, ClsPhotoProfile.class, true);
            TableUtils.dropTable(connectionSource, ClsmUserRole.class, true);
            TableUtils.dropTable(connectionSource, ClsmUserLogin.class, true);
            TableUtils.dropTable(connectionSource, ClsmUserMappingArea.class, true);
            TableUtils.dropTable(connectionSource, ClsmCounterData.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataAfterLogout(){
        try {
            TableUtils.clearTable(connectionSource, ClsmMenuData.class);
            TableUtils.clearTable(connectionSource, ClsPhotoProfile.class);
            TableUtils.clearTable(connectionSource, ClsmUserRole.class);
            TableUtils.clearTable(connectionSource, ClsmUserLogin.class);
            TableUtils.clearTable(connectionSource, ClsmUserMappingArea.class);
            TableUtils.clearTable(connectionSource, ClsmCounterData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<ClsmConfigData, Integer> getmConfigDao() throws SQLException {
        if (mConfigDao == null) {
            mConfigDao = getDao(ClsmConfigData.class);
//            mConfigDao.setObjectCache(false);
        }
        return mConfigDao;
    }

    public Dao<ClsToken, Integer> getTokenDao() throws SQLException {
        if (tokenDao == null) {
            tokenDao = getDao(ClsToken.class);
        }
        return tokenDao;
    }

    public Dao<ClsmMenuData, Integer> getMenuDao() throws SQLException {
        if (menuDao == null) {
            menuDao = getDao(ClsmMenuData.class);
        }
        return menuDao;
    }

    public Dao<ClsPhotoProfile, Integer> getProfileDao() throws SQLException {
        if (profileDao == null) {
            profileDao = getDao(ClsPhotoProfile.class);
        }
        return profileDao;
    }

    public Dao<ClsmUserRole, Integer> getmUserRolesDao() throws SQLException {
        if (mUserRolesDao == null) {
            mUserRolesDao = getDao(ClsmUserRole.class);
        }
        return mUserRolesDao;
    }

    public Dao<ClsmUserLogin, Integer> getmUserLoginsDao() throws SQLException {
        if (mUserLoginsDao == null) {
            mUserLoginsDao = getDao(ClsmUserLogin.class);
        }
        return mUserLoginsDao;
    }

    public Dao<ClsmUserMappingArea, Integer> getmUserMappingAreaDao() throws SQLException {
        if (mUserMappingAreaDao == null) {
            mUserMappingAreaDao = getDao(ClsmUserMappingArea.class);
        }
        return mUserMappingAreaDao;
    }

    public Dao<ClsmCounterData, Integer> getmCounterDataDao() throws SQLException {
        if (mCounterDataDao == null) {
            mCounterDataDao = getDao(ClsmCounterData.class);
        }
        return mCounterDataDao;
    }

    public Dao<ClstLogError, Integer> gettLogErrorDao() throws SQLException {
        if (tLogErrorDao == null) {
            tLogErrorDao = getDao(ClstLogError.class);
        }
        return tLogErrorDao;
    }


    @Override
    public void close() {
        mConfigDao = null;
        tokenDao = null;
        menuDao = null;
        profileDao = null;
        mUserLoginsDao = null;
        mUserRolesDao = null;
        mUserMappingAreaDao = null;
        mCounterDataDao = null;
        tLogErrorDao = null;
    }
}
