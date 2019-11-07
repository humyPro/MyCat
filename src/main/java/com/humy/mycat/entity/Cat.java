package com.humy.mycat.entity;

import com.humy.mycat.constant.Gender;
import com.humy.mycat.constant.RepositoryConstant;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 15:12
 * @Description:
 */
@Data
@Entity(name = "cat")
@SQLDelete(sql = "update #{#entityName} e set e." + RepositoryConstant.DELETED_COLUMN_NAME + "= 1")
@SQLDeleteAll(sql = "update #{#entityName} e set e." + RepositoryConstant.DELETED_COLUMN_NAME + "= 1")
@Where(clause = RepositoryConstant.DELETED_COLUMN_NAME + " = 0")
@DynamicUpdate
public class Cat extends BaseEntity {

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    private Long masterId;

    private Integer kindId;

    private String kindName;

    private String color;
}
