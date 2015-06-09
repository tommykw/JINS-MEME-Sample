package tommy_kw.jins_meme_sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jins_jp.meme.MemeRealtimeData;

import java.util.ArrayList;
import java.util.List;

import tommy_kw.jins_meme_sample.R;

/**
 * Created by tomita on 15/06/09.
 */
public class MemeItemAdapter extends BaseAdapter {

    List<String[]> mItems;
    Context mContext;
    LayoutInflater mInflater;

    public MemeItemAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        updateMemeData(new MemeRealtimeData());
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.meme_item, null);
        }

        TextView labelTextView = (TextView)view.findViewById(R.id.item_label);
        TextView valueTextView = (TextView)view.findViewById(R.id.item_value);

        String[] item = mItems.get(i);
        labelTextView.setText(item[0]);
        valueTextView.setText(item[1]);

        return view;
    }

    public void updateMemeData(MemeRealtimeData d) {
        mItems = new ArrayList<>();
        addItem(R.string.fit_status, d.getFitError());
        addItem(R.string.walking, d.isWalking());
        addItem(R.string.power_left, d.getPowerLeft());
        addItem(R.string.eye_move_up, d.getEyeMoveUp());
        addItem(R.string.eye_move_down, d.getEyeMoveDown());
        addItem(R.string.eye_move_left, d.getEyeMoveLeft());
        addItem(R.string.eye_move_right, d.getEyeMoveRight());
        addItem(R.string.blink_streangth, d.getBlinkStrength());
        addItem(R.string.blink_speed, d.getBlinkSpeed());
        addItem(R.string.roll, d.getRoll());
        addItem(R.string.pitch, d.getPitch());
        addItem(R.string.yaw, d.getYaw());
        addItem(R.string.acc_x, d.getAccX());
        addItem(R.string.acc_y, d.getAccY());
        addItem(R.string.acc_z, d.getAccZ());
    }

    private String getLabel(int resourceId) {
        return mContext.getResources().getString(resourceId);
    }

    private void addItem(int resourceId, Object value) {
        mItems.add(new String[] {getLabel(resourceId), String.valueOf(value)});
    }
}
