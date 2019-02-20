package shp.template.Fragment;

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

import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import shp.template.BL.BLMain;
import shp.template.Data.ClsHardCode;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Database.Repo.RepomUserLogin;
import shp.template.R;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentHome extends Fragment {
    View v;
    @BindView(R.id.image_profil_home)
    CircleImageView imageProfilHome;
    @BindView(R.id.tv_user_name_home)
    TextView tvUserNameHome;
    @BindView(R.id.tv_outlet_home)
    TextView tvOutletHome;
    @BindView(R.id.tv_plan_home)
    TextView tvPlanHome;
    @BindView(R.id.ln_plan_home)
    LinearLayout lnPlanHome;
    @BindView(R.id.tv_Realisasi_home)
    TextView tvRealisasiHome;
    @BindView(R.id.ln_realisasi_home)
    LinearLayout lnRealisasiHome;
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
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
