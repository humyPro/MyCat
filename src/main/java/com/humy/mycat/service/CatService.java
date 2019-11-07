package com.humy.mycat.service;

import com.humy.mycat.entity.Cat;
import org.springframework.data.domain.Page;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 09:35
 * @Description:
 */
public interface CatService {

    Cat CreateCat(Cat cat);

    Cat getCatById(Long id);

    Page<Cat> listCat(int page, int size);

    boolean deleteCatById(Long id);

    Cat updateCat(Cat cat);
}
