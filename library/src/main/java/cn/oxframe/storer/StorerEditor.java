package cn.oxframe.storer;

import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * cn.oxframe.storer
 * Created by WangChangYun on 2019/12/19 10:02
 * slight negligence may lead to great disaster~
 */
class StorerEditor {

    private static final Method mMethod = findMethod();

    /**
     * 反射查找apply的方法
     */
    private static Method findMethod() {
        try {
            Class<SharedPreferences.Editor> clzz = SharedPreferences.Editor.class;
            return clzz.getMethod("apply");
        } catch (NoSuchMethodException ignored) {
            Log.e("OxStorer", "SharedPreferences Editor is not find Method Exception");
        }
        return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     */
    public static void apply(SharedPreferences.Editor editor) {
        try {
            if (mMethod != null) {
                mMethod.invoke(editor);
                return;
            }
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        editor.commit();
    }

}
