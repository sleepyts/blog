package com.ts.algorithm.sensitiveWord.sensitive.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 敏感词
 *
 * @author zhaoyuhang
 * @since 2023/06/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_sensitive_word")
public class SensitiveWord {
    private String word;
}
