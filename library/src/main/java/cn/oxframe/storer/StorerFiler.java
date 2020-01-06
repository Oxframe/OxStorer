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
     * Android Q 之后不建议将 SharedPreferences 文件路径迁移到内部存储 Android 文件夹之外
     * Android Q 在读写 Android 文件夹之外需要 读写权限
     * 有些第三方sdk 需要 app 启动进行初始化，这时候可能有些数据已经需要保存到 SharedPreferences 中
     * 如果在经过一层权限的话，可能会出现一些文件存储类型的错误。
     * 原来 SharedPreferences 默认地址在  /data/data/%package_name%/shared_prefs/%fileName%.xml 下（/data/data/是一个隐藏文件夹，手机需要root 才能发现）
     * 可以将 SharedPreferences 修改到 内部存储 /Android/data/%package_name%/data/%fileName%.xml 下 （当前方法）
     * 如果出现一些异常的话，还是会存储到 /data/data/%package_name%/shared_prefs/%fileName%.xml 下 隐藏文件夹下
     * 个人理解
     */
    static SharedPreferences iSharedPreferences(Context context, String filename) {
        try {
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            // 获取mBase变量
            Object obj = field.get(context);
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            assert obj != null;
            field = obj.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);
            // 创建自定义路径
            File file = new File(iFilePath(context));
            // 修改mPreferencesDir变量的值
            Log.i("StorerFiler", file.getAbsolutePath());
            field.set(obj, file);
            return context.getSharedPreferences(filename, Activity.MODE_PRIVATE);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    private static String iFilePath(Context context) {
        return context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
    }

}
