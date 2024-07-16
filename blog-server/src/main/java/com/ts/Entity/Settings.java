package com.ts.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String aboutMe;
    private String ICP;
    private String aboutMePageSongId;
    private String indexName;
    private String indexUrl;
    private String description;
    private String logoUrl;

}

