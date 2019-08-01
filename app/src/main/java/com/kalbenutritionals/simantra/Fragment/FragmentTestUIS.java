package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbenutritionals.simantra.R;

public class FragmentTestUIS extends Fragment {

    public FragmentTestUIS() {
    }

    public static FragmentTestUIS newInstance() {
        FragmentTestUIS fragmentTestUI = new FragmentTestUIS();
        return fragmentTestUI;
    }

    View v;
    String FRAGMENT_TAG = "";
    private View line_first, line_second;
    private ImageView image_shipping, image_payment, image_confirm;
    private TextView tv_shipping, tv_payment, tv_confirm, tv_next;
    private int idx_state = 0;

    private enum State {
        CHECKING,
        LOADING,
        FINISH
    }

    State[] array_state = new State[]{State.CHECKING, State.LOADING, State.FINISH};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_rejected_vehicle, container, false);
        /*line_first = (View) v.findViewById(R.id.line_first);
        line_second = (View) v.findViewById(R.id.line_second);
        image_shipping = (ImageView) v.findViewById(R.id.image_left);
        image_payment = (ImageView) v.findViewById(R.id.image_midle);
        image_confirm = (ImageView) v.findViewById(R.id.image_right);
        tv_next = (TextView) v.findViewById(R.id.tvNext);
        tv_shipping = (TextView) v.findViewById(R.id.tvlineone);
        tv_payment = (TextView) v.findViewById(R.id.tvlinetwo);
        tv_confirm = (TextView) v.findViewById(R.id.tvlinethree);

        image_payment.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        image_confirm.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        (v.findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state == array_state.length - 1) {
                    TransactionClosed();
                }else{
                    if(array_state[idx_state].name().equalsIgnoreCase(State.CHECKING.name())){
                        FragmentDetailInfoChecker myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//                        boolean valid = myFragment.validateAnswHeader();
                        boolean isRejected = myFragment.statusRejected;
//                        valid = true;
                        isRejected = false;
                        if (true){
                            if (isRejected){
                                final Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.alert_validation);
                                dialog.setTitle("Warning");
                                *//*Button btnSave = (Button) dialog.findViewById(R.id.btnSaveToDraft);
                                // if button is clicked, close the custom dialog
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });*//*

                                dialog.show();
                            }else{
                                idx_state++;
                                displayFragment(array_state[idx_state]);
                            }
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(),"not valid",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    }
                }
            }
        });

        (v.findViewById(R.id.lyt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state < 1) return;
                idx_state--;
                displayFragment(array_state[idx_state]);
            }
        });
        displayFragment(array_state[idx_state]);*/

        return v;
    }
    private void TransactionClosed(){
        FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
        FragmentTransaction fragmentTransactionSPMSearch = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
        fragmentTransactionSPMSearch.commit();
    }
    private void displayFragment(State state) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.CHECKING.name())) {
//            fragment = new FragmentPertanyaan();
            FRAGMENT_TAG = "Checker";
            fragment = new FragmentDetailInfoChecker();
            tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
            image_shipping.clearColorFilter();
            tv_next.setText("Start Loading");
        } else if (state.name().equalsIgnoreCase(State.LOADING.name())) {
            FRAGMENT_TAG = "Loading";
            fragment = new FragmentLoading();
            line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_shipping.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_payment.clearColorFilter();
            tv_payment.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Finish");

        } else if (state.name().equalsIgnoreCase(State.FINISH.name())) {
            FRAGMENT_TAG = "Finish";
            fragment = new FragmentLoadingFinish();
            line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_payment.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_confirm.clearColorFilter();
            tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Close");
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment,FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tv_shipping.setTextColor(getResources().getColor(R.color.grey_20));
        tv_payment.setTextColor(getResources().getColor(R.color.grey_20));
        tv_confirm.setTextColor(getResources().getColor(R.color.grey_20));
    }
}
