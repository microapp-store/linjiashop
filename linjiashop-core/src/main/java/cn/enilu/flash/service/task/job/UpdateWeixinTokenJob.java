package cn.enilu.flash.service.task.job;

import cn.enilu.flash.service.api.WeixinService;
import cn.enilu.flash.service.task.JobExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定时获取微信token<br>
 *     微信token有效期是两个小时，所以建议定时任务执行频率小于两个小时
 * @author ：enilu
 * @date ：Created in 1/19/2020 4:30 PM
 */
@Component
public class UpdateWeixinTokenJob extends JobExecuter {
    @Autowired
    private WeixinService weixinService;
    @Override
    public void execute(Map<String, Object> dataMap) throws Exception {
        weixinService.updateWeixinToken();
    }
}
