package cn.enilu.flash.api.controller.system;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.entity.system.Express;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.exception.ApplicationExceptionEnum;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.system.ExpressService;
import cn.enilu.flash.service.system.LogObjectHolder;
import cn.enilu.flash.utils.StringUtil;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/express")
public class ExpressController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExpressService expressService;

    /**
     * 分页查询物流公司
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.EXPRESS})
    public Object list() {
        Page<Express> page = new PageFactory<Express>().defaultPage();
        page.setSort(Sort.by(Sort.Direction.ASC, "sort"));
        page = expressService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 获取全部非禁用的物流公司列表
     * @return
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.EXPRESS})
    public Object queryAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sort");
        SearchFilter searchFilter = SearchFilter.build("disabled", false);
        List<Express> list = expressService.queryAll(searchFilter, sort);
        return Rets.success(list);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑物流公司", key = "name")
    @RequiresPermissions(value = {Permission.EXPRESS_EDIT})
    public Object save(@ModelAttribute Express express) {
        if (express.getId() == null) {
            expressService.insert(express);
        } else {
            Express old = expressService.get(express.getId());
            LogObjectHolder.me().set(old);
            expressService.update(express);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除物流公司", key = "id")
    @RequiresPermissions(value = {Permission.EXPRESS_EDIT})
    public Object remove(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
        }
        expressService.deleteById(id);
        return Rets.success();
    }

    @RequestMapping(value="/changeDisabled",method = RequestMethod.POST)
    @RequiresPermissions(value = {Permission.EXPRESS_EDIT})
    @BussinessLog(value = "启用禁用物流公司", key = "id")
    public Object changeIsOnSale(@RequestParam("id")  Long id, @RequestParam("disabled") Boolean disabled){
        if (id == null) {
            throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
        }
        expressService.changeDisabled(id,disabled);
        return Rets.success();
    }
}
