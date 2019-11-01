package com.humy.mycat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humy.mycat.entity.Cat;
import com.humy.mycat.repository.CatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MycatApplicationTests {

    @Autowired
    private CatRepository catRepository;

    @Test
    public void contextLoads() {
        boolean exists = catRepository.exists(new Example<Cat>() {
            @Override
            public Cat getProbe() {
                Cat cat = new Cat();
                cat.setId(1L);
                cat.setName("小黑");
                return cat;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matchingAll();
            }
        });
        assert exists = true : "不存在";
    }

    @Test
    public void f1() throws JsonProcessingException {
        Optional<Cat> byId = catRepository.findById(1l);
        Cat cat = byId.get();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(cat);
        System.out.println(s);
    }

}
