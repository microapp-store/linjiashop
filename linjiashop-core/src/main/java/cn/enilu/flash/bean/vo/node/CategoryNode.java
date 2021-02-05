package cn.enilu.flash.bean.vo.node;

import cn.enilu.flash.bean.entity.shop.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryNode extends Category {
    private List<CategoryNode> children= null;
    public String getLabel(){
        return getName();
    }

}
