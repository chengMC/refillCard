package com.mc.refillCard.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @Author: MC
 * @Date2020-12-21
 */

public class DateUtils {

    /**
     *  判断一个月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int judgeDay(int year, int month) {
     return new GregorianCalendar(year, month - 1, 1)
                .getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
