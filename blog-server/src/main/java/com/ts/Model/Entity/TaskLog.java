package com.ts.Model.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_log")
public class TaskLog implements Serializable {

    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    private String ip;
    private String address;
    private String methodName;
    private String argsAndValue;
    private String os;
    private String browser;
    private Integer processTime;
    private LocalDateTime createTime;

}
