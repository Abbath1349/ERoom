package ru.dmitrykuzmin.eroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import ru.dmitrykuzmin.eroom.code.IDistrictObservable;
import ru.dmitrykuzmin.eroom.code.IDistrictObserver;
import ru.dmitrykuzmin.eroom.common.model.enums.ActionType;
import ru.dmitrykuzmin.eroom.common.model.enums.ApartmentType;
import ru.dmitrykuzmin.eroom.common.model.enums.DistrictType;
import ru.dmitrykuzmin.eroom.common.model.enums.PeriodType;
import ru.dmitrykuzmin.eroom.common.model.enums.RealEstateType;
import ru.dmitrykuzmin.eroom.common.utils.ResUtils;
import ru.dmitrykuzmin.eroom.common.utils.StringUtils;
import ru.dmitrykuzmin.eroom.dialogs.SelectDistrictDialiog;
import ru.dmitrykuzmin.eroom.parser.IApartmentFilter;


public class MainActivity extends ActionBarActivity implements IDistrictObserver {
    ERoomApplication app;
    @Inject
    IApartmentFilter filter;
    @Inject
    IDistrictObservable districtObservable;
    @InjectView(R.id.ma_et_price_low)
    EditText editTextPriceLow;
    @InjectView(R.id.ma_et_price_high)
    EditText editTextPriceHigh;
    @InjectView(R.id.ma_tv_districts)
    TextView textViewDistricts;
    @InjectViews({R.id.ma_btn_apartment_one, R.id.ma_btn_apartment_two, R.id.ma_btn_apartment_three,
            R.id.ma_btn_apartment_four, R.id.ma_btn_apartment_five_and_more})
    List<ToggleButton> buttonsApartment;

    private SelectDistrictDialiog districtDialiog;

    private Map<DistrictType, String> districtValues;
    /**
     * selected district values
     */
    private Set<String> selectedValues;

    /**
     * returns set of selected district values)
     */
    private Set<String> getSelectedValues() {
        if (selectedValues == null)
            selectedValues = new TreeSet<String>();
        return selectedValues;
    }

    private void addSelectedValue(String value) {
        getSelectedValues().add(value);
    }

    private void removeSelectedValue(String value) {
        if (getSelectedValues().contains(value))
            getSelectedValues().remove(value);
    }

    private String selectedValuesToString() {
        StringBuilder builder = new StringBuilder("");
        for (String s : getSelectedValues()) {
            builder.append(s);
            builder.append(',');
        }
        if (builder.length() > 0)
            builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.app = ERoomApplication.from(this);
        app.inject(this);
        ButterKnife.inject(this);

        filter.setRealEstateType(RealEstateType.APARTMENT);
        filter.setPeriod(PeriodType.LONG);

        districtObservable.registerObserver(this);
    }

    @OnClick(R.id.ma_btn_districts)
    public void showDialog() {
        if (districtDialiog == null) {
            districtDialiog = SelectDistrictDialiog.create();
            app.inject(districtDialiog);
        }
        districtDialiog.show(getSupportFragmentManager(), "Dialog");
    }

    @OnClick({R.id.ma_btn_apartment_one, R.id.ma_btn_apartment_two, R.id.ma_btn_apartment_three,
            R.id.ma_btn_apartment_four, R.id.ma_btn_apartment_five_and_more})
    public void oneRoomBtnClick(View view) {
        final ToggleButton button = (ToggleButton) view;
        final String name = getResources().getResourceEntryName(button.getId()).replace("ma_btn_", "");

        for (ApartmentType a : ApartmentType.values()) {
            if (name.equals(a.toString().toLowerCase())) {
                if (button.isChecked())
                    filter.addAppartmentType(a);
                else
                    filter.removeAppartmentType(a);
                break;
            }
        }
    }

    public IApartmentFilter getFilter() {
        return filter;
    }

    /**
     * what to rent? room or apartment
     */
    @OnClick({R.id.ma_rb_rent_room, R.id.ma_rb_rent_apartment})
    public void selectObjectForRentClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.ma_rb_rent_room:
                if (checked) {
                    getFilter().setRealEstateType(RealEstateType.ROOM);
                }
                break;
            case R.id.ma_rb_rent_apartment:
                if (checked) {
                    getFilter().setRealEstateType(RealEstateType.APARTMENT);
                }
                break;
        }
    }

    @OnClick(R.id.ma_btn_find)
    public void findApartmentClick() {
        String priceLow = String.valueOf(editTextPriceLow.getText());
        String priceHigh = String.valueOf(editTextPriceHigh.getText());
        filter.setPriceLow(StringUtils.isNullOrEmpty(priceLow) ? null : Integer.parseInt(priceLow));
        filter.setPriceHigh(StringUtils.isNullOrEmpty(priceHigh) ? null : Integer.parseInt(priceHigh));
        Intent intent = new Intent(MainActivity.this, ApartmentListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Map<DistrictType, String> getDistrictValues() {
        if (districtValues == null) {
            loadDistrictValues();
        }
        return districtValues;
    }

    private void loadDistrictValues() {
        districtValues = new EnumMap<DistrictType, String>(DistrictType.class);
        for (DistrictType d : DistrictType.values()) {
            districtValues.put(d, ResUtils.getStringByName(getApplicationContext(), d.toString().toLowerCase()));
        }
    }

    @Override
    public void updateDistrict(String value, ActionType action) {
        if (action == ActionType.ADD) {
            addSelectedValue(value);
        } else if (action == ActionType.REMOVE) {
            removeSelectedValue(value);
        }
        textViewDistricts.setText(selectedValuesToString());
    }
}
