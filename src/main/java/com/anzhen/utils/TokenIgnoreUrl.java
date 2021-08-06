package com.anzhen.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname : TokenIgnoreUrl
 * @Date : 21/08/02 9:22
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Data
@Component
@ConfigurationProperties(prefix = "anzhen")
public class TokenIgnoreUrl {

    private List<String> tokenIgnoreUrl = new ArrayList<>();
}
