package cn.oxframe.storer;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

/**
 * cn.oxframe.storer
 * Created by WangChangYun on 2019/12/19 10:18
 * slight negligence may lead to great disaster~
 */
class StorerFiler {

    /**
     * 返回修改路径(路径不存在会自动创建)以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml<br/>
     * 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
     */
    public static SharedPreferences iSharedPreferences(Context context, String filename) {
        try {
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            // 获取mBase变量
            Object obj = field.get(context);
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            field = obj.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);
            // 创建自定义路径
            File file = new File(iFilePath(context));
            // 修改mPreferencesDir变量的值
            Log.e("StorerFiler", file.getAbsolutePath());
            field.set(obj, file);
            // 返回修改路径以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml
            return context.getSharedPreferences(filename, Activity.MODE_PRIVATE);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    /**
     * 修改以后的sp文件的路径 /storage/emulated/0/Android/data/package_name/files
     */
    private static String iFilePath(Context context) {
        return context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
    }
}
