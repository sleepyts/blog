package com.ts.Utils;

import com.ts.Model.DTO.VerifyDTO;
import com.ts.Model.VO.VerifyVO;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

import static com.ts.Constants.RedisConstants.VERIFY_CACHE_KEY;


/**
 * 验证工具类
 *
 *
 */
public class VerifyUtil {


    public static VerifyVO getVerifyVO() {
        VerifyVO verifyVO = new VerifyVO();
        int var1,var2;
        Random random=new Random();
        var1=random.nextInt(10);
        var2=random.nextInt(10);
        String verifyKey=String.valueOf(System.currentTimeMillis());
        verifyVO.setVar1(var1);
        verifyVO.setVar2(var2);
        verifyVO.setKey(verifyKey);
        return verifyVO;
    }

    /*
     * 验证码验证
     * return 0 验证成功 1 验证失败 2 验证码已过期 3 验证码错误
     */
    public static Integer verify(VerifyDTO verifyDTO,StringRedisTemplate redisTemplate){
        if(verifyDTO==null) return 1;
        int var1=verifyDTO.getVar1();
        int var2=verifyDTO.getVar2();
        String verifyVar=verifyDTO.getVerifyVar();
        String verifyKey=verifyDTO.getKey();
        String Code=redisTemplate.opsForValue().get(VERIFY_CACHE_KEY+verifyKey);
        String str=var1+"*" + var2 + "=" + verifyVar;
        if(Code==null) return 2;
        if(!Code.equals(str)) return 3;

        return 0;
    }
}
