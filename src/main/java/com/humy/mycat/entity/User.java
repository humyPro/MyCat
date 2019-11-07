package com.humy.mycat.entity;

import com.humy.mycat.constant.RepositoryConstant;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 15:47
 * @Description:
 */
@Data
@Entity
@SQLDelete(sql = "update #{#entityName} e set e." + RepositoryConstant.DELETED_COLUMN_NAME + "= 1")
@SQLDeleteAll(sql = "update #{#entityName} e set e." + RepositoryConstant.DELETED_COLUMN_NAME + "= 1")
@Where(clause = RepositoryConstant.DELETED_COLUMN_NAME + " = 0")
@DynamicUpdate
public class User extends BaseEntity {

    @NotNull
    private String nickName;

    @NotNull
    private String telNumber;

    /**
     * encoded password
     */
    private String password;

}
