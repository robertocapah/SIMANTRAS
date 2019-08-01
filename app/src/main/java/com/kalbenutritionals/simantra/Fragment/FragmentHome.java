package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbenutritionals.simantra.ActivityMainMenu;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getDataHome.Data;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getDataHome.ResponseGetDataHome;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentHome extends Fragment {
    View v;
    @BindView(R.id.image_profil_home)
    public CircleImageView imageProfilHome;
    @BindView(R.id.tv_user_name_home)
    TextView tvUserNameHome;
    @BindView(R.id.tv_outlet_home)
    TextView tvOutletHome;
    @BindView(R.id.tv_email_home)
    TextView tvEmailHome;
    @BindView(R.id.tv_emp_id)
    TextView tvEmpId;
    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.nested_content)
    NestedScrollView nestedContent;
    @BindView(R.id.tv_greetings)
    TextView tvGreetings;
    @BindView(R.id.ln_checked)
    LinearLayout lnChecked;
    @BindView(R.id.data_draft)
    TextView dataDraft;
    @BindView(R.id.ln_draft)
    LinearLayout lnDraft;
    @BindView(R.id.data_approve)
    TextView dataApprove;
    @BindView(R.id.ln_approve)
    LinearLayout lnApprove;
    @BindView(R.id.data_rejected)
    TextView dataRejected;
    @BindView(R.id.ln_closed)
    LinearLayout lnClosed;
    @BindView(R.id.data_closed)
    TextView dataClosed;
    @BindView(R.id.lnTransList)
    LinearLayout lnTransList;
    private Toolbar toolbar;
    Unbinder unbinder;
    private String FRAG_VIEW = "Fragment view";
    RepoclsToken tokenRepo;
    Context context;
    ClsToken dataToken;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        ClsmUserLogin dtLogin = null;
        try {
            dtLogin = new RepomUserLogin(getContext()).getUserLogin(getContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dtLogin.getBlobImg() != null) {
            Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
            imageProfilHome.setImageBitmap(bitmap);
//            PickImage.previewCapturedImage(ivProfile, bitmap, 200, 200);
        }
        String greeting = new BLHelper().getGreetings(dtLogin.getTxtUserName());
        tvGreetings.setText(greeting);
        tvUserNameHome.setText(dtLogin.getTxtUserName());
        tvFullName.setText(dtLogin.getTxtUserName());
        tvEmpId.setText(dtLogin.getTxtEmpID());
        if (dtLogin.getTxtEmpID()==null){
            tvEmpId.setVisibility(View.GONE);
        }
        tvEmailHome.setText(dtLogin.getTxtEmail());
        if (dtLogin.getTxtEmail()==null){
            tvEmailHome.setVisibility(View.GONE);
        }
        tvRole.setText(dtLogin.getTxtRoleName());
        if (dtLogin.getTxtRoleName()==null){
            tvRole.setVisibility(View.GONE);
        }
        tvUserNameHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ClsHardCode().copydb(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Haiii", Toast.LENGTH_SHORT).show();
            }
        });
        imageProfilHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new ClsHardCode().copydb(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Haiii", Toast.LENGTH_SHORT).show();
            }
        });
        generateData();
        return v;
    }

    public void goToTransactionlist() {
        ActivityMainMenu activityMainMenu = (ActivityMainMenu) getActivity();
        activityMainMenu.navigationView.setCheckedItem(R.id.transaction);
        activityMainMenu.toolbar.setTitle("Transaction History");
        FragmentTransactions fragmentTransactions = new FragmentTransactions();
        FragmentTransaction fragmentTransactionSPMSearch = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionSPMSearch.replace(R.id.frame, fragmentTransactions);
        fragmentTransactionSPMSearch.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void generateData() {
        String strLinkAPI = new ClsHardCode().linkgetDataHome;
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context, 0, "", 0, 0);
        /*try {
            tokenRepo = new RepoclsToken(context);
            String token = tokenRepo.findToken();
            resJson.put("data", obj);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        new FastNetworkingUtils().FNRequestPostDataNoProgress(getActivity(), strLinkAPI, obj, new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    ResponseGetDataHome model = gson.fromJson(response.toString(), ResponseGetDataHome.class);
                    if (model.getResult() != null) {
                        if (model.getResult().isStatus()) {
                            Data data = model.getData();
                            if (data != null) {
                                int approve = data.getINTAPPROVE();
                                int reject = data.getINTREJECT();
                                int draft = data.getINTDRAFT();
                                int closed = data.getINTDONE();

                                if (dataApprove != null) {
                                    dataApprove.setText(approve + "");
                                    dataRejected.setText(reject + "");
                                    dataDraft.setText(draft + "");
                                    dataClosed.setText(closed + "");
                                }
                            } else {
                                Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;
//                finish();
            }
        });
    }

    @OnClick({R.id.data_draft, R.id.ln_draft, R.id.data_approve, R.id.ln_approve, R.id.data_rejected, R.id.ln_checked, R.id.data_closed, R.id.ln_closed, R.id.lnTransList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.data_draft:
                goToTransactionlist();
                break;
            case R.id.ln_draft:
                goToTransactionlist();
                break;
            case R.id.data_approve:
                goToTransactionlist();
                break;
            case R.id.ln_approve:
                goToTransactionlist();
                break;
            case R.id.data_rejected:
                goToTransactionlist();
                break;
            case R.id.ln_checked:
                goToTransactionlist();
                break;
            case R.id.data_closed:
                goToTransactionlist();
                break;
            case R.id.ln_closed:
                goToTransactionlist();
                break;
            case R.id.lnTransList:
                goToTransactionlist();
                break;
        }
    }
}
