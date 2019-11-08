package com.humy.mycat.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Milo Hu
 * @Date: 11/8/2019 11:14
 * @Description:
 */
@Data
public class Token {

    private String tokenStr;

    private Long userId;

    private List<Device> devices;

}
