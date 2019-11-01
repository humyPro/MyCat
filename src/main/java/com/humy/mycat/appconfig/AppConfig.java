package com.humy.mycat.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Milo Hu
 * @Date: 10/28/2019 09:49
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "custom")
@PropertySource("classpath:config/custom.properties")
@Getter
@Setter
public class AppConfig {

    private int pageNum = 0;

    private int maxPageSize = 50;

}
