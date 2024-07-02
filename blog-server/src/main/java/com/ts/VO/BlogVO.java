package com.ts.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String img;
    private String title;
    private String date;
    private String description;
    private String categoryName;
    private Integer categoryId;
    private Long commentNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
