package com.humy.mycat.repository;

import com.humy.mycat.constant.RepositoryConstant;
import com.humy.mycat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @Author: Milo Hu
 * @Date: 10/21/2019 09:53
 * @Description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByTelNumberAndPassword(String telNumber, String password);

    @Transactional
    @Modifying
    @Query("update User e set e.password = ?3 where e.id= ?1 and e.password=?2 and e." + RepositoryConstant.NOT_DELETED_SQL)
    int changePassword(Long userId, String oldPwd, String newPwd);
}
