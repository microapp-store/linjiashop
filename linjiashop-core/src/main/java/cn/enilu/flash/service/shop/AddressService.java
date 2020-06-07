package cn.enilu.flash.service.shop;


import cn.enilu.flash.bean.entity.shop.Address;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.shop.AddressRepository;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService extends BaseService<Address,Long,AddressRepository>  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AddressRepository addressRepository;

    /**
     * 获取用户默认收货地址
     * @param idUser
     * @return
     */
    public Address getDefaultAddr(Long idUser){
        List<SearchFilter> filterList = Lists.newArrayList(
                SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser),
                SearchFilter.build("isDefault", SearchFilter.Operator.EQ,true),
                SearchFilter.build("isDelete", SearchFilter.Operator.EQ,false)
        );
        return get(filterList);
    }

    public Address get(Long idUser,Long id){
        return get(Lists.newArrayList(
                SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser),
                SearchFilter.build("id", SearchFilter.Operator.EQ,id),
                SearchFilter.build("isDelete", SearchFilter.Operator.EQ,false)
        ));
    }
    public void delete(Long idUser, Long id) {
        Address address = get(Lists.newArrayList(
                SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser),
                SearchFilter.build("id", SearchFilter.Operator.EQ,id)
        ));
        address.setIsDelete(true);
        update(address);
    }
}

