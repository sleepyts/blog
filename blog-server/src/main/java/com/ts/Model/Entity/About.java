package com.ts.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class About implements Serializable{
    private static final long serialVersionUID = 1L;

    private String aboutBlogContent;
    private String songId;
    private Boolean isPlayList;
    
}
