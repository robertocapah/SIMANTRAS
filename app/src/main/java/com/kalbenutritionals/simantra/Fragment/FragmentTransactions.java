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

import org.json.JSONException;
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
    private List<VmItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> listDataChild = new HashMap<>();
    AdapterHistoricalTransaction mExpandableListAdapter;
    View v;
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
                            adapterData.setTxtSubTittle(ltTrans.size()+" Transactions");
                            adapterData.setIntColor(Color.GREEN);
                            adapterData.setTxtImgName("Banana");
                            listDataHeader.add(adapterData);
                            List<VmListItemAdapter> adapterDetail = new ArrayList<>();
                            for (ListDatTransaksiItem trans :
                                    ltTrans) {
                                VmListItemAdapter adapterDetailData = new VmListItemAdapter();
                                adapterDetailData.setTxtTittle(trans.getTXTSPMNO());
                                adapterDetailData.setTxtStatus(trans.getTXTSTATUSNAME());
                                adapterDetailData.setIntStatus(trans.getINTSTATUSID());
                                adapterDetailData.setTxtLinkPdf(trans.getTxtLinkPDF());
                                adapterDetailData.setNoSPM(trans.getTXTSPMNO());
                                adapterDetailData.setIntOrderId(trans.getINTORDERID());

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
                                    getLinkDownload(data);
                                    return false;
                                }
                            });
                            new BLHelper().stopShimmerEffect(shimmerViewContainer);
                        }

                    }
                }
            }

            @Override
            public void onError(ANError error) {

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

    public void getLinkDownload(VmListItemAdapter data){
        String txtLink = new ClsHardCode().linkDownloadFilePDF+data.getNoSPM()+"&intOrderId="+data.getIntOrderId();
        new FastNetworkingUtils().FNRequestPostData(getActivity(), txtLink,new JSONObject(), "Processing SPM", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj =  response.getJSONObject("data");
                    String link = obj.getString("link_file");
                    getPDFDowload(link);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError error) {

            }
        });
    }
    public void getPDFDowload(String url) {
        final String txtPathUserData = Environment.getExternalStorageDirectory() + File.separator + "TempDataReport" + File.separator;
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_progress_download_apk);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        String url = data.getTxtLinkPdf();
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
                    }
                });
            }

            @Override
            public void onDownloadComplete() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        String txtPath = txtPathUserData + reportName;
                        try {
                            File file = new File(txtPath);
                            Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
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
