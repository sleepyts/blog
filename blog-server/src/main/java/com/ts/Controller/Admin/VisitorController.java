package com.ts.Controller.Admin;

import com.ts.Entity.Result;
import com.ts.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/admin/visitor")
    public Result getVisitor() {
        return visitorService.getVisitor();
    }

    @DeleteMapping("/admin/visitor/{id}")
    public Result deleteVisitor(@PathVariable("id") Integer id)  {
        return visitorService.deleteVisitorById(id);
    }

    @GetMapping("/visitor/count")
    public Result getVisitorCount() {
        return visitorService.getVisitorCount();
    }
}
