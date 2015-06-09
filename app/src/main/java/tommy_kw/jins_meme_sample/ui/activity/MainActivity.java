package tommy_kw.jins_meme_sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeScanListener;
import com.jins_jp.meme.MemeStatus;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import tommy_kw.jins_meme_sample.Const;
import tommy_kw.jins_meme_sample.R;

public class MainActivity extends AppCompatActivity {
    private MemeLib mMembeLib;
    private List<String> mAddresses;
    private ArrayAdapter<String> mAddressAdapter;
    private ListView mDeviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Logger.d("savedInstanceState == null");
            init();
        } else {
            Logger.d("savedInstanceState != null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mMembeLib.isScanning()) {
            menu.findItem(R.id.action_scan).setVisible(false);
            menu.findItem(R.id.action_stop).setVisible(true);
        } else {
            menu.findItem(R.id.action_scan).setVisible(true);
            menu.findItem(R.id.action_stop).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_scan) {
            startScan();
            return true;
        } else if (itemId == R.id.action_stop) {
            stopScan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        MemeLib.init(getApplicationContext(), Const.APP_CLIENT_ID, Const.APP_CLIENT_SECRET);
        mMembeLib = MemeLib.getInstance();
        mDeviceListView = (ListView)findViewById(R.id.deviceListView);
        mDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stopScan();
                String adress = mAddresses.get(position);
                //Intent intent = new Intent()
            }
        });
    }

    private void startScan() {
        if (mMembeLib.isScanning()) {
            return;
        }

        mAddresses = new ArrayList<>();
        mAddressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAddresses);
        mDeviceListView.setAdapter(mAddressAdapter);

        setProgressBarIndeterminateVisibility(true);
        invalidateOptionsMenu();
        MemeStatus status = mMembeLib.startScan(new MemeScanListener() {
                                                    @Override
                                                    public void scanCallback(String s) {

                                                    }
                                                });
        if (status == MemeStatus.MEME_ERROR_APP_AUTH) {
            Toast.makeText(this, "App Auth Failed", Toast.LENGTH_LONG).show();
            setProgressBarIndeterminateVisibility(false);
        }
    }

    private void stopScan() {
        if (!mMembeLib.isScanning()) {
            return;
        }

        mMembeLib.stopScan();
        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();
    }
}
