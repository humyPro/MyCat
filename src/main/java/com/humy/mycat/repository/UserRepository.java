package com.humy.mycat.repository;

import com.humy.mycat.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: Milo Hu
 * @Date: 10/21/2019 09:53
 * @Description:
 */
@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

}
