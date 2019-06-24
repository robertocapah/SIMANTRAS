package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.kalbenutritionals.simantra.ActivityMainMenu;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.CustomView.Utils.CameraSelectorDialogFragment;
import com.kalbenutritionals.simantra.CustomView.Utils.FormatSelectorDialogFragment;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatIsianItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FullScannerFragment extends Fragment implements
        ZXingScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    String message = "";
    int statusLoading = 0;
    private Gson gson;
    Context context;
    Result rawResult;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mScannerView = new ZXingScannerView(getActivity());
        context = getActivity().getApplicationContext();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }
        if (getActivity().getIntent().getStringExtra(ClsHardCode.txtMessage)!=null ){
            message = getActivity().getIntent().getStringExtra(ClsHardCode.txtMessage);
            if (message.equals(ClsHardCode.txtBundleKeyBarcodeLoad)){
                statusLoading = getActivity().getIntent().getIntExtra(ClsHardCode.txtStatusLoading,0);
            }
        }
        setupFormats();
        return mScannerView;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem;

        if(mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);


        if(mAutoFocus) {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if(mFlash) {
                    item.setTitle(R.string.flash_on);
                } else {
                    item.setTitle(R.string.flash_off);
                }
                mScannerView.setFlash(mFlash);
                return true;
            case R.id.menu_auto_focus:
                mAutoFocus = !mAutoFocus;
                if(mAutoFocus) {
                    item.setTitle(R.string.auto_focus_on);
                } else {
                    item.setTitle(R.string.auto_focus_off);
                }
                mScannerView.setAutoFocus(mAutoFocus);
                return true;
            case R.id.menu_formats:
                DialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
                fragment.show(getActivity().getSupportFragmentManager(), "format_selector");
                return true;
            case R.id.menu_camera_selector:
                mScannerView.stopCamera();
                DialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
                cFragment.show(getActivity().getSupportFragmentManager(), "camera_selector");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public void handleResult(Result rawResult) {
        this.rawResult = rawResult;
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
//        mScannerView.resumeCameraPreview(this);
        if (message.equals(ClsHardCode.txtBundleKeyBarcode)){
            goToInfoChecker();
        }else{
            Intent i = new Intent(new Intent(getActivity(),ActivityMainMenu.class));
            i.putExtra(new ClsHardCode().txtMessage, ClsHardCode.txtBundleKeyBarcodeLoad);
            i.putExtra(new ClsHardCode().txtNoSPM, rawResult.getText());
            i.putExtra(new ClsHardCode().txtStatusLoading, statusLoading);
            getActivity().startActivity(i);
        }

//        showMessageDialog("Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString());
    }
    private void goToInfoChecker() {
       /* FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
        FragmentTransactions fragmentTransactionInfoChecker = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
        fragmentTransactionInfoChecker.commit();*/
        String txtLink = new ClsHardCode().linkGetListFormByOrg;
        final String noQr = "7094ecc8-493c-4122-b0d2-5de69fbea0f5";
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context,noQr);

        new FastNetworkingUtils().FNRequestPostData(getActivity(), txtLink, obj, "Processing SPM", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if(model.getResult()!=null){
                    if ( model.getResult().isStatus()){
                        new RepomPertanyaan(context).deleteAllData();
                        BLHelper.savePreference(context,"spm",noQr);
                        GenerateData(getActivity().getApplicationContext(),model);
                        Intent i = new Intent(new Intent(getActivity(),ActivityMainMenu.class));
                        i.putExtra(new ClsHardCode().txtBundleKeyBarcode, rawResult.getText());
                        i.putExtra(new ClsHardCode().txtNoSPM, rawResult.getText());
                        getActivity().startActivity(i);
                    }else{
                        Toast.makeText(context,model.getResult().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;
            }
        });
    }
    public void GenerateData(Context context, ResponseGetQuestion model){
        List<DataItem> datas =  model.getData();
        for (DataItem data :
                datas) {
            ClsmPertanyaan pertanyaan = new ClsmPertanyaan();
            pertanyaan.setIntPertanyaanId(data.getINTFORMDTLID());
            pertanyaan.setIntFillHeaderId(model.getINTFILLHDRID());
            pertanyaan.setIntJenisPertanyaanId(data.getINTTYPEID());
            pertanyaan.setTxtPertanyaan(data.getTXTFORMNAME());
            pertanyaan.setIntLocationDocsId(data.getINTPOSITIONID());
            pertanyaan.setIntSeq(Integer.parseInt(data.getINTSEQ()));
            pertanyaan.setIntValidateID(data.getINTVALIDATEID());
            pertanyaan.setTxtMapCol(data.getTXTMAPCOL());
            if (data.getBITIMG().equals("1")){
                pertanyaan.setBolHavePhoto(true);
                pertanyaan.setIntPhotoNeeded(Integer.parseInt(data.getINTIMGNEED()));
            }else{
                pertanyaan.setBolHavePhoto(false);
            }
            if(data.getBITDATA().equals("1")){
                pertanyaan.setBolHaveAnswer(true);
            }
            if (data.getListDatIsian()!= null){
                List<ListDatIsianItem> lisDataIsian = data.getListDatIsian();
                for (ListDatIsianItem jwb :
                        lisDataIsian) {
                    ClsmJawaban clsmJawaban = new ClsmJawaban();
                    clsmJawaban.setBitActive(true);
                    clsmJawaban.setTxtIdJawaban(jwb.getTXTDATADTLID());
                    clsmJawaban.setIdJawaban(jwb.getINTDATADTLID());
                    clsmJawaban.setIdPertanyaan(data.getINTFORMDTLID());
                    clsmJawaban.setBitChoosen(false);
                    clsmJawaban.setTxtMapCol(jwb.getTXTMAPCOL());
                    if(jwb.getTXTVALUE().equals("null")){
                        clsmJawaban.setTxtJawaban("");
                    }else{
                        clsmJawaban.setTxtJawaban(jwb.getTXTVALUE());
                    }
                    try{
                        new RepomJawaban(context).createOrUpdate(clsmJawaban);
                    }catch (Exception ex){
                        ex.getMessage();
                    }
                }

            }else{
                pertanyaan.setBolHaveAnswer(false);
            }
            try{
                new RepomPertanyaan(context).createOrUpdate(pertanyaan);
            }catch (Exception e){

            }
        }

    }
    public void showMessageDialog(String message) {
//        DialogFragment fragment = MessageDialogFragment.newInstance("Scan Results", message, this);
//        fragment.show(getActivity().getSupportFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }
}
