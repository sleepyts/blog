package com.ts.Model.VO;


import com.ts.Model.Entity.Category;
import com.ts.Model.Entity.Moment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsVO implements java.io.Serializable {
    private String aboutMe;
    private String ICP;
    private String aboutMePageSongId;
    private String indexName;
    private String indexUrl;
    private String description;
    private String logoUrl;
    private String announcement;
    private Integer visitorCount;
    private List<BlogRandomVO> blogRandomList;
    private List<Moment> recentMomentList;
    private List<Category> categoryList;
}
