package com.kalbenutritionals.simantra.CustomView.Utils;

import android.view.View;

import com.kalbenutritionals.simantra.ViewModel.VmListAnswerView;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dewi.oktaviani on 18/02/2019.
 */

public interface OnReceivedData {
    public void onDataTransportReceived(List<View> listAnswer, HashMap<Integer, View> HMPertanyaan1, List<VmListAnswerView> ListAnswerView );
}
