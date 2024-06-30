package com.ts.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_exception")
public class ExceptionLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String methodName;
    private String errorMessage;
    private String errorType;
    private LocalDateTime createTime;
}
