package com.ts.algorithm.sensitiveWord.sensitive.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Mapper.SensitiveWordMapper;
import com.ts.algorithm.sensitiveWord.sensitive.domain.SensitiveWord;
import org.springframework.stereotype.Service;

/**
 * 敏感词DAO
 *
 * @author zhaoyuhang
 * @since 2023/06/11
 */
@Service
public class SensitiveWordDao extends ServiceImpl<SensitiveWordMapper, SensitiveWord> {

}
