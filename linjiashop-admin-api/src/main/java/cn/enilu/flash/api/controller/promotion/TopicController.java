package cn.enilu.flash.api.controller.promotion;

import cn.enilu.flash.bean.entity.promotion.Topic;
import cn.enilu.flash.service.promotion.TopicService;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;

import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;

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
	public Object list() {
	Page<Topic> page = new PageFactory<Topic>().defaultPage();
		page = topicService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑专题", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute Topic tPromotionTopic){
		if(tPromotionTopic.getId()==null){
			topicService.insert(tPromotionTopic);
		}else {
			topicService.update(tPromotionTopic);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除专题", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (StringUtil.isEmpty(id)) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		topicService.deleteById(id);
		return Rets.success();
	}
}
