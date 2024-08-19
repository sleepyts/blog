package com.ts.Controller;

import com.ts.Model.Entity.Moment;
import com.ts.Model.Entity.Result;
import com.ts.Service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MomentController {
    @Autowired
    private MomentService momentService;

    @GetMapping("/moment")
    public Result getMomentsPage(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "7") int size) {
        return momentService.getMomentByPage(page, size);
    }

    @GetMapping("/admin/moment")
    public Result getMoments() {
        return momentService.getMoments();
    }

    @PostMapping("/admin/moment")
    public Result addMoment(@RequestBody Moment moment) {
        return momentService.addMoment(moment);
    }

    @DeleteMapping("/admin/moment/{id}")
    public Result deleteMoment(@PathVariable("id") int id) {
        return momentService.deleteMoment(id);
    }

    @PutMapping("/admin/moment")
    public Result updateMoment(@RequestBody Moment moment) {
        return momentService.updateMoment(moment);
    }

    @PutMapping("/admin/moment/visible/{id}")
    public Result updateVisible(@PathVariable("id") int id) {
        return momentService.changeVisibility(id);
    }

    @GetMapping("/moment/recent")
    public Result getRecentMoments() {
        return momentService.getRecentMoments();
    }

    @PostMapping("/moment/like/{id}")
    public Result likeMoment(@PathVariable("id") int id) {
        return momentService.likeMoment(id);
    }
}
