package com.ts.Model.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * 验证DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer var1;
    private Integer var2;
    private String verifyVar;
    private String key;
}
