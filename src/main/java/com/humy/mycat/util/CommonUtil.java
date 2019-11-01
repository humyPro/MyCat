package com.humy.mycat.util;

import com.humy.mycat.vo.Age;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 17:13
 * @Description:
 */
public class CommonUtil {

    public static Age calculateAgeByBirthDay(Long birthDay) {
        if (birthDay == null) return null;
        LocalDateTime birth = LocalDateTime.ofInstant(Instant.ofEpochMilli(birthDay), ZoneId.systemDefault());
        LocalDate start = birth.toLocalDate();
        LocalDate now = LocalDate.now();
        Period between = Period.between(start, now);
        return new Age(between.getYears(), between.getMonths());
    }

}
