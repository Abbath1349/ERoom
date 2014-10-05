package ru.dmitrykuzmin.eroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.dmitrykuzmin.eroom.adapters.ApartmentListAdapter;
import ru.dmitrykuzmin.eroom.common.model.ApartmentBase;
import ru.dmitrykuzmin.eroom.common.model.IApartmentBase;
import ru.dmitrykuzmin.eroom.common.tasks.ApartmentBaseLoader;


public class ApartmentListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<IApartmentBase>> {

    public static int LOADER_APARTMENT_BASE = 0;

    @InjectView(R.id.ala_lw_apartment_base)
    ListView listView;
    private ERoomApplication app;
    private ProgressDialog progressDialog;
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getAdapter().getItem(position) instanceof ApartmentBase) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((ApartmentBase) parent.getAdapter().getItem(position)).getDetailsLink()));
                startActivity(browserIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_list);
        this.app = ERoomApplication.from(this);
        app.inject(this);
        ButterKnife.inject(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        listView.setOnItemClickListener(clickListener);

        getSupportLoaderManager().initLoader(LOADER_APARTMENT_BASE, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apartment_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<IApartmentBase>> onCreateLoader(int id, Bundle bundle) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Loader<List<IApartmentBase>> loader = null;
        if (id == LOADER_APARTMENT_BASE) {
            loader = new ApartmentBaseLoader(this);
            app.inject(loader);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<IApartmentBase>> listLoader, List<IApartmentBase> apartmentBases) {
        if (apartmentBases == null || apartmentBases.size() == 0) {
            nothingFound();
        } else {
            ApartmentListAdapter adapter = ApartmentListAdapter.create(this, apartmentBases);
            listView.setAdapter(adapter);
            dismissProgressDialog();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<IApartmentBase>> listLoader) {

    }

    private void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void nothingFound() {
        Toast.makeText(this, getString(R.string.nothing_found), Toast.LENGTH_LONG).show();
        dismissProgressDialog();
        finish();
    }
}
