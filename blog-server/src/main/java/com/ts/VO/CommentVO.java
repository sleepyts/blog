package com.ts.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String content;
    private String url;
    private Boolean isAdmin;
    private Long replyId;
    private LocalDateTime createTime;
    private List<CommentVO> replyList;
}
