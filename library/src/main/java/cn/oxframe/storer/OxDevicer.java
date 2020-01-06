package cn.oxframe.storer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

/**
 * 合理使用下面两个对外公开的方法
 * 方法中在手机刷机，应用卸载，多种情况下。UUID 不会改变
 * cn.oxframe.storer
 * Created by WangChangYun on 2020/1/6 12:00
 * slight negligence may lead to great disaster~
 */
public class OxDevicer {

    /**
     * 生成唯一的设备号
     */
    public static String UUID() {
        return new UUID(deviceNumber().hashCode(), serialNumber().hashCode()).toString();
    }

    /**
     * 当android 版本号>= 22 和 手机号不为空 并且 卡槽中有当前手机号的情况下，所生成UUID 和 上面方法生成UUID 是不等的
     * 该方法用于用户手机号与设备号的绑定
     * 手机号的id 也是唯一的
     *
     * @param context
     * @param phone
     * @return
     * @throws SecurityException
     */
    public static String UUID(Context context, String phone) throws SecurityException {
        String serialNumber = serialNumber();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && !TextUtils.isEmpty(phone)) {
            SubscriptionManager subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (null != subsManager) {
                List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();
                for (SubscriptionInfo subsInfo : subsList) {
                    if (null != subsInfo && !TextUtils.isEmpty(subsInfo.getNumber()) && subsInfo.getNumber().contains(phone)) {
                        serialNumber = subsInfo.getIccId();
                    }
                }
            }
        }
        return new UUID(deviceNumber().hashCode(), serialNumber.hashCode()).toString();
    }

    /**
     * 序列号
     */
    @SuppressLint("HardwareIds")
    private static String serialNumber() throws SecurityException {
        String serial;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // serial = Build.getSerial();
            // The user 10660 does not meet the requirements to access device identifiers.
            serial = "serial";
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            serial = Build.getSerial();
        } else {
            serial = Build.SERIAL;
        }
        return serial;
    }

    /**
     * 设备号
     * 各种硬件信息拼接出来的15位号码
     * 不保证是否重复
     */
    private static String deviceNumber() {
        return "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
    }

}
