package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatIsianItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.FullScannerFragmentActivity;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FragmentSPMSearch extends Fragment implements ZXingScannerView.ResultHandler {
    View v;
    @BindView(R.id.etSpmNumber)
    EditText etSpmNumber;
    @BindView(R.id.btnGo)
    Button btnGo;
    Unbinder unbinder;
    @BindView(R.id.ivScanBarcode)
    ImageView ivScanBarcode;
    Context context;
    private Gson gson;
    int intStatus = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scan_barcode, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        //is there any way that I can stay in your eyes
        Bundle arguments = getArguments();
        intStatus = arguments.getInt(ClsHardCode.TXT_STATUS_MENU);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.etSpmNumber, R.id.btnGo, R.id.ivScanBarcode})
    public void onViewClicked(View view) {
        BLHelper.hideKeyboard(getActivity());
        switch (view.getId()) {
            case R.id.etSpmNumber:
                break;
            case R.id.btnGo:
                if(intStatus == ClsHardCode.INT_CHECKER){
                    goToInfoChecker();
                }else if (intStatus == ClsHardCode.INT_VALIDATOR){
                    goToValidatorUnLoading();
                }
                break;
            case R.id.ivScanBarcode:
                scanBarcode();
                break;
        }
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
                        FragmentTab fragmentTab = new FragmentTab();
                        FragmentTransaction fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                        fragmentTransactionTab.commit();
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
    private void goToValidatorUnLoading() {
       /* FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
        FragmentTransactions fragmentTransactionInfoChecker = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
        fragmentTransactionInfoChecker.commit();*/
        String txtLink = new ClsHardCode().linkGetListFormByOrg;
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context,"KNS17090047");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), txtLink, obj, "Processing SPM", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if(model.getResult()!=null){
                    if ( model.getResult().isStatus()){
//                        new RepomPertanyaan(context).deleteAllData();
//                        GenerateData(getActivity().getApplicationContext(),model);
                        FragmentValidator fragmentValidator = new FragmentValidator();
                        FragmentTransaction fragmentTransactionValidator = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransactionValidator.replace(R.id.frame, fragmentValidator);
                        fragmentTransactionValidator.commit();
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
    private void scanBarcode(){
//        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
        Intent intent = new Intent(getActivity(), FullScannerFragmentActivity.class);
        startActivity(intent);
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
                    clsmJawaban.setTxtJawaban(jwb.getTXTVALUE());
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
        /*ClsmPertanyaan pertanyaan = new ClsmPertanyaan();
        pertanyaan.setIntPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
        pertanyaan.setBolHaveAnswer(false);
        pertanyaan.setIntJenisPertanyaanId(1);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setTxtPertanyaan("Tuliskan beberapa hal di dalam textbox di bawah ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
        pertanyaan = new ClsmPertanyaan();
        pertanyaan.setBolHaveAnswer(true);
        pertanyaan.setIntPertanyaanId(2);
        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanCheckBox);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setTxtPertanyaan("pilih beberapa dari pertanyaan di checkbox di bawah ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
        pertanyaan = new ClsmPertanyaan();
        pertanyaan.setIntPertanyaanId(3);
        pertanyaan.setBolHaveAnswer(true);
        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanRadioButton);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setTxtPertanyaan("pilih satu dari beberapa di radioButton di bawah ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
        pertanyaan = new ClsmPertanyaan();
        pertanyaan.setIntPertanyaanId(4);
        pertanyaan.setBolHaveAnswer(true);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setTxtPertanyaan("isi textbox dan ambil sebuah gambar ya pak");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }
        pertanyaan = new ClsmPertanyaan();
        pertanyaan.setIntPertanyaanId(7);
        pertanyaan.setBolHaveAnswer(false);
        pertanyaan.setBolHavePhoto(true);
        pertanyaan.setIntJenisPertanyaanId(ClsHardCode.JenisPertanyaanTextBox);
        pertanyaan.setIntLocationDocsId(1);
        pertanyaan.setTxtPertanyaan("Tuliskan beberapa hal di dalam textbox di bawah ya pak (2)");
        try{
            new RepomPertanyaan(context).createOrUpdate(pertanyaan);
        }catch (Exception e){

        }

        ClsmJawaban clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(1);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja   1");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(2);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  2");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(3);
        clsmJawaban.setIdPertanyaan(2);
        clsmJawaban.setTxtJawaban("coba kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  3");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(4);
        clsmJawaban.setIdPertanyaan(3);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja 1");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(5);
        clsmJawaban.setIdPertanyaan(3);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  2");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
        clsmJawaban = new ClsmJawaban();
        clsmJawaban.setBitActive(true);
        clsmJawaban.setIdJawaban(6);
        clsmJawaban.setIdPertanyaan(3);
        clsmJawaban.setBitChoosen(false);
        clsmJawaban.setTxtJawaban("radio kjdhahsdka akjsdaksjdh ashdlakjsdha ashdakjakjdhahsdka akjsdaksjdh ashdlakjsdha ashdakja  3");
        try{
            new RepomJawaban(context).createOrUpdate(clsmJawaban);
        }catch (Exception ex){

        }
*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int a = 1;
        /*IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }*/
    }

    @Override
    public void handleResult(Result result) {
        int a = 1;
    }
}
