package com.humy.mycat.repository;

import com.humy.mycat.constant.RepositoryConstant;
import com.humy.mycat.entity.Cat;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Repository;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 15:12
 * @Description:
 */
@Repository
@Where(clause = RepositoryConstant.DELETED_COLUMN_NAME + "= 0")
public interface CatRepository extends SoftDeleteRepository<Cat, Long> {

}
