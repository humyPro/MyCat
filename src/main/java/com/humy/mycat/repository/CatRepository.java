package com.humy.mycat.repository;

import com.humy.mycat.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 15:12
 * @Description:
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

}
