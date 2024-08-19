package com.ts.Model.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
 * 文章归档实体类
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("tb_record")
public class Record implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer blogId;
    private String title;
    private LocalDate date;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
