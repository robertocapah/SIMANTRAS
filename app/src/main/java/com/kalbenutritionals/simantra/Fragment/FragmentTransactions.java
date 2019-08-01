package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.androidnetworking.error.ANError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.Intent.IntentCustom;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.BuildConfig;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterHistoricalTransaction;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getListTransaction.DataItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getListTransaction.ListDatTransaksiItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getListTransaction.ResponseGetListTransaction;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworkingDownloadFile;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmItemGroupNotifAdapter;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapter;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.facebook.shimmer.ShimmerFrameLayout;

public class FragmentTransactions extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.ln_no_item)
    LinearLayout lnNoItem;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    //    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerViewContainer;
    private List<VmItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> listDataChild = new HashMap<>();
    AdapterHistoricalTransaction mExpandableListAdapter;
    View v;
    //    @BindView(R.id.coordinator_lyt_no_item)
//    CoordinatorLayout coordinatorLytNoItem;
    @BindView(R.id.exp_transaction)
    ExpandableListView expTransaction;
    private Gson gson;
    Dialog dialog;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaction, container, false);
        unbinder = ButterKnife.bind(this, v);
        lnNoItem.setVisibility(View.GONE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        context = getActivity().getApplicationContext();
        generateData();
        return v;
    }

    public void generateData() {
        shimmerViewContainer.startShimmer();
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context, 0, "", 0, 0);
        String strLinkAPI = new ClsHardCode().linkgetTransaksiMobileList;
        new FastNetworkingUtils().FNRequestPostDataNoProgress(getActivity(), strLinkAPI, obj, new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetListTransaction model = gson.fromJson(response.toString(), ResponseGetListTransaction.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()) {

                        List<DataItem> items = model.getData();

                        int k = 0;
                        for (DataItem item :
                                items) {
                            List<ListDatTransaksiItem> ltTrans = item.getListDatTransaksi();
                            VmItemGroupNotifAdapter adapterData = new VmItemGroupNotifAdapter();
                            adapterData.setTxtTittle(item.getVENDORNAME());
                            adapterData.setTxtSubTittle(String.valueOf(ltTrans.size())+" Transactions");
//                            adapterData.setIntTransaksiId(k);
                            adapterData.setIntColor(Color.GREEN);
//                            adapterData.setTxtDescTransaksi("Transaksi desc" + i);
                            adapterData.setTxtImgName("Banana");
//                            adapterData.setTxtLinkImage(imgLink);
                            listDataHeader.add(adapterData);
                            List<VmListItemAdapter> adapterDetail = new ArrayList<>();
                            for (ListDatTransaksiItem trans :
                                    ltTrans) {
                                VmListItemAdapter adapterDetailData = new VmListItemAdapter();
                                adapterDetailData.setTxtDate("B 1234 KC " + k);
                                adapterDetailData.setTxtDesc("Description Roberto " + k);
                                adapterDetailData.setTxtId(k + "");
                                adapterDetailData.setTxtImgName("Roberto " + k);
                                adapterDetailData.setTxtTittle(trans.getTXTSPMNO());
                                adapterDetailData.setTxtSubTittle("Tj. Priok - Sunter Karya" + k);
                                adapterDetailData.setTxtStatus(trans.getTXTSTATUSNAME());
                                adapterDetailData.setIntStatus(trans.getINTSTATUSID());
                                adapterDetailData.setTxtLinkPdf(trans.getTxtLinkPDF());
                                adapterDetail.add(adapterDetailData);

                            }
                            listDataChild.put(adapterData, adapterDetail);

                            k++;
                        }
                        if(lnNoItem!=null) {
                            if (listDataHeader.size() > 0) {
                                lnNoItem.setVisibility(View.GONE);
                            } else {
                                lnNoItem.setVisibility(View.VISIBLE);
                            }
                        }
                        mExpandableListAdapter = new AdapterHistoricalTransaction(getActivity(), listDataHeader, listDataChild);
                        if(expTransaction!=null){
                            expTransaction.setAdapter(mExpandableListAdapter);
                            expTransaction.setEmptyView(v.findViewById(R.id.ln_empty));

                            expTransaction.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                @Override
                                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                    VmListItemAdapter data = (VmListItemAdapter) mExpandableListAdapter.getChild(groupPosition, childPosition);
                                    getPDFDowload(data);
                                    return false;
                                }
                            });
                            new BLHelper().stopShimmerEffect(shimmerViewContainer);
                        }

                        /*List<String> imgLink = new ArrayList<>();
//        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
//        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
                        for (int i = 0; i < 5; i++) {
                            VmItemGroupNotifAdapter adapterData = new VmItemGroupNotifAdapter();
                            adapterData.setIntTransaksiId(i);
                            adapterData.setIntColor(Color.GREEN);
                            adapterData.setTxtDescTransaksi("Transaksi desc" + i);
                            adapterData.setTxtImgName("Banana");
                            adapterData.setTxtLinkImage(imgLink);
                            adapterData.setTxtTittle("PT Bangun Persada" + i);
                            adapterData.setTxtSubTittle("12 Desc 2019 " + i);
                            listDataHeader.add(adapterData);
                            List<VmListItemAdapter> adapterDetail = new ArrayList<>();
                            for (int j = 0; j < 3; j++) {
                                VmListItemAdapter adapterDetailData = new VmListItemAdapter();
                                adapterDetailData.setBoolSection(true);
                                adapterDetailData.setDrwImg(getActivity().getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
                                adapterDetailData.setTxtDate("B 1234 KC " + j);
                                adapterDetailData.setTxtDesc("Description Roberto " + j);
                                adapterDetailData.setTxtId(j + "");
                                adapterDetailData.setTxtImgName("Roberto " + j);
                                adapterDetailData.setTxtTittle("KNS17090048 " + j);
                                adapterDetailData.setTxtSubTittle("Tj. Priok - Sunter Karya" + j);
                                adapterDetail.add(adapterDetailData);
                            }

                            listDataChild.put(adapterData, adapterDetail);

                        }
                        if (listDataHeader.size() > 0) {
                            coordinatorLytNoItem.setVisibility(View.GONE);
                        }
                        mExpandableListAdapter = new AdapterHistoricalTransaction(getActivity(), listDataHeader, listDataChild);
                        expTransaction.setAdapter(mExpandableListAdapter);
                        expTransaction.setEmptyView(v.findViewById(R.id.ln_empty));

                        expTransaction.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                getPDFDowload();
                                return false;
                            }
                        });*/
                    }
                }
            }

            @Override
            public void onError(ANError error) {
//                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    String reportName = "ReportSimantra";
    String TAG_DOWNLOAD_REPORT = "REPORT_SIMANTRA";

    public void getPDFDowload(VmListItemAdapter data) {
        final String txtPathUserData = Environment.getExternalStorageDirectory() + File.separator + "TempDataReport" + File.separator;
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_progress_download_apk);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        String url = data.getTxtLinkPdf();
        final DonutProgress progressD = (DonutProgress) dialog.findViewById(R.id.progressPercentage);
        new FastNetworkingUtils().FNRequestDownloadAPKFile(getActivity(), url, txtPathUserData, reportName, TAG_DOWNLOAD_REPORT, dialog, new InterfaceFastNetworkingDownloadFile() {
            @Override
            public void onProgress(final long bytesDownloaded, final long totalBytes) {
                // do anything with progress
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        double precentage = ((double) bytesDownloaded / (double) totalBytes) * 100;
                        progressD.setProgress((int) precentage);
//                                tvProgress.setText("bytes downloaded :" + bytesDownloaded + ". total bytes : " + totalBytes + " precentage : " + precentage + " %");
                    }
                });
            }

            @Override
            public void onDownloadComplete() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
//                        Toast.makeText(getActivity().getApplicationContext(), "Download Complete", Toast.LENGTH_SHORT).show();
                        String txtPath = txtPathUserData + reportName;
//                        Toast.makeText(context, "File downloaded", Toast.LENGTH_LONG).show();
                        try {
                            File file = new File(txtPath);
                            Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
//                                String url = "http://10.171.14.45/simantra-api/DataUpload/ReportSimantra/2019/userid/SMT_KNS17112519_20190717114210389.pdf";
//                                Uri myUri = Uri.parse(url);
                            IntentCustom.intentPDViewer(getActivity(), uri, true);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onError(ANError error) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}
