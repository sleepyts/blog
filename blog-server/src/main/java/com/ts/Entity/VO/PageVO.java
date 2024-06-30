package com.ts.Entity.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long total;
    private Object rows;
}
