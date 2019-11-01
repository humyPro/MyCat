package com.humy.mycat.entity;

import com.humy.mycat.constant.Gender;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 15:12
 * @Description:
 */
@Data
@Entity
@SQLDelete(sql = "update cat set deleted = 1 where id = ?")
public class Cat extends BaseEntity implements Serializable {

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    private Long masterId;

    private Integer kindId;

    private String kindName;

    private String color;
}
