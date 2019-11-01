package com.humy.mycat.entity;

import com.humy.mycat.util.CommonUtil;
import com.humy.mycat.vo.Age;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 17:19
 * @Description:
 */
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long birthDay;

    @Transient
    private Age age;

    private Long createdAt;

    private Long changeAt;

    private boolean deleted;

    public Age getAge() {
        return CommonUtil.calculateAgeByBirthDay(this.getBirthDay());
    }

    public Long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Long birthDay) {
        if (birthDay > System.currentTimeMillis()) {
            throw new IllegalArgumentException("Cannot be born in the future");
        }
        this.birthDay = birthDay;
    }
}
