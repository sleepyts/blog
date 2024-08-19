package com.ts.Service;

import com.ts.Model.Entity.Result;

public interface VisitorService {
    Result getVisitor();

    Result deleteVisitorById(Integer id);

    Result getVisitorCount();
}
