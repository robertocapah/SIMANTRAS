package shp.template.BL;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import shp.template.Common.clsDataError;
import shp.template.Common.clsDataJson;
import shp.template.Common.clsPushData;
import shp.template.Common.clsToken;
import shp.template.Common.mConfigData;
import shp.template.Common.mCounterData;
import shp.template.Common.mUserLogin;
import shp.template.Common.tLogError;
import shp.template.Data.CustomVolleyResponseListener;
import shp.template.Data.VolleyResponseListener;
import shp.template.Data.VolleyUtils;
import shp.template.Data.clsHardCode;
import shp.template.Data.enumCounterData;
import shp.template.Repo.clsTokenRepo;
import shp.template.Repo.mConfigRepo;
import shp.template.Repo.mCounterDataRepo;
import shp.template.Repo.mUserLoginRepo;
import shp.template.Repo.tLogErrorRepo;
import shp.template.SplashActivity;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbe.mobiledevknlibs.Volley.volley.VolleyMultipartRequest;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class clsHelperBL {

    String access_token,clientId = "";
    List<clsToken> dataToken;

    public clsPushData pushData(String versionName, Context context){
        clsPushData dtclsPushData = new clsPushData();
        clsDataJson dtPush = new clsDataJson();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        List<Boolean> isDataNull = new ArrayList<>();
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            mUserLogin dataLogin = new clsMainBL().getUserLogin(context);
            dtPush.setDtLogin(dataLogin.getDtLogIn());
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                mCounterDataRepo _mCounterDataRepo = new mCounterDataRepo(context);
                mCounterData _mCounterData = new mCounterData();
                _mCounterData.setIntId(enumCounterData.MonitorScedule.getIdCounterData());
                _mCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _mCounterData.setTxtName("Monitor Service");
                _mCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_mCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }



//            List<tLogError>

            FileUpload = new HashMap<>();
            dtPush.setFromUnplan(false);

//            if ()
        }else {
            dtPush = null;
        }
        dtclsPushData.setDataJson(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public clsPushData pushDataError(String versionName, Context context){
        clsPushData dtclsPushData = new clsPushData();
        clsDataError dtPush = new clsDataError();
        mUserLoginRepo loginRepo = new mUserLoginRepo(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            mUserLogin dataLogin = new clsMainBL().getUserLogin(context);
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                mCounterDataRepo _mCounterDataRepo = new mCounterDataRepo(context);
                mCounterData _mCounterData = new mCounterData();
                _mCounterData.setIntId(enumCounterData.MonitorScedule.getIdCounterData());
                _mCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _mCounterData.setTxtName("Monitor Service");
                _mCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_mCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            tLogErrorRepo _tLogErrorRepo = new tLogErrorRepo(context);
            List<tLogError> ListOfDataError = _tLogErrorRepo.getAllPushData();

            FileUpload = new HashMap<>();
            if (ListOfDataError!=null){
                dtPush.setListOfDatatLogError(ListOfDataError);
                for (tLogError data : ListOfDataError){
                    if (data.getBlobImg()!=null){
                        FileName.add(data.getTxtGuiId());
                        FileUpload.put(data.getTxtGuiId(), data.getBlobImg());
                    }
                }
            }

        }else {
            dtPush = null;
        }
        dtclsPushData.setDataError(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }


    public List<clsToken> getDataToken(Context context){
        List<clsToken> dtToken = null;
        try {
            clsTokenRepo tokenRepo = new clsTokenRepo(context);
            dtToken = (List<clsToken>) tokenRepo.findAll();
            if (dtToken.size() == 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtToken;
    }

    public void volleyLogin(final Activity context, String strLinkAPI, final String mRequestBody, String progressBarType, final boolean isMustLogout, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        final mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                String msg = "";
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            new ToastCustom().showToasty(context,message,4);
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
//                                    ToastCustom.showToasty(context,"Success get new Access Token",1);
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        new ToastCustom().showToasty(context,"Please press the button again",3);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    if (isMustLogout){
                        popup();
                    }
                    new ToastCustom().showToasty(context,msg,4);
                    finalDialog1.dismiss();
                }
            }

            public void popup() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

                builder.setTitle("Failed to Logout");
                builder.setMessage("You Have to request again");

                builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new SplashActivity().logout(context);
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();

                alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    public void volleyLoginCustom(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final CustomVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        final mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage, finalDialog);
//                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                String msg = "";
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            new ToastCustom().showToasty(context,message,4);
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
//                                    ToastCustom.showToasty(context,"Success get new Access Token",1);
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        new ToastCustom().showToasty(context,"Please press the button again",3);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    new ToastCustom().showToasty(context,msg,4);
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    public void volleyCheckVersion(final Context context, String strLinkAPI, final String mRequestBody, final AccountManager accountManager, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                String msg = "";
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
//                                    ToastCustom.showToasty(context,"Success get new Access Token",1);
//                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        new SplashActivity().checkVersion(context, accountManager);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();
                    popup(context, accountManager);

                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    new ToastCustom().showToasty(context,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                    popup(context, accountManager);
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);

    }

    public void popup(final Context context, final AccountManager accountManager) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setTitle("Check Version");
        builder.setMessage("You Have to request again");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                new SplashActivity().checkVersion(context, accountManager);
                dialog.dismiss();
            }
        });

