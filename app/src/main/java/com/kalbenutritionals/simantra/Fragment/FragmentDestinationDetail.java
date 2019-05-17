package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterExpandableListDestination;
import com.kalbenutritionals.simantra.CustomView.Adapter.LineItemDecoration;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Jawaban;
import com.kalbenutritionals.simantra.ViewModel.VmImageContainer;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentDestinationDetail extends Fragment {
    View v;
    Unbinder unbinder;
    @BindView(R.id.tv_number_spm)
    TextView tvNumberSpm;
    @BindView(R.id.bt_copy_code)
    ImageButton btCopyCode;
    @BindView(R.id.tv_no_pol)
    TextView tvNoPol;
    @BindView(R.id.tvDriverName)
    TextView tvDriverName;
    @BindView(R.id.tvKeraniName)
    TextView tvKeraniName;
    @BindView(R.id.tvTimeFrom)
    TextView tvTimeFrom;
    @BindView(R.id.tvDateFrom)
    TextView tvDateFrom;
    @BindView(R.id.tvTimeTo)
    TextView tvTimeTo;
    @BindView(R.id.tvDateTo)
    TextView tvDateTo;
    @BindView(R.id.tvOutletFrom)
    TextView tvOutletFrom;
    @BindView(R.id.tvAddressFrom)
    TextView tvAddressFrom;
    @BindView(R.id.tvOutletTo)
    TextView tvOutletTo;
    @BindView(R.id.tvAddressTo)
    TextView tvAddressTo;
    @BindView(R.id.rvGeneralInformation)
    RecyclerView rvGeneralInformation;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    Context context;
    List<VmListItemAdapterPertanyaan> items;
    AdapterExpandableListDestination mAdapter;
    static List<VmListItemAdapterPertanyaan> ltDataPertanyaan = new ArrayList<>();
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.tv_no_doc)
    TextView tvNoDoc;
    @BindView(R.id.bt_copy_doc)
    ImageButton btCopyDoc;

    public FragmentDestinationDetail() {

    }

    public static FragmentDestinationDetail newInstance() {
        FragmentDestinationDetail fragment = new FragmentDestinationDetail();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_destination_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        generateData();
        return v;
    }

    private void generateData() {
        rvGeneralInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGeneralInformation.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvGeneralInformation.setHasFixedSize(true);
        ltDataPertanyaan = getData();
        //set data and list adapter
//        CustomAdapter mAdapterOptional = new CustomAdapter(getActivity(), mItems);
        List<ClsmPertanyaan> nestedInfo = new ArrayList<>();
        try {
            nestedInfo = new RepomPertanyaan(context).findQuestion(ClsHardCode.BASIC);
            for (ClsmPertanyaan p :
                    nestedInfo) {
                int seqId = p.getIntSeq();
                if (seqId == 1) {
                    tvNoDoc.setText(p.getTxtPertanyaan());
                } else if (seqId == 2) {
                    tvTanggal.setText(p.getTxtPertanyaan());
                } else if (seqId == 3) {
                    tvNumberSpm.setText(p.getTxtPertanyaan());
                } else if (seqId == 4) {

                } else if (seqId == 5) {
                    List<ClsmJawaban> ltJwb = new RepomJawaban(context).findByHeader(p.getIntPertanyaanId());
                    for (ClsmJawaban jwb :
                            ltJwb) {
                        if (jwb.getIdJawaban() == 1) {
                            tvOutletFrom.setText(jwb.getTxtJawaban());
                        } else {
                            tvOutletTo.setText(jwb.getTxtJawaban());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mAdapter = new AdapterExpandableListDestination(getActivity(), ltDataPertanyaan);
        rvGeneralInformation.setAdapter(mAdapter);
    }

    private List<VmListItemAdapterPertanyaan> getData() {

        items = new ArrayList<>();
        try {
            List<ClsmPertanyaan> pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.BASIC);
            for (ClsmPertanyaan pertanyaan :
                    pertanyaans) {

                VmListItemAdapterPertanyaan obj = new VmListItemAdapterPertanyaan();
                obj.id = pertanyaan.getIntPertanyaanId();
                obj.image = 1;
                obj.txtPertanyaan = pertanyaan.getTxtPertanyaan();
                obj.bitImage = pertanyaan.isBolHavePhoto();
                if (obj.bitImage) {
                    obj.countImage = 4;
                }

                obj.bitValid = true;
                obj.message = "";
                obj.bolHaveAnswer = pertanyaan.isBolHaveAnswer();
                obj.jenisPertanyaan = pertanyaan.getIntJenisPertanyaanId();
                List<ClsmJawaban> jawabans1 = new RepomJawaban(context).findByHeader(pertanyaan.getIntPertanyaanId());
                List<Jawaban> jawabans = new ArrayList<>();

                for (ClsmJawaban jawaban :
                        jawabans1) {
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = jawaban.getIdPertanyaan();
                    jwbn.idJawaban = jawaban.getIdJawaban();
                    jwbn.jawaban = jawaban.getTxtJawaban();
                    jwbn.bitChoosen = jawaban.isBitChoosen();
                    jawabans.add(jwbn);
                }
                obj.jawabans = jawabans;

                List<VmImageContainer> containerList = new ArrayList<>();
                for (int i = 0; i < obj.countImage; i++) {
                    VmImageContainer imageContainer = new VmImageContainer();
                    imageContainer.setPosition(i);
                    imageContainer.setPath(null);
                    imageContainer.setBitmap(null);
                    containerList.add(imageContainer);
                }
                obj.listImage = containerList;
                items.add(obj);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
