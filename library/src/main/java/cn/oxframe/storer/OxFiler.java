package cn.oxframe.storer;

import android.os.Environment;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * cn.oxframe.storer
 * Created by WangChangYun on 2020/1/4 16:42
 * slight negligence may lead to great disaster~
 */
public class OxFiler {

    public static String APP_NAME = "Sample";

    /**
     * SD Card 是否安装 是否可写入数据
     */
    private static boolean isSDCardAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else {
            return false;
        }
    }

    /**
     * 创建一个文件夹
     */
    public static String iCreateFolderPath(String folder) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            /**
             * 内部存储 根目录
             * /storage/emulated/0/%APP_NAME%
             */
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append(APP_NAME);
        } else {
            /**
             * 内部存储
             * /storage/emulated/0/Android/data/%package_name%
             */
            sb.append(Environment.getDataDirectory());
        }
        sb.append(File.separator);
        sb.append(folder);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists() || !file.isDirectory()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                Log.i("OxFiler", "文件夹创建成功：" + sb.toString());
            } else {
                Log.i("OxFiler", "文件夹创建失败：" + sb.toString());
            }
        } else {
            Log.i("OxFiler", "文件夹已经存在：" + sb.toString());
        }
        return file.getAbsolutePath();
    }


    /**
     * 得到SD卡根目录，SD卡不可用则获取内部存储的根目录
     */
    public static File getRootPath() {
        if (isSDCardAvailable()) {
            return Environment.getExternalStorageDirectory(); //SD卡根目录    /storage/emulated/0
        } else {
            return Environment.getDataDirectory();//内部存储的根目录    /data
        }
    }

    /**
     * 是否安装了sd 卡
     * sd卡是否可写入数据
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else {
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(File file) {
        if (file == null)
            return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists())
            return file.isFile();
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     */
    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null)
            return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
