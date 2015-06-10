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

import tommy_kw.jins_meme_sample.R;
import tommy_kw.jins_meme_sample.adapter.MemeItemAdapter;

/**
 * Created by tomita on 15/06/09.
 */
public class DetailActivity extends AppCompatActivity {
    private MemeLib mMemeLib;
    private String mAddress;
    private ListView mItemListView;
    private MemeItemAdapter mMemeItemAdapter;

    final MemeRealtimeListener memeRealtimeListener = new MemeRealtimeListener() {
        @Override
        public void realtimeCallback(final MemeRealtimeData memeRealtimeData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setProgressBarIndeterminateVisibility(true);
                    mMemeItemAdapter.updateMemeData(memeRealtimeData);
                    mMemeItemAdapter.notifyDataSetChanged();
                    setProgressBarIndeterminateVisibility(false);
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_detail);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_disconnect) {
            mMemeLib.disconnect();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void init() {
        mMemeLib = MemeLib.getInstance();
        mAddress = getIntent().getStringExtra("device_address");

        mItemListView = (ListView)findViewById(R.id.data_item_list_view);
        mMemeItemAdapter = new MemeItemAdapter(this);
        mItemListView.setAdapter(mMemeItemAdapter);

        mMemeLib.connect(mAddress, new MemeConnectListener() {
            @Override
            public void connectCallback(boolean status) {
                mMemeLib.startListen(memeRealtimeListener);
            }
        });
    }
}
