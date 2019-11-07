package com.humy.mycat.entity;

import com.humy.mycat.util.CommonUtil;
import com.humy.mycat.vo.Age;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * @Author: Milo Hu
 * @Date: 10/17/2019 17:19
 * @Description:
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long birthDay;

    @Transient
    private Age age;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    @Version
    private Long changeAt;

    private boolean deleted;

    public Age getAge() {
        return CommonUtil.calculateAgeByBirthDay(this.getBirthDay());
    }

    public Long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Long birthDay) {
        if (birthDay != null && birthDay > System.currentTimeMillis()) {
            throw new IllegalArgumentException("Cannot be born in the future");
        }
        this.birthDay = birthDay;
    }
}
