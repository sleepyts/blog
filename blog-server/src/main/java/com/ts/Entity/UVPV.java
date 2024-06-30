package com.ts.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_uvpv")
public class UVPV implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer uv;
    private Integer pv;
    private LocalDate date;
}
