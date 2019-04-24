package com.kalbenutritionals.simantra.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.R;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.data_checked)
    TextView dataChecked;
    @BindView(R.id.ln_checked)
    LinearLayout lnChecked;
    @BindView(R.id.tv_excalation)
    TextView tvExcalation;
    @BindView(R.id.ln_excalation)
    LinearLayout lnExcalation;
    @BindView(R.id.tv_rejected)
    TextView tvRejected;
    @BindView(R.id.ln_rejected)
    LinearLayout lnRejected;
    private Toolbar toolbar;
    Unbinder unbinder;
    private String FRAG_VIEW = "Fragment view";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);


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
        tvEmailHome.setText(dtLogin.getTxtEmail());
        tvRole.setText(dtLogin.getTxtRoleName());
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
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
