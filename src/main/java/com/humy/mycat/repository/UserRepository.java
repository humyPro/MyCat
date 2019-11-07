package com.humy.mycat.repository;

import com.humy.mycat.dto.in.Login;
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

    @Query("update #{#entityName} e set e.passwprd = #{login.newPassword} where e.id= #{login.userId} and e.password=#{login.password}")
    @Transactional
    @Modifying
    int changePassword(Login login);
}
