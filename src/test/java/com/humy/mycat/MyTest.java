package com.humy.mycat;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        long l = LocalDateTime.now().minus(6, ChronoUnit.MONTHS).minus(1, ChronoUnit.YEARS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(l);

    }

    @Test
    public void f2() throws IOException {
        String s1 = "{\n" +
                "\t\"name\":\"小花\",\n" +
                "\t\"birthDay\":\"1523954348694\",\n" +
                "\t\"gender\":1,\n" +
                "\t\"color\":\"黄白\",\n" +
                "\t\"adasd\":\"asdasd\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        Cat cat = mapper.readValue(s1, Cat.class);
        System.out.println(cat);
        System.out.println("gdgdygdas87cgwe8uadiuctwivxwidh.f8ywedvjh1exgiy1vwsgiyvdqekhxgiuvdkhqevxiuqegxkqhevxqeiugxqkhevxqiuefx1hkevdi1uegfuoe2vfuo2ejoqwrvfojwqrfgouqwrgcouwegcu9wrgffgwu9evfojwecvjowdvc9uwvcjorwvc9uwdgcohwevcudwgwrq9uctojwecggyc7weu9r2bfycjowrcg9uwrcvouwrcvu8wrg9tcwojevf78ewgcgf9uwevcwr8weoucgoweu" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "");

    }

}
