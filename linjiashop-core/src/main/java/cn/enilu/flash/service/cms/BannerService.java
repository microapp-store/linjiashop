package cn.enilu.flash.service.cms;

import cn.enilu.flash.bean.entity.cms.Banner;
import cn.enilu.flash.bean.entity.shop.CategoryBannerRel;
import cn.enilu.flash.bean.enumeration.cms.BannerTypeEnum;
import cn.enilu.flash.bean.vo.offcialsite.BannerVo;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.dao.cms.BannerRepository;
import cn.enilu.flash.service.BaseService;
import cn.enilu.flash.service.shop.CategoryBannerRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends BaseService<Banner, Long, BannerRepository> {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private CategoryBannerRelService categoryBannerRelService;

    /**
     * 查询首页banner数据
     *
     * @return
     */
    public BannerVo queryIndexBanner() {
        return queryBanner(BannerTypeEnum.INDEX.getValue());
    }

    public BannerVo queryBanner(String type) {
        BannerVo banner = new BannerVo();
        List<cn.enilu.flash.bean.entity.cms.Banner> bannerList = bannerRepository.findAllByType(type);
        banner.setIndex(0);
        banner.setList(bannerList);
        return banner;
    }

    /**
     * 删除banner<br>
     * 1,删除banner
     * 2,删除商品类别与banner关联关系记录
     *
     * @param id 主键
     */
    @Override
    public void deleteById(Long id) {
        //1,
        super.deleteById(id);
        //2,
        List<CategoryBannerRel> rels = categoryBannerRelService.queryAll(SearchFilter.build("idBanner", id));
        categoryBannerRelService.deleteAll(rels);

    }
}
