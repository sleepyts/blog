package com.ts.Controller;


import com.ts.Model.Entity.App;
import com.ts.Model.Entity.Result;
import com.ts.Service.IAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AppController {

    @Autowired
    private IAppService appService;

    @GetMapping("/app")
    public Result getApps(){
        return appService.getApps();
    }

    @PostMapping("/admin/app")
    public Result addApps(@RequestBody App app){
        return appService.addApp(app);
    }

    @DeleteMapping("/admin/app/{id}")
    public Result deleteApp(@PathVariable("id") Long id){
        return appService.deleteApp(id);
    }

    @PutMapping("/admin/app")
    public Result updateApp(@RequestBody App app) {
        return appService.updateApp(app);
    }
}
