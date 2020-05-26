package cn.enilu.flash.api.controller.promotion;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.promotion.Topic;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.promotion.TopicService;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion/topic")
public class TopicController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TopicService topicService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list(@RequestParam(value = "disabled",required = false) Boolean disabled,
					   @RequestParam(value = "startDate",required = false) String startDate,
					   @RequestParam(value = "endDate",required = false) String endDate) {
		Page<Topic> page = new PageFactory<Topic>().defaultPage();
		page.addFilter( "disabled", disabled);
		page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parse(startDate,"yyyyMMddHHmmss"));
		page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parse(endDate,"yyyyMMddHHmmss"));
		page = topicService.queryPage(page);
		return Rets.success(page);
	}

	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑专题", key = "name")
	@RequiresPermissions(value = {Permission.TOPIC_EDIT})
	public Object save(@ModelAttribute Topic topic){
		if(topic.getId()==null){
			topic.setPv(0L);
			topicService.insert(topic);
		}else {
			topicService.update(topic);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除专题", key = "id")
	@RequiresPermissions(value = {Permission.TOPIC_DEL})
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
		}
		topicService.deleteById(id);
		return Rets.success();
	}

	@RequestMapping(value="/changeDisabled",method = RequestMethod.POST)
	@RequiresPermissions(value = {Permission.TOPIC_EDIT})
	public Object changeIsOnSale(@RequestParam("id")  Long id,@RequestParam("disabled") Boolean disabled){
		if (id == null) {
			throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
		}
		topicService.changeDisabled(id,disabled);
		return Rets.success();
	}
}
