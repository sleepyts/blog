package com.ts.Controller;


import com.ts.Model.Entity.Result;
import com.ts.Model.Entity.Settings;
import com.ts.Service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/settings")
    public Result getSettings() {
        return settingsService.getSettings();
    }

    @PutMapping("/admin/settings")
    public Result updateSettings(@RequestBody Settings settings) {
        return settingsService.updateSettings(settings);
    }
}
