package com.anzhen.config;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname : MybatisConfig
 * @Date : 2021.4.27 9:21
 * @Created : anzhen
 * @Description :
 * @目的:  后续配置分页插件
 */
@Configuration
@MapperScan("com.anzhen.mapper")
public class MybatisConfig {
    // 开启驼峰命名

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
