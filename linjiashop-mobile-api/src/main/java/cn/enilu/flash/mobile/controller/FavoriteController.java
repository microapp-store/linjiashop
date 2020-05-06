package cn.enilu.flash.mobile.controller;

import cn.enilu.flash.bean.entity.shop.Favorite;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.shop.FavoriteService;
import cn.enilu.flash.web.controller.BaseController;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 1/25/2020 9:28 PM
 */
@RestController
@RequestMapping("/user/favorite")
public class FavoriteController extends BaseController {
    @Autowired
    private FavoriteService favoriteService;
    @RequestMapping(value = "/add/{idGoods}",method = RequestMethod.POST)
    public Object add(@PathVariable("idGoods") Long idGoods){
        Long idUser = JwtUtil.getUserId();
        Favorite old = favoriteService.get(idUser,idGoods);
        if(old!=null){
            return Rets.success();
        }
        Favorite favorite = new Favorite();
        favorite.setIdUser(idUser);
        favorite.setIdGoods(idGoods);
        favoriteService.insert(favorite);
        return Rets.success();
    }
    @RequestMapping(value = "/dislike/{idGoods}",method = RequestMethod.POST)
    public Object disLike(@PathVariable("idGoods") long idGoods){
        Long idUser = JwtUtil.getUserId();
        Favorite old = favoriteService.get(idUser,idGoods);
        if(old==null){
            return Rets.failure("未收藏改商品");
        }
        favoriteService.delete(old);
        return Rets.success();
    }
    @RequestMapping(value = "/dislikeBatch",method = RequestMethod.POST)
    public Object disLike(@RequestBody List<Long> ids){
        logger.info("ids:{}", Json.toJson(ids));
        favoriteService.delete(ids);
        return Rets.success();
    }

    @RequestMapping(value = "/ifLike/{idGoods}",method = RequestMethod.GET)
    public Object ifLike(@PathVariable("idGoods") Long idGoods){
        Long idUser = JwtUtil.getUserId();
        Favorite favorite = favoriteService.get(idUser,idGoods);
        return Rets.success(favorite!=null);
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(){
        List<Favorite> list = favoriteService.queryAll();
        return Rets.success(list);
    }


}
