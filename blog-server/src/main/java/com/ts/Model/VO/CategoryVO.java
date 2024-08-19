package com.ts.Model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
}
