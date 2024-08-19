package com.ts.Model.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDetailVO implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String img;
    private String title;
    private String content;
    private String categoryName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
