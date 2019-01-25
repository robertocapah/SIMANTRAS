package shp.kalbecallplanaedp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import shp.kalbecallplanaedp.Common.clsPhotoProfile;
import shp.kalbecallplanaedp.Common.clsToken;
import shp.kalbecallplanaedp.Common.mActivity;
import shp.kalbecallplanaedp.Common.mApotek;
import shp.kalbecallplanaedp.Common.mConfigData;
import shp.kalbecallplanaedp.Common.mCounterData;
import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Common.mFileAttachment;
import shp.kalbecallplanaedp.Common.mMenuData;
import shp.kalbecallplanaedp.Common.mSubActivity;
import shp.kalbecallplanaedp.Common.mSubSubActivity;
import shp.kalbecallplanaedp.Common.mTypeSubSubActivity;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Common.mUserMappingArea;
import shp.kalbecallplanaedp.Common.mUserRole;
import shp.kalbecallplanaedp.Common.tAkuisisiDetail;
import shp.kalbecallplanaedp.Common.tAkuisisiHeader;
import shp.kalbecallplanaedp.Common.tInfoProgramDetail;
import shp.kalbecallplanaedp.Common.tInfoProgramHeader;
import shp.kalbecallplanaedp.Common.tLogError;
import shp.kalbecallplanaedp.Common.tMaintenanceDetail;
import shp.kalbecallplanaedp.Common.tMaintenanceHeader;
import shp.kalbecallplanaedp.Common.tNotification;
import shp.kalbecallplanaedp.Common.tProgramVisit;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivityAttachment;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;

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

    protected Dao<tAkuisisiHeader, Integer> tAkuisisiHeaderDao;
    protected RuntimeExceptionDao<clsToken, Integer> tAkuisisiHeaderRuntimeDao;

    protected Dao<tAkuisisiDetail, Integer> tAkuisisiDetailDao;
    protected RuntimeExceptionDao<clsToken, Integer> tAkuisisiDetailRuntimeDao;

    protected Dao<mActivity, Integer> mActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mActivityRuntimeDao;

    protected Dao<mSubActivity, Integer> mSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mSubActivityRuntimeDao;

    protected Dao<mSubSubActivity, Integer> mSubSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mSubSubActivityRuntimeDao;

    protected Dao<mTypeSubSubActivity, Integer> mTypeSubSubActivityDao;
    protected RuntimeExceptionDao<clsToken, Integer> mTypeSubSubActivityRuntimeDao;


    protected Dao<mApotek, Integer> mApotekDao;
    protected Dao<mDokter, Integer> mDokterDao;
    protected Dao<tProgramVisit, Integer> tProgramVisitDao;
    protected Dao<tProgramVisitSubActivity, Integer> tProgramVisitSubActivityDao;
    protected Dao<tRealisasiVisitPlan, Integer> tRealisasiVisitPlanDao;
    protected Dao<mUserMappingArea, Integer> mUserMappingAreaDao;
    protected Dao<tProgramVisitSubActivityAttachment, Integer> tProgramVisitSubActivityAttachmentDao;
    protected Dao<mCounterData, Integer> mCounterDataDao;
    protected Dao<tMaintenanceHeader, Integer> tMaintenanceHeaderDao;
    protected Dao<tMaintenanceDetail, Integer> tMaintenanceDetailDao;
    protected Dao<tInfoProgramHeader, Integer> tInfoProgramHeaderDao;
    protected Dao<tInfoProgramDetail, Integer> tInfoProgramDetailDao;
    protected Dao<mFileAttachment, Integer> mFileAttachmentDao;
    protected Dao<tNotification, Integer> tNotificationDao;
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
            TableUtils.createTableIfNotExists(connectionSource, tAkuisisiHeader.class);
            TableUtils.createTableIfNotExists(connectionSource, tAkuisisiDetail.class);
            TableUtils.createTableIfNotExists(connectionSource, mActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mSubSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mTypeSubSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, mApotek.class);
            TableUtils.createTableIfNotExists(connectionSource, mDokter.class);
            TableUtils.createTableIfNotExists(connectionSource, tProgramVisit.class);
            TableUtils.createTableIfNotExists(connectionSource, tProgramVisitSubActivity.class);
            TableUtils.createTableIfNotExists(connectionSource, tRealisasiVisitPlan.class);
            TableUtils.createTableIfNotExists(connectionSource, mUserMappingArea.class);
            TableUtils.createTableIfNotExists(connectionSource, tProgramVisitSubActivityAttachment.class);
            TableUtils.createTableIfNotExists(connectionSource, mCounterData.class);
            TableUtils.createTableIfNotExists(connectionSource, tMaintenanceHeader.class);
            TableUtils.createTableIfNotExists(connectionSource, tMaintenanceDetail.class);
            TableUtils.createTableIfNotExists(connectionSource, tInfoProgramHeader.class);
            TableUtils.createTableIfNotExists(connectionSource, tInfoProgramDetail.class);
            TableUtils.createTableIfNotExists(connectionSource, tNotification.class);
            TableUtils.createTableIfNotExists(connectionSource, mFileAttachment.class);
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
            TableUtils.dropTable(connectionSource, tAkuisisiHeader.class, true);
            TableUtils.dropTable(connectionSource, tAkuisisiDetail.class, true);
            TableUtils.dropTable(connectionSource, mActivity.class, true);
            TableUtils.dropTable(connectionSource, mSubActivity.class, true);
            TableUtils.dropTable(connectionSource, mSubSubActivity.class, true);
            TableUtils.dropTable(connectionSource, mTypeSubSubActivity.class, true);
            TableUtils.dropTable(connectionSource, mApotek.class, true);
            TableUtils.dropTable(connectionSource, mDokter.class, true);
            TableUtils.dropTable(connectionSource, tProgramVisit.class, true);
            TableUtils.dropTable(connectionSource, tProgramVisitSubActivity.class, true);
            TableUtils.dropTable(connectionSource, tRealisasiVisitPlan.class, true);
            TableUtils.dropTable(connectionSource, mUserMappingArea.class, true);
            TableUtils.dropTable(connectionSource, tProgramVisitSubActivityAttachment.class, true);
            TableUtils.dropTable(connectionSource, mCounterData.class, true);
            TableUtils.dropTable(connectionSource, tMaintenanceHeader.class, true);
            TableUtils.dropTable(connectionSource, tMaintenanceDetail.class, true);
            TableUtils.dropTable(connectionSource, tInfoProgramHeader.class, true);
            TableUtils.dropTable(connectionSource, tInfoProgramDetail.class, true);
            TableUtils.dropTable(connectionSource, tNotification.class, true);
            TableUtils.dropTable(connectionSource, mFileAttachment.class, true);
