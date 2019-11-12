package com.humy.mycat.dto.vo;

import lombok.Data;

/**
 * @Author: Milo Hu
 * @Date: 11/8/2019 13:03
 * @Description:
 */
@Data
public class ClientToken {

    private String token;

    private String uuid;

    private Long loginTime;

}
