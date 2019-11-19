package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.cms.Banner;
import cn.enilu.flash.bean.entity.shop.Category;
import cn.enilu.flash.bean.entity.shop.CategoryBannerRel;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.shop.CategoryBannerRelService;
import cn.enilu.flash.service.shop.CategoryService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/category")
public class CategoryController extends BaseController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryBannerRelService categoryBannerRelService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
		Page<Category> page = new PageFactory<Category>().defaultPage();
		page = categoryService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public Object getAll() {

		List<Category> categories = categoryService.queryAll();
		return Rets.success(categories);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑商品类别", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute Category tShopCategory){
		if(tShopCategory.getId()==null){
			categoryService.insert(tShopCategory);
		}else {
			categoryService.update(tShopCategory);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除商品类别", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		categoryService.deleteById(id);
		return Rets.success();
	}
	@RequestMapping(value="/getBanners/{idCategory}",method = RequestMethod.GET)
	public Object getBanners(@PathVariable("idCategory") Long idCategory){
		if (idCategory == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		List<CategoryBannerRel> relList = categoryBannerRelService.queryAll(SearchFilter.build("idCategory", SearchFilter.Operator.EQ,idCategory));
		List<Banner> bannerList = Lists.newArrayList();
		relList.forEach( item->{
			bannerList.add(item.getBanner());
		});

		return Rets.success(bannerList);
	}
	@RequestMapping(value="/removeBanner/{idCategory}/{idBanner}",method = RequestMethod.DELETE)
	public Object removeBanner(@PathVariable("idCategory") Long idCategory,
							@PathVariable("idBanner") Long idBanner){
		if (idCategory == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		CategoryBannerRel rel = categoryBannerRelService.get(Lists.newArrayList(
				SearchFilter.build("idCategory",idCategory),
				SearchFilter.build("idBanner",idBanner)
		));
		if(rel!=null){
			categoryBannerRelService.delete(rel);
		}
		return Rets.success();
	}
	@RequestMapping(value="/setBanner/{idCategory}/{idBanner}",method = RequestMethod.POST)
	public Object setBanner(@PathVariable("idCategory") Long idCategory,
							@PathVariable("idBanner") Long idBanner){
		if (idCategory == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		CategoryBannerRel rel = categoryBannerRelService.get(Lists.newArrayList(
				SearchFilter.build("idCategory",idCategory),
				SearchFilter.build("idBanner",idBanner)
		));
		if(rel!=null){
			return Rets.success();
		}
		rel = new CategoryBannerRel();
		rel.setIdCategory(idCategory);
		rel.setIdBanner(idBanner);
		categoryBannerRelService.insert(rel);
		return Rets.success();
	}

}
