package com.ts.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 博客缩略VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogThumbnailVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    // 博客ID
    private Integer value;
    // 博客标题
    private String label;

}
