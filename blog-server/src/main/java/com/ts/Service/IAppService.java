package com.ts.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.Entity.App;
import com.ts.Entity.Result;

public interface IAppService extends IService<App> {

    Result getApps();

    Result addApp(App app);

    Result deleteApp(Long id);

    Result updateApp(App app);
}
