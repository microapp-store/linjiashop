package cn.enilu.flash.service.system;


import cn.enilu.flash.bean.entity.system.Express;
import cn.enilu.flash.dao.system.ExpressRepository;

import cn.enilu.flash.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressService extends BaseService<Express,Long,ExpressRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExpressRepository expressRepository;

    public void changeDisabled(Long id, Boolean disabled) {
        Express express = get(id);
        express.setDisabled(disabled);
        update(express);
    }
}

