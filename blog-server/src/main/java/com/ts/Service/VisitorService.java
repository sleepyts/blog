package com.ts.Service;

import com.ts.Entity.Result;

public interface VisitorService {
    Result getVisitor();

    Result deleteVisitorById(Integer id);
}
