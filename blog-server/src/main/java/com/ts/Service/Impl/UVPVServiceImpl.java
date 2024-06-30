package com.ts.Service.Impl;


import com.ts.Entity.UVPV;
import com.ts.Mapper.UVPVMapper;
import com.ts.Service.IUVPVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UVPVServiceImpl implements IUVPVService {

    @Autowired
    private UVPVMapper uvpvMapper;
    @Override
    public void addUVPV(UVPV uvpv) {
        uvpvMapper.insertUVPV(uvpv);
    }
}
