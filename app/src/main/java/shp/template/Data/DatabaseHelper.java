package shp.template.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import shp.template.Common.clsPhotoProfile;
import shp.template.Common.clsToken;
import shp.template.Common.mConfigData;
import shp.template.Common.mCounterData;
import shp.template.Common.mMenuData;
import shp.template.Common.mUserLogin;
import shp.template.Common.mUserMappingArea;
import shp.template.Common.mUserRole;
import shp.template.Common.tLogError;

import java.sql.SQLException;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static clsHardCode _path = new clsHardCode();
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    protected Dao<mConfigData, Integer> mConfigDao;

    protected Dao<mMenuData, Integer> menuDao;
    protected RuntimeExceptionDao<mMenuData, Integer> menuRuntimeDao;

    protected Dao<clsPhotoProfile, Integer> profileDao;
    protected RuntimeExceptionDao<clsPhotoProfile, Integer> profileRuntimeDao;

    protected Dao<clsToken, Integer> tokenDao;
    protected RuntimeExceptionDao<clsToken, Integer> tokenRuntimeDao;

    protected Dao<mUserRole, Integer> mUserRolesDao;
    protected RuntimeExceptionDao<clsToken, Integer> mUserRolesRuntimeDao;

    protected Dao<mUserLogin, Integer> mUserLoginsDao;
    protected RuntimeExceptionDao<clsToken, Integer> mUserLoginsRuntimeDao;

    protected Dao<mUserMappingArea, Integer> mUserMappingAreaDao;
    protected Dao<mCounterData, Integer> mCounterDataDao;
    protected Dao<tLogError, Integer> tLogErrorDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, mConfigData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsToken.class);
            TableUtils.createTableIfNotExists(connectionSource, mMenuData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsPhotoProfile.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserRole.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserMappingArea.class);
            TableUtils.createTableIfNotExists(connectionSource, mCounterData.class);
            TableUtils.createTableIfNotExists(connectionSource, tLogError.class);

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
            TableUtils.dropTable(connectionSource, mConfigData.class, true);
            TableUtils.dropTable(connectionSource, mMenuData.class, true);
            TableUtils.dropTable(connectionSource, clsPhotoProfile.class, true);
            TableUtils.dropTable(connectionSource, mUserRole.class, true);
            TableUtils.dropTable(connectionSource, mUserLogin.class, true);
            TableUtils.dropTable(connectionSource, mUserMappingArea.class, true);
            TableUtils.dropTable(connectionSource, mCounterData.class, true);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDataAfterLogout(){
        try {
            TableUtils.clearTable(connectionSource, mMenuData.class);
            TableUtils.clearTable(connectionSource, clsPhotoProfile.class);
            TableUtils.clearTable(connectionSource, mUserRole.class);
            TableUtils.clearTable(connectionSource, mUserLogin.class);
            TableUtils.clearTable(connectionSource, mUserMappingArea.class);
            TableUtils.clearTable(connectionSource, mCounterData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<mConfigData, Integer> getmConfigDao() throws SQLException {
        if (mConfigDao == null) {
            mConfigDao = getDao(mConfigData.class);
//            mConfigDao.setObjectCache(false);
        }
        return mConfigDao;
    }

    public Dao<clsToken, Integer> getTokenDao() throws SQLException {
        if (tokenDao == null) {
            tokenDao = getDao(clsToken.class);
        }
        return tokenDao;
    }

    public Dao<mMenuData, Integer> getMenuDao() throws SQLException {
        if (menuDao == null) {
            menuDao = getDao(mMenuData.class);
        }
        return menuDao;
    }

    public Dao<clsPhotoProfile, Integer> getProfileDao() throws SQLException {
        if (profileDao == null) {
            profileDao = getDao(clsPhotoProfile.class);
        }
        return profileDao;
    }

    public Dao<mUserRole, Integer> getmUserRolesDao() throws SQLException {
        if (mUserRolesDao == null) {
            mUserRolesDao = getDao(mUserRole.class);
        }
        return mUserRolesDao;
    }

    public Dao<mUserLogin, Integer> getmUserLoginsDao() throws SQLException {
        if (mUserLoginsDao == null) {
            mUserLoginsDao = getDao(mUserLogin.class);
        }
        return mUserLoginsDao;
    }

    public Dao<mUserMappingArea, Integer> getmUserMappingAreaDao() throws SQLException {
        if (mUserMappingAreaDao == null) {
            mUserMappingAreaDao = getDao(mUserMappingArea.class);
        }
        return mUserMappingAreaDao;
    }

    public Dao<mCounterData, Integer> getmCounterDataDao() throws SQLException {
        if (mCounterDataDao == null) {
            mCounterDataDao = getDao(mCounterData.class);
        }
        return mCounterDataDao;
    }

    public Dao<tLogError, Integer> gettLogErrorDao() throws SQLException {
        if (tLogErrorDao == null) {
            tLogErrorDao = getDao(tLogError.class);
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