//            TableUtils.dropTable(connectionSource, tLogError.class, true);

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
            TableUtils.clearTable(connectionSource, tAkuisisiHeader.class);
            TableUtils.clearTable(connectionSource, tAkuisisiDetail.class);
            TableUtils.clearTable(connectionSource, mActivity.class);
            TableUtils.clearTable(connectionSource, mSubActivity.class);
            TableUtils.clearTable(connectionSource, mSubSubActivity.class);
            TableUtils.clearTable(connectionSource, mTypeSubSubActivity.class);
            TableUtils.clearTable(connectionSource, mApotek.class);
            TableUtils.clearTable(connectionSource, mDokter.class);
            TableUtils.clearTable(connectionSource, tProgramVisit.class);
            TableUtils.clearTable(connectionSource, tProgramVisitSubActivity.class);
            TableUtils.clearTable(connectionSource, mUserMappingArea.class);
            TableUtils.clearTable(connectionSource, tRealisasiVisitPlan.class);
            TableUtils.clearTable(connectionSource, tProgramVisitSubActivityAttachment.class);
            TableUtils.clearTable(connectionSource, mCounterData.class);
            TableUtils.clearTable(connectionSource, tMaintenanceHeader.class);
            TableUtils.clearTable(connectionSource, tMaintenanceDetail.class);
            TableUtils.clearTable(connectionSource, tInfoProgramHeader.class);
            TableUtils.clearTable(connectionSource, tInfoProgramDetail.class);
            TableUtils.clearTable(connectionSource, tNotification.class);
            TableUtils.clearTable(connectionSource, mFileAttachment.class);
//            TableUtils.clearTable(connectionSource, tLogError.class);

