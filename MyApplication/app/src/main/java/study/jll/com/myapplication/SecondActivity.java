package study.jll.com.myapplication;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import study.jll.com.library.FixDexUtils;
import study.jll.com.library.utils.Constants;
import study.jll.com.library.utils.FileUtils;
import study.jll.com.myapplication.utils.ParamsSort;

public class SecondActivity extends BaseActivity {

    private Button bt_show;
    private Button bt_fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bt_show = findViewById(R.id.bt_show);
        bt_fix = findViewById(R.id.bt_fix);

        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsSort sort =new ParamsSort();
                sort.math(SecondActivity.this);
            }
        });

        bt_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixBug();
            }
        });
    }

    /**
     * 通过服务器接口下载dex文件
     */
    private void fixBug() {

        //通过服务器接口下载dex文件,v1.3.3版本有某一个热修复dex包
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DDE_NAME);

        //目标路径，私有目录里的临时文件夹odex
        File targetFile =new File(getDir(Constants.DDE_DIR, Context.MODE_PRIVATE).getAbsolutePath()+File.separator+Constants.DDE_NAME);

        //r如果存在，比如之前修复过的classess2.dex 清理
        if (targetFile.exists()){
            targetFile.delete();
            Toast.makeText(this,"删除已经存在的dex文件",Toast.LENGTH_SHORT).show();
        }

        try {
            //复制修复包dex文件到app私有目录
            FileUtils.copyFile(sourceFile,targetFile);
            Toast.makeText(this,"复制dex文件完成",Toast.LENGTH_SHORT).show();
            //开始修复
            FixDexUtils.loadFixDex(this);
        } catch (IOException e) {
            Log.i("jll"," 1111111111");
            e.printStackTrace();
        }
    }
}
