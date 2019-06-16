package org.apache.rocketmq.order.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author snowalker
 * @date 2018/9/20
 * @desc 日期工具
 */
public class DateUtil {

    private DateUtil() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 线程安全方式
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    private static final ThreadLocal<DateFormat> DATE_FORMATY_YYYMMDD
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    private static final ThreadLocal<DateFormat> DATE_FORMAT_YYYYMM
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMM"));

    private static final ThreadLocal<DateFormat> DATE_FORMAT_NORMAL_THREAD_LOCAL
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final ThreadLocal<DateFormat> DATE_FORMATY_YYYMMDD2
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    /**
     * 格式化时间为时间戳：yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 解析字符串形式的时间戳为java.util.Date
     * @param timeStampStr
     * @return
     */
    public static Date parseDateFromStr(String timeStampStr) {
        try {
            return DATE_FORMAT_THREAD_LOCAL.get().parse(timeStampStr);
        } catch (ParseException e) {
            LOGGER.error("解析'yyyyMMddHHmmss'形式时间戳为java.util.Date失败,e={}", e);
        }
        return null;
    }

    public static void remove() {
        DATE_FORMAT_THREAD_LOCAL.remove();
    }


    /**
     * 格式化时间为时间戳：yyyyMMdd
     * @param date
     * @return
     */
    public static String formatDateYyyyMMdd(Date date) {
        return DATE_FORMATY_YYYMMDD.get().format(date);
    }

    /**
     * 解析字符串形式的时间戳为java.util.Date
     * @param timeStampStr
     * @return
     */
    public static Date parseDateFromStrYyyyMMdd(String timeStampStr) {
        try {
            return DATE_FORMATY_YYYMMDD.get().parse(timeStampStr);
        } catch (ParseException e) {
            LOGGER.error("解析'yyyyMMdd'形式时间戳为java.util.Date失败,e={}", e);
        }
        return null;
    }

    public static void removeYyyyMMdd() {
        DATE_FORMATY_YYYMMDD.remove();
    }

    /**
     * 格式化时间为时间戳：yyyyMM
     * @param date
     * @return
     */
    public static String formatNormalDate(Date date) {
        return DATE_FORMAT_NORMAL_THREAD_LOCAL.get().format(date);
    }

    /**
     * 解析字符串形式的时间戳为java.util.Date
     * @param timeStampStr
     * @return
     */
    public static Date parseNormalDateFromStr(String timeStampStr) {
        try {
            return DATE_FORMAT_NORMAL_THREAD_LOCAL.get().parse(timeStampStr);
        } catch (ParseException e) {
            LOGGER.error("解析'yyyy-MM-dd HH:mm:ss'形式时间戳为java.util.Date失败,e={}", e);
        }
        return null;
    }

    public static void removeNormal() {
        DATE_FORMAT_NORMAL_THREAD_LOCAL.remove();
    }

    /**
     * 格式化时间为时间戳：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateYyyyMM(Date date) {
        return DATE_FORMAT_YYYYMM.get().format(date);
    }

    /**
     * 解析字符串形式的时间戳为java.util.Date  yyyy-MM-dd HH:mm:ss
     * @param timeStampStr
     * @return
     */
    public static Date parseDateFromStrYyyyMM(String timeStampStr) {
        try {
            return DATE_FORMAT_YYYYMM.get().parse(timeStampStr);
        } catch (ParseException e) {
            LOGGER.error("解析'yyyyMM'形式时间戳为java.util.Date失败,e={}", e);
        }
        return null;
    }

    public static void removeYyyyMM() {
        DATE_FORMAT_YYYYMM.remove();
    }

    /**
     * 格式化时间为时间戳：yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDateYyyyMMdd2(Date date) {
        return DATE_FORMATY_YYYMMDD2.get().format(date);
    }

    /**
     * 解析字符串形式的时间戳为java.util.Date  yyyy-MM-dd
     * @param timeStampStr
     * @return
     */
    public static Date parseDateFromStrYyyyMMdd2(String timeStampStr) {
        try {
            return DATE_FORMATY_YYYMMDD2.get().parse(timeStampStr);
        } catch (ParseException e) {
            LOGGER.error("解析'yyyy-MM-dd'形式时间戳为java.util.Date失败,e={}", e);
        }
        return null;
    }

    public static void removeYyyyMMdd2() {
        DATE_FORMATY_YYYMMDD2.remove();
    }

    /**
     * 获取本月第一天的00:00:00
     * @return
     */
    public static Date getCurrMonthFirstDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = DateUtil.formatDateYyyyMMdd2(c.getTime());
        first = first + " 00:00:00";
        Date queryStartDate = DateUtil.parseNormalDateFromStr(first);
        return queryStartDate;
    }

    /**
     * 获取本月最后一天的23:59:59
     * @return
     */
    public static Date getCurrMonthEndDate() {
        // 查询结束时间--本月最后一秒
        Calendar lastDay = Calendar.getInstance();
        lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = DateUtil.formatDateYyyyMMdd2(lastDay.getTime());
        last = last + " 23:59:59";
        Date queryEndDate = DateUtil.parseNormalDateFromStr(last);
        return queryEndDate;
    }
    /**
     * 获取当前月份整数值--用于表分区
     * @return
     */
    public static Integer getCurrMonthNum() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

}


