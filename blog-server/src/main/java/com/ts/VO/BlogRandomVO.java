package com.ts.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRandomVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private LocalDateTime createTime;

}
