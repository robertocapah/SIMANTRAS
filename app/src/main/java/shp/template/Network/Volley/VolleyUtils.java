package shp.template.Network.Volley;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import shp.template.Database.Common.ClsPushData;
import shp.template.Database.Common.ClsToken;
import shp.template.Database.Common.ClsmConfigData;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Data.ClsHardCode;
import shp.template.Database.Repo.RepoclsToken;
import shp.template.Database.Repo.RepomConfig;
import shp.template.ActivitySplash;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert on 2/12/2019.
 */

public class VolleyUtils {
    String access_token,clientId = "";
    List<ClsToken> dataToken;
    public void makeJsonObjectRequestToken(final Context activity, String strLinkAPI, final String username, final String password, final String clientId, String progressBarType, final InterfaceVolleyResponseListener listener) {
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
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
                String body, message;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("Message");
                        Toast.makeText(activity.getApplicationContext(), "Error 401 " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    popup();
                    finalDialog1.dismiss();
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("error_description");
                        Toast.makeText(activity.getApplicationContext(), "Error 400, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    popup();
                    finalDialog1.dismiss();
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR ){
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                    popup();
                    finalDialog1.dismiss();
                } else {
                    popup();
                    finalDialog1.dismiss();
                }
            }
            public void popup() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

                builder.setTitle("Request Time Out");
                builder.setMessage("You Have to request again");

                builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new ActivitySplash().requestToken(activity);
                        dialog.dismiss();
                    }
                });

