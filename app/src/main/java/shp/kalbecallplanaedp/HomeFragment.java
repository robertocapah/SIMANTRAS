package shp.kalbecallplanaedp;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Fragment.FragmentHeaderCallPlan;
import shp.kalbecallplanaedp.Fragment.FragmentHistory;
import shp.kalbecallplanaedp.Fragment.FragmentListCallPlan;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import shp.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class HomeFragment extends Fragment {
    View v;
    private Toolbar toolbar;
    private LinearLayout ln_plan_home, ln_realisasi_home, ln_unplan_home;
    TextView tv_plan_home, tv_unplan_home, tvRealisasi_home, tv_userName, tv_email, tv_outlet, tv_emp_id, tv_name, tv_role;
    tRealisasiVisitPlanRepo repoRealisasi;
    CircleImageView ivProfile;
    private String FRAG_VIEW = "Fragment view";
    tProgramVisitSubActivity dtPlan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ln_plan_home = (LinearLayout)v.findViewById(R.id.ln_plan_home);
        ln_realisasi_home = (LinearLayout) v.findViewById(R.id.ln_realisasi_home);
//        ln_unplan_home = (LinearLayout) v.findViewById(R.id.ln_unplan_home);
        tv_plan_home = (TextView)v.findViewById(R.id.tv_plan_home);
//        tv_unplan_home = (TextView)v.findViewById(R.id.tv_unplan_home);
        tv_userName = (TextView)v.findViewById(R.id.tv_user_name_home);
        tv_email = (TextView)v.findViewById(R.id.tv_email_home);
        tv_outlet = (TextView)v.findViewById(R.id.tv_outlet_home);
        tv_role = (TextView)v.findViewById(R.id.tv_role);
        tv_name = (TextView)v.findViewById(R.id.tv_full_name);
        tv_emp_id = (TextView)v.findViewById(R.id.tv_emp_id);
        ivProfile = (CircleImageView)v.findViewById(R.id.image_profil_home);

        tvRealisasi_home = (TextView)v.findViewById(R.id.tv_Realisasi_home);

        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());

        mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());
        if (dtLogin.getBlobImg()!=null){
            Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
            ivProfile.setImageBitmap(bitmap);
//            PickImage.previewCapturedImage(ivProfile, bitmap, 200, 200);
        }
        tRealisasiVisitPlan dataCheckinActive = (tRealisasiVisitPlan) repoRealisasi.getDataCheckinActive();
        List<tRealisasiVisitPlan> listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
        List<tRealisasiVisitPlan> listPlan = (List<tRealisasiVisitPlan>) repoRealisasi.getAllPlan();
        final boolean isDataReady = new clsMainBL().isDataReady(getContext());

        if (listRealisasi!=null){
            tvRealisasi_home.setText(String.valueOf(listRealisasi.size()));
        }

        if (listPlan!=null){
            tv_plan_home.setText(String.valueOf(listPlan.size()));
        }

        if (dataCheckinActive!=null){
            try {
                dtPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dataCheckinActive.getTxtProgramVisitSubActivityId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tv_userName.setText(dtLogin.getTxtUserName().toUpperCase());
            if (dtPlan.getIntActivityId()==new clsHardCode().VisitDokter){
                tv_outlet.setText(dataCheckinActive.getTxtDokterName());
            }else if (dtPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                tv_outlet.setText(dataCheckinActive.getTxtApotekName());
            }
        }else {
            tv_userName.setText(dtLogin.getTxtUserName().toUpperCase());
            tv_outlet.setText("Inactive");
        }

        tv_email.setText(dtLogin.getTxtEmail());
        tv_emp_id.setText(dtLogin.getTxtEmpID());
        tv_role.setText(dtLogin.getTxtRoleName());
        tv_name.setText(dtLogin.getTxtNick());


        ln_plan_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int a = 5/0;
                if (isDataReady){
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAG_VIEW, "Plan");
                    new Tools().intentFragmentSetArgument(FragmentHeaderCallPlan.class, "Call Plan", getContext(), bundle);
                }

            }
        });

        ln_realisasi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataReady){
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAG_VIEW, "Realisasi");
                    new Tools().intentFragmentSetArgument(FragmentHeaderCallPlan.class, "Call Plan", getContext(), bundle);
                }

            }
        });

        tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new clsHardCode().copydb(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Haiii", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}
