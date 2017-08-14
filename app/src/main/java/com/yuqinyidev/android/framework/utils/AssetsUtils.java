package com.yuqinyidev.android.framework.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Assets资源工具类
 * Created by yuqy on 2017/8/14.
 */
public class AssetsUtils {

    /**
     * 文件拷贝
     *
     * @param context    上下文
     * @param assetsPath assets文件(abc.txt,images/abc.jpg)
     * @param newPath    目标文件夹
     */
    public static void copyFilesFromAssets(Context context, String assetsPath, String newPath) {
        File f = new File(newPath + assetsPath);
        if (f.exists()) {
            return;
        }
        try {
//            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
//            if (fileNames.length > 0) {//如果是目录
//                File file = new File(newPath);
//                file.mkdirs();//如果文件夹不存在，则递归
//                for (String fileName : fileNames) {
//                    copyFilesFromAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
//                }
//            } else {//如果是文件
            File file = new File(newPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            InputStream is = context.getResources().getAssets().open(assetsPath);
            FileOutputStream fos = new FileOutputStream(new File(newPath + assetsPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }
            fos.flush();//刷新缓冲区
            is.close();
            fos.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
//            MainActivity.handler.sendEmptyMessage(COPY_FALSE);
        }
    }

}
