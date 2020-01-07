package cn.enilu.flash.api.controller;

import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.dashboard.DashboardService;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ：enilu
 * @date ：Created in 1/7/2020 2:23 PM
 */
@RequestMapping("/dashboard")
@RestController
public class DashboardController extends BaseController {
    @Autowired
    private DashboardService dashboardService;
    @RequestMapping(method = RequestMethod.GET)
    public Object get(){
        Map data = dashboardService.getDashboardData();
        return Rets.success(data);
    }
}
