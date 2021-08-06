package com.anzhen.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Classname : Blog
 * @Date : 2021.4.29 9:28
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {

    private static final long serialVersionUID = 9121075790485507207L;

    private int id;

    private Boolean isTop;

    private String banner;

    private Boolean isHot;

    private String title;

    private String summary;

    private Long viewsCount;

    private Long commentsCount;

    private String createTime;

    private String mdContent;


}
