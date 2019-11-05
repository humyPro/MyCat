package com.humy.mycat;

import com.humy.mycat.entity.Cat;
import com.humy.mycat.vo.Age;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 16:34
 * @Description:
 */
public class MyTest {

    @Test
    public void f1() {
        Cat cat = new Cat();
        cat.setBirthDay(LocalDateTime.now().minus(2, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        Age age = cat.getAge();
        System.out.println(age);
        String clause;

        long l = LocalDateTime.now().minus(6, ChronoUnit.MONTHS).minus(1, ChronoUnit.YEARS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(l);

    }

    @Test
    public void f2() throws IOException {
    }

}
