package study.jll.com.myapplication;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import study.jll.com.library.FixDexUtils;

/**
 * Created by jll on 2019/1/24.
 */

public class BaseApplication extends MultiDexApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
//        FixDexUtils.loadFixDex(this);
    }
}
