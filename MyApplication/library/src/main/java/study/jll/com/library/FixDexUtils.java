package study.jll.com.library;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.HashSet;

import javax.crypto.NullCipher;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import study.jll.com.library.utils.ArraryUtils;
import study.jll.com.library.utils.Constants;
import study.jll.com.library.utils.ReflectUtils;

/**
 * Created by jll on 2019/1/24.
 */

public class FixDexUtils {
    //存放需要修复dex集合，可能不止一个
    private static HashSet<File> loadDex = new HashSet<>();

    static {
        //修复前，进行清理工作
        loadDex.clear();
    }

    /**
     * 加载热修复的dex文件
     *
     * @param context
     */
    public static void loadFixDex(Context context) {
        if (context == null) return;
        //dex文件目录(私有目录中，存在之前已经复制过来的修复包)
        File fileDir = context.getDir(Constants.DDE_DIR, Context.MODE_PRIVATE);
        File[] listFiles = fileDir.listFiles();
        //遍历私有目录的所有的文件
        for (File file : listFiles) {
            if (file.getName().endsWith(Constants.DDE_SUFFIX) && !"classes.dex".equals(file.getName())) {
                loadDex.add(file);
            }
        }

        //模拟类加载器
        createDexClassLoader(context, fileDir);
    }

    /**
     * 创建加载补丁的DexClassLoader(自有目录)
     *
     * @param context
     * @param fileDir Dex文件目录
     */
    private static void createDexClassLoader(Context context, File fileDir) {
        //创建临时的解压目录（先解压到该目录，再加载java）
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        //不存在就创建
        File fopt = new File(optimizedDir);
        if (!fopt.exists()) {
            fopt.mkdirs();
        }

        for (File dex : loadDex) {
            //每遍历一个要修复的dex文件，就需要插桩一次
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(), optimizedDir, null, context.getClassLoader());
            hotfix(classLoader, context);
        }
    }

    /**
     * 热修复
     *
     * @param classLoader 自有的类加载器，加载了修复包的loader
     * @param context
     */
    private static void hotfix(DexClassLoader classLoader, Context context) {
        //获取系统的PathClassLoader
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            //获取自有的dexElements数组对象
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));

            //获取系统的dexElements数组对象
            Object systemDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));

            //合并成新的dexElements数组对象
            Object dexElements = ArraryUtils.combineArray(myDexElements, systemDexElements);

            //通过反射的在获取 系统的pathList对象
            Object systemPathList = ReflectUtils.getPathList(pathClassLoader);

            //重新赋值给系统的pathList属性 -- 修改了pathList中的dexElements数组对象
            ReflectUtils.setField(systemPathList,systemPathList.getClass(),dexElements);
            Log.i("jll"," 123456");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("jll"," 789");
        }


    }
}
