package cn.enilu.flash.bean.vo.node;

import cn.enilu.flash.bean.entity.shop.Category;
import cn.enilu.flash.utils.Lists;
import lombok.Data;

import java.util.List;

@Data
public class CategoryNode extends Category {
    private List<CategoryNode> children= Lists.newArrayList();

}
