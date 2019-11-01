package com.humy.mycat.service.impl;

import com.humy.mycat.entity.Cat;
import com.humy.mycat.repository.CatRepository;
import com.humy.mycat.service.CatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 09:35
 * @Description:
 */
@Service
public class CatServiceImpl implements CatService {

    private CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat CreateCat(Cat cat) {
        cat.setCreatedAt(System.currentTimeMillis());
        cat.setDeleted(false);
        return catRepository.save(cat);
    }

    @Override
    public Cat getCatById(Long id) {
        Optional<Cat> byId = catRepository.findById(id);
        return byId.orElse(null);
    }

    public Page<Cat> listCat(int page, int size) {
        Page<Cat> catPage = catRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        return catPage;
    }

    @Override
    public Boolean deleteCatById(Long id) {
        return null;
    }
}
