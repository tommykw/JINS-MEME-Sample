package tommy_kw.jins_meme_sample;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by tomita on 15/06/09.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("Sample");
    }
}
