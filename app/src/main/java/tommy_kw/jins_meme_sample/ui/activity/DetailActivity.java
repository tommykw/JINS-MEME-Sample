package tommy_kw.jins_meme_sample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.jins_jp.meme.MemeConnectListener;
import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeRealtimeData;
import com.jins_jp.meme.MemeRealtimeListener;

/**
 * Created by tomita on 15/06/09.
 */
public class DetailActivity extends AppCompatActivity {
    private MemeLib mMemeLib;
    private String mAddress;
    private ListView mItemListView;

    final MemeRealtimeListener memeRealtimeListener = new MemeRealtimeListener() {
        @Override
        public void realtimeCallback(final MemeRealtimeData memeRealtimeData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setProgressBarIndeterminateVisibility(true);
                    dataItemAdapter.updateMemeData(memeRealtimeData);
                    dataItemAdapter.notifyDataSetChanged();
                    setProgressBarIndeterminateVisibility(false);
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_meme_data);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meme_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_disconnect) {
            memeLib.disconnect();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void init() {
        memeLib = MemeLib.getInstance();
        deviceAddress = getIntent().getStringExtra("device_address");

        dataItemListView = (ListView)findViewById(R.id.data_item_list_view);
        dataItemAdapter = new MemeDataItemAdapter(this);
        dataItemListView.setAdapter(dataItemAdapter);

        memeLib.connect(deviceAddress, new MemeConnectListener() {
            @Override
            public void connectCallback(boolean status) {
                memeLib.startListen(memeRealtimeListener);
            }
        });
    }
}
