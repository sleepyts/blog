package com.ts.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/*
 * 传输给前端的验证码对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer var1;
    private Integer var2;
    private String key;
}