//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });

        android.app.AlertDialog alert = builder.create();
        alert.show();

        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
    }

    public void volleyDownloadData(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                String msg = "";
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            new ToastCustom().showToasty(context,message,4);
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(context, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    new ToastCustom().showToasty(context,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    public void volleyDownloadDataget(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        mConfigRepo configRepo = new mConfigRepo(context);
        try {
            mConfigData configDataClient = (mConfigData) configRepo.findById(4);
            clientId = configDataClient.getTxtDefaultValue().toString();
            dataToken = getDataToken(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.GET, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String strLinkAPI = new clsHardCode().linkToken;
                final String refresh_token = dataToken.get(0).txtRefreshToken;
                NetworkResponse networkResponse = error.networkResponse;
                String msg = "";
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        //Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            new ToastCustom().showToasty(context,message,4);
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            if (response != null) {
                                try {
                                    String accessToken = "";
                                    String newRefreshToken = "";
                                    String refreshToken = "";
                                    JSONObject jsonObject = new JSONObject(response);
                                    accessToken = jsonObject.getString("access_token");
                                    refreshToken = jsonObject.getString("refresh_token");
                                    String dtIssued = jsonObject.getString(".issued");

                                    clsToken data = new clsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    clsTokenRepo tokenRepo = new clsTokenRepo(context);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(context, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    finalDialog1.dismiss();

                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    new ToastCustom().showToasty(context,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(context);
                access_token = dataToken.get(0).getTxtUserToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }
        };
        request.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    public void volleyDownloadDataKLB(final Context context, String strLinkAPI, String progressBarType, final VolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        StringRequest request = new StringRequest(Request.Method.GET, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String msg = "";
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        // body for value error response
                        body[0] = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body[0]);
                        message[0] = jsonObject.getString("Message");
                        Toast.makeText(context, "Error 401, " + message[0], Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finalDialog1.dismiss();
                } else  if (error instanceof NetworkError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    msg = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    msg = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    msg = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    msg = "Connection TimeOut! Please check your internet connection.";
                } else {
                    msg = "Error 500, Server Error";
                }

                if (msg!=null||!msg.equals("")){
                    new ToastCustom().showToasty(context,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                }

//
//                else {
//                    ToastCustom.showToasty(context,"Failed Download Data, please check your connection",4);
////                    Toast.makeText(context, "Failed Download Data Doctor/Pharmacy", Toast.LENGTH_SHORT).show();
//                    finalDialog1.dismiss();
//                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                mConfigRepo configRepo = new mConfigRepo(context);
                mConfigData UserName = null;
                mConfigData Passoword = null;
                try {
                    UserName = (mConfigData) configRepo.findById(9);
                    Passoword = (mConfigData) configRepo.findById(10);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                Map<String,String> headers = SyncStateContract.Constants.getHeaders(context);
                HashMap<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = UserName.getTxtDefaultValue().toString() +":"+Passoword.getTxtDefaultValue().toString();
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
//        request.setRetryPolicy(new
//                DefaultRetryPolicy(60000,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}