//            onCreate(db, connectionSource);
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

    public Dao<tAkuisisiHeader, Integer> getAkuisisiHeaderDao() throws SQLException {
        if (tAkuisisiHeaderDao == null) {
            tAkuisisiHeaderDao = getDao(tAkuisisiHeader.class);
        }
        return tAkuisisiHeaderDao;
    }

    public Dao<tAkuisisiDetail, Integer> getAkuisisiDetailDao() throws SQLException {
        if (tAkuisisiDetailDao == null) {
            tAkuisisiDetailDao = getDao(tAkuisisiDetail.class);
        }
        return tAkuisisiDetailDao;
    }

    public Dao<mActivity, Integer> getmActivityDao() throws SQLException {
        if (mActivityDao == null) {
            mActivityDao = getDao(mActivity.class);
        }
        return mActivityDao;
    }

    public Dao<mSubActivity, Integer> getmSubActivityDao() throws SQLException {
        if (mSubActivityDao == null) {
            mSubActivityDao = getDao(mSubActivity.class);
        }
        return mSubActivityDao;
    }

    public Dao<mSubSubActivity, Integer> getmSubSubActivityDao() throws SQLException {
        if (mSubSubActivityDao == null) {
            mSubSubActivityDao = getDao(mSubSubActivity.class);
        }
        return mSubSubActivityDao;
    }

    public Dao<mTypeSubSubActivity, Integer> getmTypeSubSubActivityDao() throws SQLException {
        if (mTypeSubSubActivityDao == null) {
            mTypeSubSubActivityDao = getDao(mTypeSubSubActivity.class);
        }
        return mTypeSubSubActivityDao;
    }

    public Dao<mApotek, Integer> getmApotekDao() throws SQLException {
        if (mApotekDao == null) {
            mApotekDao = getDao(mApotek.class);
        }
        return mApotekDao;
    }

    public Dao<mDokter, Integer> getmDokterDao() throws SQLException {
        if (mDokterDao == null) {
            mDokterDao = getDao(mDokter.class);
        }
        return mDokterDao;
    }

    public Dao<tProgramVisit, Integer> gettProgramVisitDao() throws SQLException {
        if (tProgramVisitDao == null) {
            tProgramVisitDao = getDao(tProgramVisit.class);
        }
        return tProgramVisitDao;
    }

    public Dao<tProgramVisitSubActivity, Integer> gettProgramVisitSubActivityDao() throws SQLException {
        if (tProgramVisitSubActivityDao == null) {
            tProgramVisitSubActivityDao = getDao(tProgramVisitSubActivity.class);
        }
        return tProgramVisitSubActivityDao;
    }

    public Dao<tRealisasiVisitPlan, Integer> gettRealisasiVisitPlanDao() throws SQLException {
        if (tRealisasiVisitPlanDao == null) {
            tRealisasiVisitPlanDao = getDao(tRealisasiVisitPlan.class);
        }
        return tRealisasiVisitPlanDao;
    }

    public Dao<mUserMappingArea, Integer> getmUserMappingAreaDao() throws SQLException {
        if (mUserMappingAreaDao == null) {
            mUserMappingAreaDao = getDao(mUserMappingArea.class);
        }
        return mUserMappingAreaDao;
    }

    public Dao<tProgramVisitSubActivityAttachment, Integer> gettProgramVisitSubActivityAttachmentDao() throws SQLException {
        if (tProgramVisitSubActivityAttachmentDao == null) {
            tProgramVisitSubActivityAttachmentDao = getDao(tProgramVisitSubActivityAttachment.class);
        }
        return tProgramVisitSubActivityAttachmentDao;
    }

    public Dao<mCounterData, Integer> getmCounterDataDao() throws SQLException {
        if (mCounterDataDao == null) {
            mCounterDataDao = getDao(mCounterData.class);
        }
        return mCounterDataDao;
    }

    public Dao<tMaintenanceHeader, Integer> gettMaintenanceHeaderDao() throws SQLException {
        if (tMaintenanceHeaderDao == null) {
            tMaintenanceHeaderDao = getDao(tMaintenanceHeader.class);
        }
        return tMaintenanceHeaderDao;
    }

    public Dao<tMaintenanceDetail, Integer> gettMaintenanceDetailDao() throws SQLException {
        if (tMaintenanceDetailDao == null) {
            tMaintenanceDetailDao = getDao(tMaintenanceDetail.class);
        }
        return tMaintenanceDetailDao;
    }

    public Dao<tInfoProgramHeader, Integer> gettInfoProgramHeaderDao() throws SQLException {
        if (tInfoProgramHeaderDao == null) {
            tInfoProgramHeaderDao = getDao(tInfoProgramHeader.class);
        }
        return tInfoProgramHeaderDao;
    }

    public Dao<tInfoProgramDetail, Integer> gettInfoProgramDetailDao() throws SQLException {
        if (tInfoProgramDetailDao == null) {
            tInfoProgramDetailDao = getDao(tInfoProgramDetail.class);
        }
        return tInfoProgramDetailDao;
    }

    public Dao<tNotification, Integer> gettNotificationDao() throws SQLException {
        if (tNotificationDao == null) {
            tNotificationDao = getDao(tNotification.class);
        }
        return tNotificationDao;
    }

    public Dao<mFileAttachment, Integer> getmFileAttachmentDao() throws SQLException {
        if (mFileAttachmentDao == null) {
            mFileAttachmentDao = getDao(mFileAttachment.class);
        }
        return mFileAttachmentDao;
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
        tAkuisisiHeaderDao = null;
        tAkuisisiDetailDao = null;
        mUserLoginsDao = null;
        mUserRolesDao = null;
        mActivityDao = null;
        mSubActivityDao = null;
        mSubSubActivityDao = null;
        mTypeSubSubActivityDao = null;
        mDokterDao = null;
        mApotekDao = null;
        tProgramVisitDao = null;
        tProgramVisitSubActivityDao = null;
        tRealisasiVisitPlanDao = null;
        mUserMappingAreaDao = null;
        tProgramVisitSubActivityAttachmentDao = null;
        mCounterDataDao = null;
        tMaintenanceHeaderDao = null;
        tMaintenanceDetailDao = null;
        tInfoProgramHeaderDao = null;
        tInfoProgramDetailDao = null;
        tNotificationDao = null;
        mFileAttachmentDao = null;
        tLogErrorDao = null;
    }
}
