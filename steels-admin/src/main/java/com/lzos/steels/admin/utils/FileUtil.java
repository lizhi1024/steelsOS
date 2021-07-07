package com.lzos.steels.admin.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作工具类
 * Auth: lizhi
 * Date: 2021-07-01
 */
public class FileUtil {

    /**
     * 复制文件内容
     * @param src
     * @param des
     */
    public static void copy(File src, File des) throws Exception{

        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader(src);
            fw = new FileWriter(des);
            int rs;
            while((rs = fr.read()) != -1){
                fw.write(rs);
            }
        }catch (Exception e) {
            throw e;
        } finally {
            if (fr != null) {
                fr.close();
            }
            if (fw != null) {
                fw.close();
            }
        }

    }

    /**
     * 删除文件或文件夹
     * @param src
     */
    public static void delete(File src){

        if(src.isFile()){
            src.delete();
        }if(src.isDirectory()){
            File[] f= src.listFiles();
            for(int i = 0; i < f.length; i++){
                if(f[i].isFile()){
                    f[i].delete();
                }if(f[i].isDirectory()){
                    delete(f[i]);
                    f[i].delete();
                }
            }
        }

    }

    /**
     * 单个文件重命名
     * @param src
     * @param newName
     */
    public static void rename(File src,String newName){

        File newFile = new File(newName);
        if(src.exists()){
            src.renameTo(newFile);
        }else{
            System.out.print("该文件不存在！");
        }

    }

    /**
     * 获取文件列表
     * @param src
     * @return
     */
    public static ArrayList<Map<String, Object>> listFile(File src){

        ArrayList<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
        Map<String, Object> file = null;

        File[] f = src.listFiles();
        for(int i = 0; i < f.length; i++){

            String fileName = f[i].getName();
            boolean isDir = f[i].isDirectory();
            long fileSize = f[i].length();

            file = new HashMap<String, Object>();
            file.put("fileName", fileName);
            file.put("isDir", isDir);
            file.put("fileSize", fileSize);
            files.add(file);
        }
        return files;

    }

    /**
     * 类序列化
     * @param o
     * @param f
     */
    public static void writeObjectToFile(Object o, File f) throws Exception{

        FileOutputStream fos = null;
        ObjectOutputStream obs = null;
        try {
            fos = new FileOutputStream(f);
            obs = new ObjectOutputStream(fos);
            obs.writeObject(o);
        } catch (Exception e) {
            throw e;
        } finally {
            if (obs != null) {
                obs.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * 文件反序列化
     * @param f
     * @return
     */
    public static Object readObjectFromFile(File f) throws Exception{

        Object o = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            o = ois.readObject();
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return o;

    }

    /**
     * 合并文件
     * @param f1
     * @param f2
     * @param f3
     */
    public static void compposite(File f1,File f2,File f3) throws Exception{

        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        SequenceInputStream sis = null;
        FileOutputStream fos = null;
        try {
            fis1 = new FileInputStream(f1);
            fis2 = new FileInputStream(f2);
            sis = new SequenceInputStream(fis1,fis2);
            fos = new FileOutputStream(f3);
            int rs = 0;
            while((rs = sis.read()) != -1){
                fos.write(rs);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (sis != null) {
                sis.close();
            }
            if (fis2 != null) {
                fis2.close();
            }
            if (fis1 != null) {
                fis1.close();
            }
        }

    }

}
