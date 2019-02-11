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

import shp.template.ActivitySplash;
import shp.template.Common.ClsDataError;
import shp.template.Common.ClsDataJson;
import shp.template.Common.ClsPushData;
import shp.template.Common.ClsToken;
import shp.template.Common.ClsmConfigData;
import shp.template.Common.ClsmCounterData;
import shp.template.Common.ClsmUserLogin;
import shp.template.Common.ClstLogError;
import shp.template.Data.ClsHardCode;
import shp.template.Data.InterfaceCustomVolleyResponseListener;
import shp.template.Data.InterfaceVolleyResponseListener;
import shp.template.Data.VolleyUtils;
import shp.template.Data.EnumCounterData;
import shp.template.Repo.RepoclsToken;
import shp.template.Repo.RepomConfig;
import shp.template.Repo.RepomUserLogin;
import shp.template.Repo.RepotLogError;
import shp.template.Repo.RepomCounterData;

import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

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

public class BLHelper {

    String access_token,clientId = "";
    List<ClsToken> dataToken;

    public ClsPushData pushData(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataJson dtPush = new ClsDataJson();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        List<Boolean> isDataNull = new ArrayList<>();
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = new BLMain().getUserLogin(context);
            dtPush.setDtLogin(dataLogin.getDtLogIn());
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }



//            List<ClstLogError>

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

    public ClsPushData pushDataError(String versionName, Context context){
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataError dtPush = new ClsDataError();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context)>0){
            ClsmUserLogin dataLogin = new BLMain().getUserLogin(context);
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RepotLogError _repotLogError = new RepotLogError(context);
            List<ClstLogError> ListOfDataError = _repotLogError.getAllPushData();

            FileUpload = new HashMap<>();
            if (ListOfDataError!=null){
                dtPush.setListOfDatatLogError(ListOfDataError);
                for (ClstLogError data : ListOfDataError){
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


    public List<ClsToken> getDataToken(Context context){
        List<ClsToken> dtToken = null;
        try {
            RepoclsToken tokenRepo = new RepoclsToken(context);
            dtToken = (List<ClsToken>) tokenRepo.findAll();
            if (dtToken.size() == 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtToken;
    }

    public void volleyLogin(final Activity context, String strLinkAPI, final String mRequestBody, String progressBarType, final boolean isMustLogout, final InterfaceVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        final RepomConfig configRepo = new RepomConfig(context);
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
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
                String strLinkAPI = new ClsHardCode().linkToken;
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

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
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

                                    ClsToken data = new ClsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    RepoclsToken tokenRepo = new RepoclsToken(context);
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
                        new ActivitySplash().logout(context);
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

    public void volleyLoginCustom(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final InterfaceCustomVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        final RepomConfig configRepo = new RepomConfig(context);
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
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
                String strLinkAPI = new ClsHardCode().linkToken;
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

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
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

                                    ClsToken data = new ClsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    RepoclsToken tokenRepo = new RepoclsToken(context);
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

    public void volleyCheckVersion(final Context context, String strLinkAPI, final String mRequestBody, final AccountManager accountManager, String progressBarType, final InterfaceVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        RepomConfig configRepo = new RepomConfig(context);
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
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
                String strLinkAPI = new ClsHardCode().linkToken;
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

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
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

                                    ClsToken data = new ClsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    RepoclsToken tokenRepo = new RepoclsToken(context);
                                    tokenRepo.createOrUpdate(data);
//                                    ToastCustom.showToasty(context,"Success get new Access Token",1);
//                                    Toast.makeText(context, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        new ActivitySplash().checkVersion(context, accountManager);
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
                new ActivitySplash().checkVersion(context, accountManager);
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

    public void volleyDownloadData(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final InterfaceVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        RepomConfig configRepo = new RepomConfig(context);
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
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
                String strLinkAPI = new ClsHardCode().linkToken;
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

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
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

                                    ClsToken data = new ClsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    RepoclsToken tokenRepo = new RepoclsToken(context);
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

    public void volleyDownloadDataget(final Context context, String strLinkAPI, final String mRequestBody, String progressBarType, final InterfaceVolleyResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] body = new String[1];
        final String[] message = new String[1];
        final ProgressDialog Dialog = new ProgressDialog(context);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        RepomConfig configRepo = new RepomConfig(context);
        try {
            ClsmConfigData configDataClient = (ClsmConfigData) configRepo.findById(4);
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
                String strLinkAPI = new ClsHardCode().linkToken;
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

                    new VolleyUtils().requestTokenWithRefresh(context, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
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

                                    ClsToken data = new ClsToken();
                                    data.setIntId("1");
                                    data.setDtIssuedToken(dtIssued);
                                    data.setTxtUserToken(accessToken);
                                    data.setTxtRefreshToken(refreshToken);

                                    RepoclsToken tokenRepo = new RepoclsToken(context);
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

    public void volleyDownloadDataKLB(final Context context, String strLinkAPI, String progressBarType, final InterfaceVolleyResponseListener listener) {
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
                RepomConfig configRepo = new RepomConfig(context);
                ClsmConfigData UserName = null;
                ClsmConfigData Passoword = null;
                try {
                    UserName = (ClsmConfigData) configRepo.findById(9);
                    Passoword = (ClsmConfigData) configRepo.findById(10);
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


