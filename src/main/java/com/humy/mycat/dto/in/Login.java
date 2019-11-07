package com.humy.mycat.dto.in;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:56
 * @Description:
 */
@Getter
@Setter
public class Login {

    private Long userId;

    private String telNumber;

    private String password;

    private String newPassword;

}
