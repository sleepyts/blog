package com.ts.Service;

import com.ts.Entity.Result;
import com.ts.Entity.Settings;

public interface SettingsService {
    Result getSettings();

    Result updateSettings(Settings settings);
}
