package com.humy.mycat.service.impl;

import com.humy.mycat.entity.Cat;
import com.humy.mycat.repository.CatRepository;
import com.humy.mycat.service.CatService;
import com.humy.mycat.util.BeanUtil;
import com.humy.mycat.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 09:35
 * @Description:
 */
@Service
@Slf4j
public class CatServiceImpl implements CatService {

    private CatRepository catRepository;

    private RedisUtil redis;

    public CatServiceImpl(CatRepository catRepository, RedisUtil redis) {
        this.catRepository = catRepository;
        this.redis = redis;
    }

    @Override
    public Cat CreateCat(Cat cat) {
        Cat saved = catRepository.save(cat);
        redis.setValue(RedisUtil.CAT_PREFIX + saved.getId(), cat, 3600 * 24);
        return saved;
    }

    @Override
    public Cat getCatById(Long id) {
        Cat cat = redis.getValue(RedisUtil.CAT_PREFIX + id, Cat.class);
        if (cat == null) {
            Optional<Cat> byId = catRepository.findById(id);
            Cat cat_from_db = byId.orElse(null);
            if (cat_from_db == null) {
                redis.setEmptyValue(RedisUtil.CAT_PREFIX + id);
                return null;
            } else {
                redis.setValue(RedisUtil.CAT_PREFIX + cat_from_db.getId(), cat_from_db);
                return cat_from_db;
            }
        }
        return cat;
    }

    public Page<Cat> listCat(int page, int size) {
        return catRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public boolean deleteCatById(Long id) {
        catRepository.deleteById(id);
        redis.remove(RedisUtil.CAT_PREFIX + id);
        return true;
    }

    @Override
    public Cat updateCat(Cat cat) {
        Optional<Cat> byId = catRepository.findById(cat.getId());
        if (!byId.isPresent()) {
            return null;
        }
        Cat in_db = byId.get();
        BeanUtil.copyPropertiesIgnoreNull(cat, in_db);
        Cat save = catRepository.save(in_db);
        redis.setValue(RedisUtil.CAT_PREFIX + save.getId(), save);
        return save;
    }
}
