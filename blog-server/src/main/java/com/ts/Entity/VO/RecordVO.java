package com.ts.Entity.VO;

import com.ts.Entity.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordVO implements Serializable {
    private static final long serialVersionUID = 1L;
//
//    //归档展示的标题
//    private String title;
//    //归档日期
//    private LocalDate date;
//    //博客id
//    private Long blogId;
    private String year;
    private List<Record> recordList;
}