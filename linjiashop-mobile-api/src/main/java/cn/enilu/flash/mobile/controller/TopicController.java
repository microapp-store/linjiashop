package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.promotion.Topic;
import cn.enilu.flash.bean.entity.shop.Goods;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.promotion.TopicService;
import cn.enilu.flash.service.shop.GoodsService;
import cn.enilu.flash.utils.Convert;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 1/9/2020 9:06 PM
 */
@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private GoodsService goodsService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list() {
        Page<Topic> page = new Page<Topic>();
        page.setSize(6);
        page.setSort(Sort.by(Sort.Direction.ASC,"id"));
        page.addFilter(SearchFilter.build("disabled",false));
        page = topicService.queryPage(page);

        return Rets.success(page.getRecords());
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable("id") Long id){
        Topic topic = topicService.get(id);
        topic.setPv(topic.getPv()+1);
        topicService.update(topic);
        String idGoodsList = topic.getIdGoodsList();
        if(StringUtil.isNotEmpty(idGoodsList)){
            Long[] idGoodsArr = Convert.toLongArray(",",idGoodsList);
            List<Goods> goodsList = goodsService.queryAll(SearchFilter.build("id", SearchFilter.Operator.IN,idGoodsArr));
          topic.setGoodsList(goodsList);

        }
        return Rets.success(topic);
    }
}
