package shp.template.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import shp.template.BL.clsMainBL;
import shp.template.Common.mUserLogin;
import shp.template.Common.tProgramVisitSubActivity;
import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.clsHardCode;
import shp.template.MainMenu;
import shp.template.R;
import shp.template.Repo.tProgramVisitSubActivityRepo;
import shp.template.Repo.tRealisasiVisitPlanRepo;
import shp.template.Utils.IOBackPressed;
import shp.template.Utils.Tools;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.Maps.PopUpMaps;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Dewi Oktaviani on 10/3/2018.
 */

public class FragmentCallPlan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, IOBackPressed{
    View v;
    private Button btnCheckin, btnViewMap, btnRefreshMap;
    private ImageView imgCamera1, imgCamera2;
    private TextView tvLongUser, tvLongOutlet, tvLatUser, tvLatOutlet,tvAcc, tvOutlet;
    private EditText etDesc, etBranch, etDate, etOutlet;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private double mlongitude = 0;
    private double mlatitude = 0;
    Options options;
    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE2_REQUEST_CODE = 130;
    private static final String IMAGE_DIRECTORY_NAME = "Image Activity";
    private String DT_CALL_PLAN = "dtCallPlan";
    Bundle dataHeader;
    private String fileName;
    private Toolbar toolbar;
//    clsListItemAdapter dtTesting;
    tRealisasiVisitPlan dtTemp;
    tRealisasiVisitPlanRepo realisasiVisitPlanRepo;
    tRealisasiVisitPlan dtRealisasiVisit;
    tProgramVisitSubActivity dtVisitPlan;
    tProgramVisitSubActivityRepo visitPlanRepo;
    CardView cvImgview;
    private String DT_HISTORY = "dari history";
    boolean valid = false;
    private String FRAG_VIEW = "Fragment view";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.call_plan_fragment, container, false);

        btnCheckin = (Button) v.findViewById(R.id.buttonCheckIn);
        btnViewMap = (Button) v.findViewById(R.id.btnViewMap);
        btnRefreshMap = (Button) v.findViewById(R.id.btnRefreshMaps);
        imgCamera1 = (ImageView) v.findViewById(R.id.imageViewCamera1);
        imgCamera2 = (ImageView) v.findViewById(R.id.imageViewCamera2);
        tvLongUser = (TextView) v.findViewById(R.id.tvLong);
        tvLatUser = (TextView) v.findViewById(R.id.tvLat);
        tvLongOutlet = (TextView) v.findViewById(R.id.tvLongOutlet);
        tvLatOutlet = (TextView) v.findViewById(R.id.tvlatOutlet);
        tvAcc = (TextView) v.findViewById(R.id.tvAcc);
        tvOutlet = (TextView)v.findViewById(R.id.tvOutlet);
