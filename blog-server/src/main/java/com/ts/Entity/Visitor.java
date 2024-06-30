package com.ts.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName
public class Visitor implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    private String ip;
    private String address;
    private String browser;
    private String os;
    private LocalDateTime firstVisitTime;
    private LocalDateTime lastVisitTime;
    private Integer visitCount;
    private String userAgent;
}
