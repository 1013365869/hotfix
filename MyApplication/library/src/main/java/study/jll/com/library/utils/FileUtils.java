package study.jll.com.library.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jll on 2019/1/24.
 */

public class FileUtils {

    public static void copyFile(File sourceFile,File targetFile) throws IOException {
        //新建文件输入流并对它进行缓冲
        FileInputStream inputStream =new FileInputStream(sourceFile);
        BufferedInputStream inBuff =new BufferedInputStream(inputStream);

        //新建文件输出流并对它进行缓冲
        FileOutputStream outputStream = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff =new BufferedOutputStream(outputStream);

        //缓冲数组
        byte[] b =new byte[1024*5];
        int len;
        while ((len = inBuff.read(b))!=-1){
            outBuff.write(b,0,len);
        }
        //刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        outputStream.close();
        inputStream.close();
    }
}
