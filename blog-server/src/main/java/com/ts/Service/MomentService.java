package com.ts.Service;

import com.ts.Entity.Moment;
import com.ts.Entity.Result;

public interface MomentService {
    Result addMoment(Moment moment);
    Result getMomentByPage(int page, int pageSize);
    Result getMoments();
    Result deleteMoment(int id);
    Result updateMoment(Moment moment);
    Result changeVisibility(int id);
    Result getRecentMoments();
}
