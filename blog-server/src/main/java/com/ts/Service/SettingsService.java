package com.ts.Service;

import com.ts.Model.Entity.Result;
import com.ts.Model.Entity.Settings;

public interface SettingsService {
    Result getSettings();

    Result updateSettings(Settings settings);
}
