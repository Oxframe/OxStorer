package cn.oxframe.storer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/8/008.
 */

public class OxStorer {

    private static OxStorer mStorer = null;
    private static SharedPreferences mShPre;

    /**
     * debug 环境下允许修改 sp文件的路径
     */

    public static OxStorer instance(Context context, String fileName) {
        if (null == mStorer) {
            synchronized (OxStorer.class) {
                if (null == mStorer) {
                    mStorer = new OxStorer(context, fileName);
                }
            }
        }
        return mStorer;
    }

    private OxStorer(Context context, String fileName) {
        mShPre = StorerFiler.iSharedPreferences(context, fileName);
    }

    /**
     * 保存数据
     */
    public static void put(String keyName, Object value) {
        SharedPreferences.Editor editor = mShPre.edit();
        if (value instanceof String) {
            editor.putString(keyName, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(keyName, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(keyName, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(keyName, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(keyName, (Long) value);
        } else {
            editor.putString(keyName, null != value ? value.toString() : null);
        }

        StorerEditor.apply(editor);
    }

    /**
     * 获取数据
     */
    public static Object get(String keyName, Object defaultValue) {
        SharedPreferences sp = mShPre;
        if (defaultValue instanceof String) {
            return sp.getString(keyName, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(keyName, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(keyName, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(keyName, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(keyName, (Long) defaultValue);
        }
        return null;
    }


    /**
     * 移除某个key值对应的值
     */
    public static void remove(String keyName) {
        SharedPreferences.Editor editor = mShPre.edit();
        editor.remove(keyName);
        StorerEditor.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences.Editor editor = mShPre.edit();
        editor.clear();
        StorerEditor.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String keyName) {
        return mShPre.contains(keyName);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        return mShPre.getAll();
    }

}
