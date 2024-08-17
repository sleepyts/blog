package com.ts.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer totalUV;
    private Integer totalPV;
    private Integer dailyPV;
    private Integer dailyUV;
    private Integer blogCount;
    private Integer commentCount;
    private Integer momentCount;

}
