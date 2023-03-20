package com.heaven.controller;

import com.heaven.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanggq
 * @date 2023/3/20 16:31
 */
@RestController
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/load")
    public void load(){
        gatewayService.loadRoute();
    }
}
