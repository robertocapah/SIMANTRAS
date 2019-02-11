package shp.template.Repo;

import android.content.Context;


import shp.template.Common.mConfigData;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Rian Andrivani on 11/21/2017.
 */

public class mConfigRepo {
    DatabaseHelper helper;
    //    public String API = "http://10.171.14.10/WebAPITemplate/API/";
//    public String APIToken = "http://10.171.14.10/WebAPITemplate/";
//    public String APIToken = "http://10.171.13.50:8013/";
    public String APIToken = "http://aedp.kalbenutritionals.web.id/api/";
//    public String APIToken = "http://10.171.14.26/apiAEDP/";
    //    public String APIToken = "http://10.171.14.16/apiAEDP/";
//    public String API = "http://10.171.13.50:8013/api/";
    public String API = APIToken + "api/";
    public String APIKLB = "http://api.karsalintasbuwana.com/";

    public mConfigRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    public Object findById(int value) throws SQLException {
        mConfigData item = null;
        try{
            item = helper.getmConfigDao().queryForId(value);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    public List<?> findAll() throws SQLException {
        List<mConfigData> items = null;
        try{
            items = helper.getmConfigDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public void InsertDefaultmConfig() throws SQLException {
        mConfigData data = new mConfigData();
        data.setIntId(1);
        data.setTxtName("android:versionCode");
        data.setTxtValue("5");
        data.setTxtDefaultValue("5");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(2);
        data.setTxtName("API_menu");
        data.setTxtValue(APIToken);
        data.setTxtDefaultValue(APIToken);
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(3);
        data.setTxtName("Domain Kalbe");
        data.setTxtValue("ONEKALBE.LOCAL");
        data.setTxtDefaultValue("ONEKALBE.LOCAL");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(4);
        data.setTxtName("Application Name");
        data.setTxtValue("Rian Application");
        data.setTxtDefaultValue("3VyizZ7haX2KCvR0wl64YwulEteHqsq5FLncJSL+pBM=");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(5);
        data.setTxtName("UserId");
        data.setTxtValue("rian.andrivani");
        data.setTxtDefaultValue("rian.andrivani");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(6);
        data.setTxtName("Text Footer");
        data.setTxtValue("Copyright &copy; KN IT 2018");
        data.setTxtDefaultValue("Copyright &copy; KN IT 2018");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(7);
        data.setTxtName("application_name");
        data.setTxtValue("AEDP Mobile Apps");
        data.setTxtDefaultValue("AEDP Mobile Apps");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(8);
        data.setTxtName("Username");
        data.setTxtValue("mochalatte-mae-stage");
        data.setTxtDefaultValue("1234567890");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(9);
        data.setTxtName("Username");
        data.setTxtValue("mochalatte-mae-stage");
        data.setTxtDefaultValue("mochalatte-mae-stage");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);

        data = new mConfigData();
        data.setIntId(10);
        data.setTxtName("Password");
        data.setTxtValue("1234567890");
        data.setTxtDefaultValue("1234567890");
        data.setIntEditAdmin("1");
        helper.getmConfigDao().createOrUpdate(data);
    }
}
