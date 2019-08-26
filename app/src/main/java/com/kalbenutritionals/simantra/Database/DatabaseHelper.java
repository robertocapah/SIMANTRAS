package com.kalbenutritionals.simantra.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kalbenutritionals.simantra.Database.Common.ClsExpedition;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.Database.Common.ClsOrganisation;
import com.kalbenutritionals.simantra.Database.Common.ClsPhotoProfile;
import com.kalbenutritionals.simantra.Database.Common.ClsTChecker;
import com.kalbenutritionals.simantra.Database.Common.ClsTConfigHelper;
import com.kalbenutritionals.simantra.Database.Common.ClsTDataRejection;
import com.kalbenutritionals.simantra.Database.Common.ClsTJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsTNotifikasi;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmChecklist;
import com.kalbenutritionals.simantra.Database.Common.ClsmConfigData;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmJenisPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmKendaraan;
import com.kalbenutritionals.simantra.Database.Common.ClsmLocationPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmMenuData;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserMappingArea;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserRole;
import com.kalbenutritionals.simantra.Database.Common.ClstLogError;
import com.kalbenutritionals.simantra.Data.ClsHardCode;

import java.sql.SQLException;

/**
 * Created by roberto 16 April 19.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static ClsHardCode _path = new ClsHardCode();
    // txtPertanyaan of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 26;

    // the DAO object we use to access the SimpleData table
    protected Dao<ClsmConfigData, Integer> mConfigDao;
    protected Dao<ClsmMenuData, Integer> menuDao;
    protected Dao<ClsPhotoProfile, Integer> profileDao;
    protected Dao<ClsToken, Integer> tokenDao;
    protected Dao<ClsmUserRole, Integer> mUserRolesDao;
    protected Dao<ClsmUserLogin, Integer> mUserLoginsDao;
    protected Dao<ClsmUserMappingArea, Integer> mUserMappingAreaDao;
    protected Dao<ClsmCounterData, Integer> mCounterDataDao;
    protected Dao<ClstLogError, Integer> tLogErrorDao;
    protected Dao<ClsmChecklist, Integer> mChecklistDao;
    protected Dao<ClsmKendaraan, Integer> mKendaraabDao;
    protected Dao<ClsmJenisPertanyaan, Integer> mJenisPertanyaanDao;
    protected Dao<ClsmLocationPertanyaan, Integer> mLocationDao;
    protected Dao<ClsmPertanyaan, Integer> mPertanyaanDao;
    protected Dao<ClsmJawaban, Integer> mJawabanDao;
    protected Dao<ClsTJawaban, Integer> tJawabanDao;
    protected Dao<ClsTChecker, Integer> tCheckersDao;
    protected Dao<ClsTNotifikasi, Integer> tNotifikasisDao;
    protected Dao<ClsImages, Integer> ClsImagesDao;
    protected Dao<ClsTConfigHelper, Integer> ClsTCommonRejectionDao;
    protected Dao<ClsTDataRejection, Integer> ClsTDataRejectionDao;
    protected Dao<ClsOrganisation, Integer> ClsOrganisationDao;
    protected Dao<ClsExpedition, Integer> ClsExpeditionDao;



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
            TableUtils.createTableIfNotExists(connectionSource, ClsmChecklist.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmKendaraan.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmPertanyaan.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmJenisPertanyaan.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmLocationPertanyaan.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsmJawaban.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsTJawaban.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsTChecker.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsTNotifikasi.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsImages.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsTDataRejection.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsTConfigHelper.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsOrganisation.class);
            TableUtils.createTableIfNotExists(connectionSource, ClsExpedition.class);

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
            TableUtils.dropTable(connectionSource, ClsmChecklist.class,true);
            TableUtils.dropTable(connectionSource, ClsmKendaraan.class,true);
            TableUtils.dropTable(connectionSource, ClsmPertanyaan.class,true);
            TableUtils.dropTable(connectionSource, ClsmJenisPertanyaan.class,true);
            TableUtils.dropTable(connectionSource, ClsmLocationPertanyaan.class,true);
            TableUtils.dropTable(connectionSource, ClsmJawaban.class,true);
            TableUtils.dropTable(connectionSource, ClsTJawaban.class,true);
            TableUtils.dropTable(connectionSource, ClsTChecker.class,true );
            TableUtils.dropTable(connectionSource, ClsTNotifikasi.class,true );
            TableUtils.dropTable(connectionSource, ClsImages.class,true );
            TableUtils.dropTable(connectionSource, ClsTDataRejection.class,true );
            TableUtils.dropTable(connectionSource, ClsTConfigHelper.class,true );
            TableUtils.dropTable(connectionSource, ClsOrganisation.class,true );
            TableUtils.dropTable(connectionSource, ClsExpedition.class,true );

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
            TableUtils.clearTable(connectionSource, ClsmChecklist.class);
            TableUtils.clearTable(connectionSource, ClsmKendaraan.class);
            TableUtils.clearTable(connectionSource, ClsmPertanyaan.class);
            TableUtils.clearTable(connectionSource, ClsmJenisPertanyaan.class);
            TableUtils.clearTable(connectionSource, ClsmLocationPertanyaan.class);
            TableUtils.clearTable(connectionSource, ClsmJawaban.class);
            TableUtils.clearTable(connectionSource, ClsTJawaban.class);
            TableUtils.clearTable(connectionSource, ClsTChecker.class);
            TableUtils.clearTable(connectionSource, ClsTNotifikasi.class);
            TableUtils.clearTable(connectionSource, ClsImages.class);
            TableUtils.clearTable(connectionSource, ClsTDataRejection.class);
            TableUtils.clearTable(connectionSource, ClsTConfigHelper.class);
            TableUtils.clearTable(connectionSource, ClsOrganisation.class);
            TableUtils.clearTable(connectionSource, ClsExpedition.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean clearDataNotif(){
        boolean valid = false;
        try{
            TableUtils.clearTable(connectionSource, ClsTNotifikasi.class);
            valid = true;
        }catch (SQLException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }
    public boolean clearDataPertanyaanJawaban(){
        boolean valid = false;
        try{
            TableUtils.clearTable(connectionSource, ClsTNotifikasi.class);
            TableUtils.clearTable(connectionSource, ClsmJawaban.class);
            TableUtils.clearTable(connectionSource, ClsmPertanyaan.class);
            TableUtils.clearTable(connectionSource, ClsImages.class);
            TableUtils.clearTable(connectionSource, ClsTDataRejection.class);
            TableUtils.clearTable(connectionSource, ClsTConfigHelper.class);
            valid = true;
        }catch (SQLException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    public Dao<ClsExpedition, Integer> getClsExpeditionDao() throws SQLException {
        if(ClsExpeditionDao == null){
            ClsExpeditionDao = getDao(ClsExpedition.class);
        }
        return ClsExpeditionDao;
    }
    public Dao<ClsOrganisation, Integer> getClsOrganisationDao() throws SQLException {
        if(ClsOrganisationDao == null){
            ClsOrganisationDao = getDao(ClsOrganisation.class);
        }
        return ClsOrganisationDao;
    }
    public Dao<ClsTConfigHelper, Integer> getClsTCommonRejectionDao() throws SQLException {
        if(ClsTCommonRejectionDao == null){
            ClsTCommonRejectionDao = getDao(ClsTConfigHelper.class);
        }
        return ClsTCommonRejectionDao;
    }

    public Dao<ClsTDataRejection, Integer> getClsTDataRejectionDao() throws SQLException {
        if(ClsTDataRejectionDao == null){
            ClsTDataRejectionDao = getDao(ClsTDataRejection.class);
        }
        return ClsTDataRejectionDao;
    }

    public Dao<ClsImages, Integer> gettClsImagesDao() throws SQLException {
        if(ClsImagesDao == null){
            ClsImagesDao = getDao(ClsImages.class);
        }
        return ClsImagesDao;
    }
    public Dao<ClsTNotifikasi, Integer> gettNotifikasisDao() throws SQLException {
        if(tNotifikasisDao == null){
            tNotifikasisDao = getDao(ClsTNotifikasi.class);
        }
        return tNotifikasisDao;
    }

    public Dao<ClsTJawaban, Integer> gettJawabanDao() throws SQLException {
        if(tJawabanDao == null){
            tJawabanDao = getDao(ClsTJawaban.class);
        }
        return tJawabanDao;
    }

    public Dao<ClsTChecker, Integer> gettCheckersDao() throws SQLException {
        if(tCheckersDao == null){
            tCheckersDao = getDao(ClsTChecker.class);
        }
        return tCheckersDao;
    }

    public Dao<ClsmJawaban, Integer> getmJawabanDao() throws SQLException {
        if(mJawabanDao == null){
            mJawabanDao = getDao(ClsmJawaban.class);
        }
        return mJawabanDao;
    }

    public Dao<ClsmPertanyaan, Integer> getmPertanyaanDao() throws SQLException{
        if (mPertanyaanDao == null){
            mPertanyaanDao = getDao(ClsmPertanyaan.class);
        }
        return mPertanyaanDao;
    }
    public Dao<ClsmJenisPertanyaan, Integer> getmJenisPertanyaanDao() throws SQLException{
        if (mJenisPertanyaanDao == null){
            mJenisPertanyaanDao = getDao(ClsmJenisPertanyaan.class);
        }
        return mJenisPertanyaanDao;
    }
    public Dao<ClsmLocationPertanyaan, Integer> getmLocationDao() throws SQLException{
        if (mLocationDao == null){
            mLocationDao = getDao(ClsmLocationPertanyaan.class);
        }
        return mLocationDao;
    }
    public Dao<ClsmKendaraan, Integer> getmKendaraabDao() throws SQLException{
        if (mKendaraabDao == null){
            mKendaraabDao = getDao(ClsmKendaraan.class);
        }
        return mKendaraabDao;
    }

    public Dao<ClsmChecklist, Integer> getmChecklistDao() throws SQLException {
        if (mChecklistDao == null){
            mChecklistDao = getDao(ClsmChecklist.class);
        }
        return mChecklistDao;
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
        mChecklistDao = null;
        mPertanyaanDao = null;
        mJawabanDao = null;
        tCheckersDao = null;
        tJawabanDao = null;
        tNotifikasisDao = null;
        ClsImagesDao = null;
        ClsTCommonRejectionDao = null;
        ClsTDataRejectionDao = null;
        ClsOrganisationDao = null;
        ClsExpeditionDao = null;
    }
}
