package com.asanhospital.server.service.TempService;

public class TempQueryServiceImpl implements TempQueryService{
    @Override
    public void CheckFlag(Integer flag) {
        if (flag == 1) {
            System.out.println("Flag is 1");
        } else {
            System.out.println("Flag is not 1");
        }
    }
}
