package shp.template.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import shp.template.BL.BLMain;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Data.ClsHardCode;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import shp.template.R;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentHome extends Fragment {
    View v;
    private Toolbar toolbar;
    private LinearLayout ln_plan_home, ln_realisasi_home, ln_unplan_home;
    TextView tv_plan_home, tv_unplan_home, tvRealisasi_home, tv_userName, tv_email, tv_outlet, tv_emp_id, tv_name, tv_role;
    CircleImageView ivProfile;
    private String FRAG_VIEW = "Fragment view";
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



        ClsmUserLogin dtLogin = new BLMain().getUserLogin(getContext());
        if (dtLogin.getBlobImg()!=null){
            Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
            ivProfile.setImageBitmap(bitmap);
//            PickImage.previewCapturedImage(ivProfile, bitmap, 200, 200);
        }


        tv_userName.setOnClickListener(new View.OnClickListener() {
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

}
