package com.kalbenutritionals.simantra.Fragment;

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

import com.google.zxing.Result;
import com.kalbenutritionals.simantra.FullScannerFragmentActivity;
import com.kalbenutritionals.simantra.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scan_barcode, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.etSpmNumber, R.id.btnGo, R.id.ivScanBarcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etSpmNumber:
                break;
            case R.id.btnGo:
                goToInfoChecker();
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
        FragmentTab fragmentTab = new FragmentTab();
        FragmentTransaction fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionTab.replace(R.id.frame, fragmentTab);
        fragmentTransactionTab.commit();
    }
    private void scanBarcode(){
//        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
        Intent intent = new Intent(getActivity(), FullScannerFragmentActivity.class);
        startActivity(intent);
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