//                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });

                android.app.AlertDialog alert = builder.create();
                alert.show();

                alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("username", username);
                params.put("password", password);
                params.put("client_id", clientId);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }

    public void requestTokenWithRefresh(final Context activity, String strLinkAPI, final String refreshToken, final String clientId, final InterfaceVolleyResponseListener listener) {
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body, message;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("Message");
                        Toast.makeText(activity.getApplicationContext(), "Error 401, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.optString("error_description");
                        if (message.equals("")) {
                            message = jsonObject.optString("error");
                        }
                        Toast.makeText(activity.getApplicationContext(), "Error 400, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR ){
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Something Error, please request again", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    ClsmConfigData configDataUser = (ClsmConfigData) new RepomConfig(activity.getApplicationContext()).findById(5);
                    ClsmConfigData configDataClient = (ClsmConfigData) new RepomConfig(activity.getApplicationContext()).findById(4);
                    params.put("grant_type", "password");
                    params.put("client_id", configDataClient.getTxtDefaultValue().toString());
                    params.put("refresh_token", refreshToken);
                    params.put("username", configDataUser.getTxtDefaultValue().toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                params.put("grant_type", "refresh_token");
//                params.put("client_id", clientId);
//                params.put("refresh_token", refreshToken);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }
    public void requestTokenWithRefresh2(final Context activity, String strLinkAPI, final String username, final String refreshToken, final String clientId, final InterfaceVolleyResponseListener listener) {
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body, message;
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.getString("Message");
                        Toast.makeText(activity.getApplicationContext(), "Error 401, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        JSONObject jsonObject = new JSONObject(body);
                        message = jsonObject.optString("error_description");
                        if (message.equals("")) {
                            message = jsonObject.optString("error");
                        }
                        Toast.makeText(activity.getApplicationContext(), "Error 400, " + message, Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR ){
                    Toast.makeText(activity.getApplicationContext(), "Error 500, Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Something Error, please request again", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("client_id", clientId);
                params.put("refresh_token", refreshToken);
                params.put("username", username);

                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }
    public void makeJsonObjectRequestPushData(final Context ctx, String strLinkAPI, final ClsPushData mRequestBody, final ProgressDialog pDialog, final InterfaceVolleyResponseListener listener) {
        final String[] body = new String[1];
        final String[] message = new String[1];

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
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
                    RepomConfig configRepo = new RepomConfig(ctx);
                    ClsmConfigData configDataClient = null;
                    ClsmConfigData configDataUser = null;
                    try {
                        configDataClient = (ClsmConfigData) configRepo.findById(4);
                        configDataUser = (ClsmConfigData) configRepo.findById(5);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    String username = configDataUser.getTxtDefaultValue().toString();
                    clientId = configDataClient.getTxtDefaultValue().toString();
//                    Activity activity = (Activity)ctx;
                    new VolleyUtils().requestTokenWithRefresh2(ctx, strLinkAPI, username, refresh_token, clientId, new InterfaceVolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            new ToastCustom().showToasty(ctx,message,4);
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

                                    RepoclsToken tokenRepo = new RepoclsToken(ctx);
                                    tokenRepo.createOrUpdate(data);
//                                    Toast.makeText(ctx, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(ctx, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    pDialog.dismiss();

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
                    new ToastCustom().showToasty(ctx,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
//                error.printStackTrace();
//                NetworkResponse networkResponse = error.networkResponse;
////                int a = networkResponse.statusCode;
//                if (networkResponse==null){
//                    ToastCustom.showToasty(ctx,"Please check your connection...",4);
//                    pDialog.dismiss();
//                }else {
//                    listener.onError(error.getMessage());
//                    try {
//                        String responseBody = new String( error.networkResponse.data, "utf-8" );
//                        JSONObject jsonObject = new JSONObject( responseBody );
//                    } catch ( JSONException e ) {
//                        //Handle a malformed json response
//                        String b = "hasd";
//                    } catch (UnsupportedEncodingException e){
//                        String c = "hasd";
//                    }
//                    pDialog.dismiss();
//                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
//                access_token = "BRIVeCejVsSyXviEg56KyrqRl3ZjhrK7qanAeIEsJGJYWQhjhTVk-DHV7Mlsbdsx3ddSPB-zxBmRpoIynoA7tU2rU5qnmgT6-4aGjdF5XS__rVPcZDdqyTRIFSbW9CkAMX476bCdUZwnzr_5uCocTPgpPupl-ppyJ2GRm2n3rzNDDlgxYlS4raRDBUSwl_Bdicy9OfDr2Idci-5Kfnx5yYUOGUxGh6msTpP9fFpc4WkJR2CdLWNsZgcZRYhZBjNhx9TOwgki1LXFdVzbpEy1u_7FyQ3bJuKCo6k3rwg-i21IOF0BjXJYVhluFLpAkZQW81NyJfRYMlAeUAFMQcc_PS8zbmfuMIm-EJi_qj2Y_mJogttj-8sn7Vd-qLLJKnHU";
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access category real path
                // for now just get bitmap data category ImageView
                if (mRequestBody.getFileName()!=null){
                    if (mRequestBody.getFileName().size()>0){
                        for (int i = 0; i< mRequestBody.getFileName().size(); i++){
                            String fileName = mRequestBody.getFileName().get(i).toString();
                            params.put(fileName, new DataPart(fileName + ".jpg", mRequestBody.getFileUpload().get(fileName),"image/jpeg"));
//                            params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                        }

                    }
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(120000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }
    public void makeJsonObjectRequestPushDataBackground(final Context ctx, String strLinkAPI, final ClsPushData mRequestBody, final InterfaceVolleyResponseListener listener) {
//        strLinkAPI =  strLinkAPI+"?txtParam=\"test\"";
        RequestQueue queue = null;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                NetworkResponse networkResponse = error.networkResponse;
//                int a = networkResponse.statusCode;
                if (networkResponse==null){
//                    ToastCustom.showToasty(ctx,"Please check your connection...",4);
                }else {
                    listener.onError(error.getMessage());
                    try {
                        String responseBody = new String( error.networkResponse.data, "utf-8" );
                        JSONObject jsonObject = new JSONObject( responseBody );
                    } catch ( JSONException e ) {
                        //Handle a malformed json response
                        String b = "hasd";
                    } catch (UnsupportedEncodingException e){
                        String c = "hasd";
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
//                access_token = "BRIVeCejVsSyXviEg56KyrqRl3ZjhrK7qanAeIEsJGJYWQhjhTVk-DHV7Mlsbdsx3ddSPB-zxBmRpoIynoA7tU2rU5qnmgT6-4aGjdF5XS__rVPcZDdqyTRIFSbW9CkAMX476bCdUZwnzr_5uCocTPgpPupl-ppyJ2GRm2n3rzNDDlgxYlS4raRDBUSwl_Bdicy9OfDr2Idci-5Kfnx5yYUOGUxGh6msTpP9fFpc4WkJR2CdLWNsZgcZRYhZBjNhx9TOwgki1LXFdVzbpEy1u_7FyQ3bJuKCo6k3rwg-i21IOF0BjXJYVhluFLpAkZQW81NyJfRYMlAeUAFMQcc_PS8zbmfuMIm-EJi_qj2Y_mJogttj-8sn7Vd-qLLJKnHU";
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access category real path
                // for now just get bitmap data category ImageView
                if (mRequestBody.getFileName()!=null){
                    if (mRequestBody.getFileName().size()>0){
                        for (int i = 0; i< mRequestBody.getFileName().size(); i++){
                            String fileName = mRequestBody.getFileName().get(i).toString();
                            params.put(fileName, new DataPart(fileName + ".jpg", mRequestBody.getFileUpload().get(fileName),"image/jpeg"));
                        }

                    }
                }

                return params;
            }
        };
        //testing
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //default
//        multipartRequest.setRetryPolicy(new
//                DefaultRetryPolicy(50000,
//                0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        multipartRequest.setShouldCache(false);
        if (queue == null) {
         queue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        queue.add(multipartRequest);
    }
    public void changeProfile(final Context ctx, String strLinkAPI, final String mRequestBody, final ProgressDialog progressDialog, final ClsmUserLogin dtLogin, final InterfaceVolleyResponseListener listener) {
//        strLinkAPI =  strLinkAPI+"?txtParam=\"test\"";
        final String[] body = new String[1];
        final String[] message = new String[1];

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
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

                    new VolleyUtils().requestTokenWithRefresh(ctx, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            new ToastCustom().showToasty(ctx,message,4);
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

                                    RepoclsToken tokenRepo = new RepoclsToken(ctx);
                                    tokenRepo.createOrUpdate(data);
                                    Toast.makeText(ctx, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(ctx, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    progressDialog.dismiss();

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
                    new ToastCustom().showToasty(ctx,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
//                error.printStackTrace();
//                NetworkResponse networkResponse = error.networkResponse;
//                String msg = "";
////                int a = networkResponse.statusCode;
//                if (networkResponse==null){
//                    if (error instanceof NetworkError) {
//                        msg = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof ServerError) {
//                        msg = "The server could not be found. Please try again after some time!!";
//                    } else if (error instanceof AuthFailureError) {
//                        msg = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof ParseError) {
//                        msg = "Parsing error! Please try again after some time!!";
//                    } else if (error instanceof NoConnectionError) {
//                        msg = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof TimeoutError) {
//                        msg = "Connection TimeOut! Please check your internet connection.";
//                    } else {
//                        msg = "Error 500, Server Error";
//                    }
//
//                    if (msg!=null||!msg.equals("")){
//                        ToastCustom.showToasty(ctx,msg,4);
//                        progressDialog.dismiss();
//                    }
////                    ToastCustom.showToasty(ctx,"Please check your connection...",4);
//                }else {
//                    listener.onError(error.getMessage());
//                    try {
//                        String responseBody = new String( error.networkResponse.data, "utf-8" );
//                        JSONObject jsonObject = new JSONObject( responseBody );
//                    } catch ( JSONException e ) {
//                        //Handle a malformed json response
//                        String b = "hasd";
//                    } catch (UnsupportedEncodingException e){
//                        String c = "hasd";
//                    }
//                    progressDialog.dismiss();
//                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
//                access_token = "BRIVeCejVsSyXviEg56KyrqRl3ZjhrK7qanAeIEsJGJYWQhjhTVk-DHV7Mlsbdsx3ddSPB-zxBmRpoIynoA7tU2rU5qnmgT6-4aGjdF5XS__rVPcZDdqyTRIFSbW9CkAMX476bCdUZwnzr_5uCocTPgpPupl-ppyJ2GRm2n3rzNDDlgxYlS4raRDBUSwl_Bdicy9OfDr2Idci-5Kfnx5yYUOGUxGh6msTpP9fFpc4WkJR2CdLWNsZgcZRYhZBjNhx9TOwgki1LXFdVzbpEy1u_7FyQ3bJuKCo6k3rwg-i21IOF0BjXJYVhluFLpAkZQW81NyJfRYMlAeUAFMQcc_PS8zbmfuMIm-EJi_qj2Y_mJogttj-8sn7Vd-qLLJKnHU";
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("txtParam", mRequestBody);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access category real path
                // for now just get bitmap data category ImageView
                if (dtLogin.getBlobImg()!=null){
                    params.put(String.valueOf(dtLogin.getIntUserID()), new DataPart(String.valueOf(dtLogin.getIntUserID()) + ".jpg", dtLogin.getBlobImg(), "image/jpeg"));
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }
    public void makeJsonObjectRequestPushError(final Context ctx, String strLinkAPI, final ClsPushData mRequestBody, final ProgressDialog pDialog, final InterfaceVolleyResponseListener listener) {
        final String[] body = new String[1];
        final String[] message = new String[1];

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
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

                    new VolleyUtils().requestTokenWithRefresh(ctx, strLinkAPI, refresh_token, clientId, new InterfaceVolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            new ToastCustom().showToasty(ctx,message,4);
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

                                    RepoclsToken tokenRepo = new RepoclsToken(ctx);
                                    tokenRepo.createOrUpdate(data);
//                                    Toast.makeText(ctx, "Success get new Access Token", Toast.LENGTH_SHORT).show();
                                    newRefreshToken = refreshToken;
                                    if (refreshToken == newRefreshToken && !newRefreshToken.equals("")) {
                                        Toast.makeText(ctx, "Please press the button again", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    pDialog.dismiss();

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
                    new ToastCustom().showToasty(ctx,msg,4);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }

//                error.printStackTrace();
//                NetworkResponse networkResponse = error.networkResponse;
////                int a = networkResponse.statusCode;
//                if (networkResponse==null){
//                    ToastCustom.showToasty(ctx,"Please check your connection...",4);
//                    pDialog.dismiss();
//                }else {
//                    listener.onError(error.getMessage());
//                    try {
//                        String responseBody = new String( error.networkResponse.data, "utf-8" );
//                        JSONObject jsonObject = new JSONObject( responseBody );
//                    } catch ( JSONException e ) {
//                        //Handle a malformed json response
//                        String b = "hasd";
//                    } catch (UnsupportedEncodingException e){
//                        String c = "hasd";
//                    }
//                    pDialog.dismiss();
//                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                dataToken = getDataToken(ctx);
                access_token = dataToken.get(0).getTxtUserToken();
//                access_token = "BRIVeCejVsSyXviEg56KyrqRl3ZjhrK7qanAeIEsJGJYWQhjhTVk-DHV7Mlsbdsx3ddSPB-zxBmRpoIynoA7tU2rU5qnmgT6-4aGjdF5XS__rVPcZDdqyTRIFSbW9CkAMX476bCdUZwnzr_5uCocTPgpPupl-ppyJ2GRm2n3rzNDDlgxYlS4raRDBUSwl_Bdicy9OfDr2Idci-5Kfnx5yYUOGUxGh6msTpP9fFpc4WkJR2CdLWNsZgcZRYhZBjNhx9TOwgki1LXFdVzbpEy1u_7FyQ3bJuKCo6k3rwg-i21IOF0BjXJYVhluFLpAkZQW81NyJfRYMlAeUAFMQcc_PS8zbmfuMIm-EJi_qj2Y_mJogttj-8sn7Vd-qLLJKnHU";
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + access_token);

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("txtParam", mRequestBody.getDataError().txtJSON().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access category real path
                // for now just get bitmap data category ImageView
                if (mRequestBody.getFileName()!=null){
                    if (mRequestBody.getFileName().size()>0){
                        for (int i = 0; i< mRequestBody.getFileName().size(); i++){
                            String fileName = mRequestBody.getFileName().get(i).toString();
                            params.put(fileName, new DataPart(fileName + ".jpg", mRequestBody.getFileUpload().get(fileName),"image/jpeg"));
//                            params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                        }

                    }
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
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

        android.app.AlertDialog alert = builder.create();
        alert.show();

        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
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

}