//        tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        etBranch = (EditText) v.findViewById(R.id.etBranch);
        etOutlet = (EditText) v.findViewById(R.id.etOutlet);
        etDesc = (EditText) v.findViewById(R.id.etDesc);
        etDate = (EditText) v.findViewById(R.id.etDate);
        cvImgview = (CardView)v.findViewById(R.id.llimgview);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        realisasiVisitPlanRepo = new tRealisasiVisitPlanRepo(getContext());
        visitPlanRepo = new tProgramVisitSubActivityRepo(getContext());
        dtTemp = new tRealisasiVisitPlan();
        dataHeader = getArguments();
        if (dataHeader!=null){
            try {
                dtVisitPlan = (tProgramVisitSubActivity) visitPlanRepo.findBytxtId(dataHeader.getString(DT_CALL_PLAN));
//                dtRealisasiVisit = (tRealisasiVisitPlan) realisasiVisitPlanRepo.findBytxtId(dataHeader.getString(DT_CALL_PLAN));
                if (dtVisitPlan!=null){
                    dtRealisasiVisit = (tRealisasiVisitPlan) realisasiVisitPlanRepo.findBytxtPlanId(dtVisitPlan.getTxtProgramVisitSubActivityId());
                    if (dataHeader.getString(DT_HISTORY)!=null){
                        valid = true;
                    }

//                   dtVisitPlan = (tProgramVisitSubActivity) visitPlanRepo.findBytxtId(dtRealisasiVisit.getTxtProgramVisitSubActivityId());
                   if (dtVisitPlan.getIntActivityId()==1){
                       tvOutlet.setText("Doctor Name  : ");
                       etOutlet.setText(dtVisitPlan.getTxtDokterName());
                   }else if (dtVisitPlan.getIntActivityId()==2){
                       tvOutlet.setText("Pharmacy Name  : ");
                       etOutlet.setText(dtVisitPlan.getTxtApotekName());
                   }else {
                       tvOutlet.setVisibility(View.GONE);
                       etOutlet.setVisibility(View.GONE);
                       btnCheckin.setText("REALIZATION");
                       cvImgview.setVisibility(View.GONE);
                   }
                    etDate.setText(parseDate(dtVisitPlan.getDtStart()));
                    etDesc.setText(dtVisitPlan.getTxtNotes());
                    etBranch.setText(dtVisitPlan.getTxtAreaName());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        options = new Options();
        options.inSampleSize = 2;

        if (checkPlayServices()){
            buildGoogleApiClient();
        }
        if (valid){
            btnCheckin.setVisibility(View.GONE);
            btnRefreshMap.setVisibility(View.GONE);
            imgCamera1.setEnabled(false);
            imgCamera2.setEnabled(false);
            if (dtRealisasiVisit.getBlobImg1()!=null){
                Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtRealisasiVisit.getBlobImg1());
                new PickImage().previewCapturedImage(imgCamera1, bitmap, 150, 150);
            }

            if (dtRealisasiVisit.getBlobImg2()!=null){
                Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtRealisasiVisit.getBlobImg2());
                new PickImage().previewCapturedImage(imgCamera2, bitmap, 150, 150);
            }

            tvLongUser.setText(dtRealisasiVisit.getTxtLong());
            tvLatUser.setText(dtRealisasiVisit.getTxtLat());
            tvAcc.setText(dtRealisasiVisit.getTxtAcc());
        }else {
            tvLongUser.setText("");
            tvLatUser.setText("");
            tvAcc.setText("");
            tvLongOutlet.setText("");
            tvLatOutlet.setText("");
            getLocation();

            if (mLastLocation!=null){
                displayLocation(mLastLocation);
            }

        }


        btnRefreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                if (mLastLocation == null){
                    displayLocation(mLastLocation);
                }
            }
        });
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new PopUpMaps().popUpMapsTwoCoordinates(getContext(), R.layout.popup_map, tvLatOutlet.getText().toString(), tvLongOutlet.getText().toString());
                btnViewMap.setEnabled(false);
                new PopUpMaps().popUpMapsCustom(getActivity(), R.layout.popup_map, tvLatUser.getText().toString(), tvLongUser.getText().toString(), btnViewMap);


            }
        });


        imgCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                fileName = "temp_absen" + timeStamp;
                new PickImage().CaptureImage(getActivity(), new clsHardCode().txtFolderCheckIn, fileName,CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
            }
        });
        imgCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                fileName = "temp_absen" + timeStamp;
                new PickImage().CaptureImage(getActivity(), new clsHardCode().txtFolderCheckIn, fileName,CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
            }
        });

        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure?");

                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (dtVisitPlan.getIntActivityId()==1||dtVisitPlan.getIntActivityId()==2){
                            saveDataVisit();
                        }else {
                            saveData();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return v;
    }

    private void saveDataVisit(){
        boolean valid = false;
        if (dtTemp==null){
            valid = false;
            new ToastCustom().showToasty(getContext(),"Please take at least 1 photo",4);
        }else if (dtTemp.getBlobImg1()==null && dtTemp.getBlobImg2()==null){
            new ToastCustom().showToasty(getContext(),"Please take at least 1 photo",4);
        }else if (tvLatUser.getText().toString().equals("")&&tvLongUser.getText().toString().equals("")&&tvAcc.getText().toString().equals("")){
            valid = false;
            new ToastCustom().showToasty(getContext(),"Failed checkin: Location not found, please check your GPS",4);
        }else if (tvLatUser.getText()==null && tvLongUser.getText()==null&&tvAcc.getText()==null){
            new ToastCustom().showToasty(getContext(),"Failed checkin: Location not found, please check your GPS",4);
        } else {
            try {
                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                data.setTxtRealisasiVisitId(dtRealisasiVisit.getTxtRealisasiVisitId());
                data.setTxtProgramVisitSubActivityId(dtRealisasiVisit.getTxtProgramVisitSubActivityId());
                data.setIntUserId(dtRealisasiVisit.getIntUserId());
                data.setIntRoleID(dtRealisasiVisit.getIntRoleID());
                data.setTxtDokterId(dtRealisasiVisit.getTxtDokterId());
                data.setTxtDokterName(dtRealisasiVisit.getTxtDokterName());
                data.setTxtApotekId(dtRealisasiVisit.getTxtApotekId());
                data.setTxtApotekName(dtRealisasiVisit.getTxtApotekName());
                data.setDtCheckIn(dateTimeFormat.format(cal.getTime()));
                data.setDtCheckOut(null);
                data.setDtDateRealisasi(null); ///tanggal login
                data.setDtDatePlan(dtRealisasiVisit.getDtDatePlan());
                data.setIntNumberRealisasi(dtRealisasiVisit.getIntNumberRealisasi()); //generate number
                data.setTxtAcc(tvAcc.getText().toString());
                data.setTxtLat(tvLatUser.getText().toString());
                data.setTxtLong(tvLongUser.getText().toString());
                data.setTxtImgName1(dtTemp.getTxtImgName1());
                data.setBlobImg1(dtTemp.getBlobImg1());
                data.setTxtImgName2(dtTemp.getTxtImgName2());
                data.setBlobImg2(dtTemp.getBlobImg2());
                data.setIntStatusRealisasi(new clsHardCode().VisitPlan);
                data.setIntFlagPush(new clsHardCode().Save);
                realisasiVisitPlanRepo.createOrUpdate(data);

                tProgramVisitSubActivity dtVisit = dtVisitPlan;
                dtVisit.setIntFlagPush(new clsHardCode().Save);
                visitPlanRepo.createOrUpdate(dtVisit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new ToastCustom().showToasty(getContext(),"Submit",1);
//            Tools.intentFragment(HomeFragment.class, "Home", getContext());
            Intent myIntent = new Intent(getContext(), MainMenu.class);
            getActivity().finish();
            startActivity(myIntent);
        }


    }

    private void saveData(){
        if (tvLatUser.getText().toString().equals("")&&tvLongUser.getText().toString().equals("")){
            new ToastCustom().showToasty(getContext(),"Failed realization: Location not found, please check your GPS",4);
        }else if (tvLatUser.getText()==null && tvLongUser.getText()==null){
            new ToastCustom().showToasty(getContext(),"Failed realization: Location not found, please check your GPS",4);
        } else {
            try {
                mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());
                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                data.setTxtRealisasiVisitId(dtRealisasiVisit.getTxtRealisasiVisitId());
                data.setTxtProgramVisitSubActivityId(dtRealisasiVisit.getTxtProgramVisitSubActivityId());
                data.setIntUserId(dtRealisasiVisit.getIntUserId());
                data.setIntRoleID(dtRealisasiVisit.getIntRoleID());
                data.setTxtDokterId(dtRealisasiVisit.getTxtDokterId());
                data.setTxtDokterName(dtRealisasiVisit.getTxtDokterName());
                data.setTxtApotekId(dtRealisasiVisit.getTxtApotekId());
                data.setTxtApotekName(dtRealisasiVisit.getTxtApotekName());
                data.setDtCheckIn(dateTimeFormat.format(cal.getTime()));
                data.setDtCheckOut(dateTimeFormat.format(cal.getTime()));
                data.setDtDateRealisasi(dateFormat.format(dateTimeFormat.parse(dtLogin.getDtLogIn()))); ///tanggal login
                data.setDtDatePlan(dtRealisasiVisit.getDtDatePlan());
                data.setIntNumberRealisasi(dtRealisasiVisit.getIntNumberRealisasi()); //generate number
                data.setTxtAcc(tvAcc.getText().toString());
                data.setTxtLat(tvLatUser.getText().toString());
                data.setTxtLong(tvLongUser.getText().toString());
                data.setTxtImgName1(null);
                data.setBlobImg1(null);
                data.setTxtImgName2(null);
                data.setBlobImg2(null);
                data.setIntStatusRealisasi(new clsHardCode().Realisasi);
                data.setIntFlagPush(new clsHardCode().Save);
                realisasiVisitPlanRepo.createOrUpdate(data);

                tProgramVisitSubActivity dtVisit = dtVisitPlan;
                dtVisit.setIntFlagPush(new clsHardCode().Save);
                visitPlanRepo.createOrUpdate(dtVisit);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new ToastCustom().showToasty(getContext(),"Submit",1);
            Intent myIntent = new Intent(getContext(), MainMenu.class);
            getActivity().finish();
            startActivity(myIntent);
        }


    }
    private String parseDateTime(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
    }

    private String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_CAPTURE_IMAGE1_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = new UriData().getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderCheckIn, fileName);
                //untuk mendapatkan bitmap bisa menggunakan decode stream
                Bitmap bitmap = new PickImage().decodeStreamReturnBitmap(getContext(), uri);
                //get byte array
                byte[] save = new PickImage().getByteImageToSaveRotate(getContext(), uri);
                dtTemp.setBlobImg1(save);
                dtTemp.setTxtImgName1(fileName);
                Bitmap bitmap1 = null;
                try {
                    bitmap1 = new PickImage().rotateBitmap(bitmap, new PickImage().Orientation(getContext(), uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Glide.with(this).load(uri).into(imgCamera1);
//                Bitmap bitmap1 = new PickImage().decodeByteArrayReturnBitmap(save);
                new PickImage().previewCapturedImage(imgCamera1, bitmap1, 150, 150);
            }else if (resultCode == 0) {
               new clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
              new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }else if (requestCode== CAMERA_CAPTURE_IMAGE2_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = new UriData().getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderCheckIn, fileName);
                //untuk mendapatkan bitmap bisa menggunakan decode stream
                Bitmap bitmap = new PickImage().decodeStreamReturnBitmap(getContext(), uri);
                //get byte array
                byte[] save = new PickImage().getByteImageToSaveRotate(getContext(), uri);
                Bitmap bitmap1 = null;
                try {
                    bitmap1 = new PickImage().rotateBitmap(bitmap, new PickImage().Orientation(getContext(), uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dtTemp.setBlobImg2(save);
                dtTemp.setTxtImgName2(fileName);
                new PickImage().previewCapturedImage(imgCamera2, bitmap1, 150, 150);
            }else if (resultCode == 0) {
                new clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
                new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }

    }

    // get location GPS
    private boolean earlyState = true;
    boolean mockStatus = false;
    public Location getLocation() {
        try {
//            Drawable icon = getResources().getDrawable(R.mipmap.ic_error_outline);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                new ToastCustom().showToasty(getContext(),"Please turn on GPS or check your internet connection",4);
//                new clsMainActivity().showCustomToast(getContext(), "Please turn on GPS or check your internet connection", false);
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        new ToastCustom().showToasty(getContext(),"Please check application permissions",4);
//                        _clsMainActivity.showCustomToast(getContext(), "Please check application permissions", false);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                        mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                } else {
                    new ToastCustom().showToasty(getContext(),"Please check your connection",4);
//                    _clsMainActivity.showCustomToast(getContext(), "Please check your connection", false);
                }

                if (isGPSEnabled && mLastLocation==null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                    mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if(!isGPSEnabled){
                    new ToastCustom().showToasty(getContext(),"Please check your connection",4);
//                    _clsMainActivity.showCustomToast(getContext(), "Please check your connection", false);
                }
            }

            if (mLastLocation != null) {
                int intOs = Integer.valueOf(Build.VERSION.SDK);
                if (intOs >= 18) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        mockStatus = mLastLocation.isFromMockProvider();
                    }
                }
            }
            if (mockStatus){
                new ToastCustom().showToastyCustom(getContext(), Html.fromHtml("<b>" + "Fake GPS detected ! " + "</b> "+ "<br/>" + "<br/>" +"Please Turn Off Fake Location, And Restart Your Phone"), getResources().getDrawable(R.mipmap.ic_error_outline), getResources().getColor(R.color.red_600), 10, false, true);
//                ToastCustom.showToasty(getContext(),"Fake GPS detected ! " + "\n" + "Please Turn Off Fake Location, And Restart Your Phone",2);
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(5000);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mLastLocation!=null&&!earlyState){
            new clsMainActivity().showCustomToast(getContext(), "Location Updated", true);
        }
        earlyState = false;
        return mLastLocation;
    }


    @SuppressWarnings("deprecation")
    //set text view long lat
    private void displayLocation(Location mLastLocation) {
        DecimalFormat df = new DecimalFormat("#.##");

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            double accurate = mLastLocation.getAccuracy();

            tvLongUser.setText(String.format("%s", longitude));
            tvLatUser.setText(String.format("%s", latitude));
            tvAcc.setText(String.format("%s", df.format(accurate)));

//            try {
//                float distance = countDistance(latitude, longitude);
//                tvDistance.setText(String.format("%s meters", String.valueOf((int) Math.ceil(distance))));
//            } catch (Exception ignored) {
//
//            }

            mlongitude = longitude;
            mlatitude = latitude;

        } else {
            tvLatUser.setText("");
            tvLongUser.setText("");
            tvAcc.setText("");
//            tvDistance.setText("");
        }

    }

    // count distance
//    private float countDistance(double latitude, double longitude) {
//        float distance;
//
////        double latitudeOutlet = Double.parseDouble(HMoutletLat.get(spnOutlet.getSelectedItem().toString()));
////        double longitudeOutlet = Double.parseDouble(HMoutletLang.get(spnOutlet.getSelectedItem().toString()));
//
//        double latitudeOutlet = Double.parseDouble(tvLatOutlet.getText().toString());
//        double longitudeOutlet = Double.parseDouble(tvLongOutlet.getText().toString());
//
//        Location locationA = new Location("point user");
//
//        locationA.setLatitude(latitude);
//        locationA.setLongitude(longitude);
//
//        Location locationB = new Location("point outlet");
//
//        locationB.setLatitude(latitudeOutlet);
//        locationB.setLongitude(longitudeOutlet);
//
//        distance = locationA.distanceTo(locationB);
//
//        tvDistance.setText(String.format("%s meters", String.valueOf((int) Math.ceil(distance))));
//
//        return distance;
//    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @SuppressWarnings("deprecation")
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation(mLastLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onBackPressed() {
//        if (valid){
//            new Tools().intentFragment(FragmentHistory.class, "History", getContext());
//            return true;
//        }else {
//
//        }

        Bundle bundle = new Bundle();
        if (valid){
            bundle.putString(FRAG_VIEW, "Realisasi");
            new Tools().intentFragmentSetArgument(FragmentHeaderCallPlan.class, "Call Plan", getContext(), bundle);
            return true;
        }else {
            bundle.putString(FRAG_VIEW, "Plan");
            new Tools().intentFragmentSetArgument(FragmentHeaderCallPlan.class, "Call Plan", getContext(), bundle);
            return true;
        }
    }
}
