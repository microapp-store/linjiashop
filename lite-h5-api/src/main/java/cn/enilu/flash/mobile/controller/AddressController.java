package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.Address;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.AddressService;
import cn.enilu.flash.utils.HttpUtil;
import cn.enilu.flash.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 11/5/2019 7:36 PM
 */
@RestController
@RequestMapping("/user/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;
    @RequestMapping(value = "/queryByUser",method = RequestMethod.GET)
    public Object getByUser(){
        Long idUser = getIdUser(HttpUtil.getRequest());
        List<Address> list = addressService.queryAll(SearchFilter.build("idUser", SearchFilter.Operator.EQ,idUser));
        return Rets.success(list);
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(@ModelAttribute @Valid Address address){
        Long idUser = getIdUser(HttpUtil.getRequest());
        address.setIdUser(idUser);
        addressService.insert(address);
        return Rets.success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object update(@ModelAttribute @Valid Address address){
        addressService.update(address);
        return Rets.success();
    }
}
