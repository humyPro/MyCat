package com.humy.mycat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 15:47
 * @Description:
 */
@Data
@Entity
public class User extends BaseEntity {

    @NotNull
    private String nickName;

    @NotNull
    private String telNumber;

}
