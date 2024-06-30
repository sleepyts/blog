package com.ts.Mapper;

import com.ts.Entity.Settings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SettingsMapper {

    @Select("SELECT * FROM tb_settings")
    Settings getSettings();

    @Update("UPDATE tb_settings set about_me=#{aboutMe},ICP=#{ICP},about_me_page_song_id=#{aboutMePageSongId}")
    boolean updateSettings(Settings setting);
}