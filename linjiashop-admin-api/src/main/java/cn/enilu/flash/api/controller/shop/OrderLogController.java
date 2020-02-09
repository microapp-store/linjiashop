package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.shop.OrderLog;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.OrderLogService;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/order/log")
public class OrderLogController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderLogService orderLogService;
	@RequestMapping(value = "/queryByIdOrder/{idOrder}",method = RequestMethod.GET)
	public Object queryByIdOrder(@PathVariable("idOrder") Long idOrder){
		List<OrderLog> logList = orderLogService.queryAll(SearchFilter.build("idOrder",idOrder));
		return Rets.success(logList);
	}
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<OrderLog> page = new PageFactory<OrderLog>().defaultPage();
		page = orderLogService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑订单日志", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute OrderLog tShopOrderLog){
		if(tShopOrderLog.getId()==null){
			orderLogService.insert(tShopOrderLog);
		}else {
			orderLogService.update(tShopOrderLog);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除订单日志", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		orderLogService.deleteById(id);
		return Rets.success();
	}
}
