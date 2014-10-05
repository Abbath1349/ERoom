package ru.dmitrykuzmin.eroom.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.dmitrykuzmin.eroom.R;
import ru.dmitrykuzmin.eroom.code.Names;
import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;

/**
 * Created by DmitryComp on 21.09.2014.
 */
public class ApartmentListAdapter extends ArrayAdapter<IApartmentBase> {
    private final List<IApartmentBase> apartmentBases;
    private Activity context;

    private ApartmentListAdapter(Activity context, List<IApartmentBase> apartmentBases) {
        super(context, R.layout.list_item_apartment_base, apartmentBases);
        this.apartmentBases = apartmentBases;
        this.context = context;
    }

    public static ApartmentListAdapter create(Activity activity, List<IApartmentBase> apartmentBases) {
        return new ApartmentListAdapter(activity, apartmentBases);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(Names.LOG_TAG_ONE, "apartment adapter getView start");
        View view;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_item_apartment_base, null);
            final ViewHolder viewHolder = ViewHolder.create(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // todo add method get district name to DitsrictTypeEnum
        IApartmentBase current = apartmentBases.get(position);
        viewHolder.districtTextView.setText(current.getDistrict());
        Log.d(Names.LOG_TAG_ONE, "start to put apartments");
        Log.d(Names.LOG_TAG_ONE, current.getAddress());
        viewHolder.addressTextView.setText(current.getAddress());
        // todo add sourse
        viewHolder.resourseTextView.setText("E1");
        Log.d(Names.LOG_TAG_ONE, current.getPrice() != null ? current.getPrice().toString() : "-");
        viewHolder.priceTextView.setText(current.getPrice() != null ? current.getPrice().toString() : "-");
        Log.d(Names.LOG_TAG_ONE, "view done correctly");

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.li_apartment_base_tw_district)
        TextView districtTextView;
        @InjectView(R.id.li_apartment_base_tw_address)
        TextView addressTextView;
        @InjectView(R.id.li_apartment_base_tw_resourse)
        TextView resourseTextView;
        @InjectView(R.id.li_apartment_base_tw_price)
        TextView priceTextView;


        private ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public static ViewHolder create(View view) {
            return new ViewHolder(view);
        }
    }
}
