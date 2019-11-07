package com.humy.mycat;

import com.alibaba.fastjson.JSON;
import com.humy.mycat.entity.Cat;
import com.humy.mycat.vo.Age;
import org.junit.Test;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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
        X.f1();
        Cat cat = new Cat();
        cat.setBirthDay(LocalDateTime.now().minus(2, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        Age age = cat.getAge();
        System.out.println(age);
        String clause;

        long l = LocalDateTime.now().minus(6, ChronoUnit.MONTHS).minus(1, ChronoUnit.YEARS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(l);


    }

    @Test
    public void f32() {

    }

    @Test
    public void f2() throws IOException {
        this.<String>f111("1111");
    }

    public <T> void f111(T a) {
        System.out.println(111111);
    }

    public void f111(String a) {
        System.out.println(222222);
    }

    public static void main(String[] args) {
        Jackson2JsonRedisSerializer jack = new Jackson2JsonRedisSerializer(Object.class);
        byte[] bytes = jack.serialize(new Cat());
        Object deserialize = jack.deserialize(bytes);
        System.out.println(new String(bytes));
        Cat cat = new Cat();
        cat.setId(1111L);
        System.out.println(JSON.toJSONString(cat));

    }

}
