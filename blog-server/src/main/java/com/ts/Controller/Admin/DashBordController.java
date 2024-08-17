package com.ts.Controller.Admin;

import com.ts.Entity.Result;
import com.ts.Mapper.UVPVMapper;
import com.ts.Service.IBlogService;
import com.ts.Service.ICommentService;
import com.ts.VO.DashBoardVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ts.Constants.RedisConstants.PV_CACHE_KEY;
import static com.ts.Constants.RedisConstants.UV_CACHE_KEY;

@RestController
@RequestMapping("/admin/dashboard")
public class DashBordController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private UVPVMapper uvpvMapper;

    @GetMapping("")
    public Result getDashboard() {
        DashBoardVO dashboardVO = new DashBoardVO();
        dashboardVO.setBlogCount((int) blogService.count());
        dashboardVO.setCommentCount((int) commentService.count());
        dashboardVO.setDailyPV(Math.toIntExact(redisTemplate.opsForValue().increment(PV_CACHE_KEY)));
        dashboardVO.setDailyUV(Math.toIntExact(redisTemplate.opsForSet().size(UV_CACHE_KEY)));
        dashboardVO.setTotalPV(uvpvMapper.getTotalPV());
        dashboardVO.setTotalUV(uvpvMapper.getTotalUV());
        return Result.success(dashboardVO);
    }
}
