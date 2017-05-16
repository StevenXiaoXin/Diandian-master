package com.daqianjietong.diandian.utils;

import android.graphics.Bitmap;
import android.os.Environment;


import com.daqianjietong.diandian.C;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 文件读写工具类
 *
 * @author bear
 */
public class FileUtil {

    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "chargepile";

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path     文件路径
     * @param isDelete 源文件是否先删除在创建
     * @return
     */
    public static void CreateFileOrDel(String path, boolean isDelete) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } else {
            if (isDelete) {
                FileTool.delFile(path);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }

    public static void inputstreamtofile(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[2048];
        int len;
        while ((len = ins.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    /**
     * 向文件中写入数据
     *
     * @param filePath 目标文件全路径
     * @param data     要写入的数据
     * @return true表示写入成功  false表示写入失败
     */
    public static boolean writeBytes(String filePath, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 从文件中读取数据
     *
     * @param file
     * @return
     */
    public static byte[] readBytes(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public static byte[] getBytesFromFile(String path) throws IOException {
        File file = new File(path);
        return getBytesFromFile(file);
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > 2147483647L) {
            throw new IOException("File is to large " + file.getName());
        }
        byte[] bytes = new byte[(int)length];
        try {
            int offset = 0;
            int numRead = 0;
            while ((offset < bytes.length) &&
                    ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " +
                        file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            bytes = (byte[])null;
        }
    }

    /**
     * 向文件中写入字符串String类型的内容
     *
     * @param file    文件路径
     * @param content 文件内容
     */
    public static void writeString(String file, String content) {
        try {
            File filePath = new File(file);
            if (!filePath.exists()) {
                CreateFileOrDel(file, true);
            }

            byte[] data = content.getBytes("utf-8");
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, true));
            pw.print(content);
//            pw.print(data);
            pw.flush();
            pw.close();
//            writeBytes(file, data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 从文件中读取数据，返回类型是字符串String类型
     *
     * @param file 文件路径
     * @return
     */
    public static String readString(String file) {
        byte[] data = readBytes(file);
        String ret = null;

        try {
            ret = new String(data, "utf-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }


    /**初始化保存路径
     * @return
     */
    private static String initPath(){
        if(storagePath.equals("")){
//            storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
//            storagePath = C.DOWN_PATH;
            File f = new File(storagePath);
            if(!f.exists()){
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**保存Bitmap到sdcard
     * @param b
     */
    public static String saveBitmap(Bitmap b){
        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + dataTake + ".JPEG";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  jpegName;
    }


}