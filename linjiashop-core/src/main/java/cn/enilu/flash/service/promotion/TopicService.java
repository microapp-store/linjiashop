package cn.enilu.flash.service.promotion;


import cn.enilu.flash.bean.entity.promotion.Topic;
import cn.enilu.flash.dao.promotion.TopicRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService extends BaseService<Topic,Long,TopicRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TopicRepository topicRepository;

    public Topic changeDisabled(Long id, Boolean disabled) {
        Topic topic = get(id);
        topic.setDisabled(disabled);
        update(topic);
        return topic;
    }
}

