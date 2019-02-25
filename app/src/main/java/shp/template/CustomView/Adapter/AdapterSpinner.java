package shp.template.CustomView.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import shp.template.ViewModel.VmSpinner;

/**
 * Created by dewi.oktaviani on 21/02/2019.
 */

public class AdapterSpinner extends ArrayAdapter<VmSpinner> {
    private Context mContext;
    private List<VmSpinner> objects;
    public AdapterSpinner(@NonNull Context context, int resource, @NonNull List<VmSpinner> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public VmSpinner getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView)super.getView(position, convertView, parent);
//        label.setcolo
        label.setText(objects.get(position).getTxtValue());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(objects.get(position).getTxtValue());
        return label;
    }
}
