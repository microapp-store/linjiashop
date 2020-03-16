package cn.enilu.flash.service;

import cn.enilu.flash.BaseApplicationStartTest;
import cn.enilu.flash.bean.entity.system.Dept;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.system.DeptService;
import cn.enilu.flash.utils.Lists;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2020/3/13 13:26
 */
public class BaseServiceTest extends BaseApplicationStartTest {
    @Autowired
    private DeptService deptService;
    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void delete1() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void get() {
    }

    @Test
    public void get1() {
    }

    @Test
    public void get2() {
    }

    @Test
    public void get3() {
    }

    @Test
    public void query() {
    }

    @Test
    public void queryAll() {
        List<SearchFilter> filters = Lists.newArrayList();
        filters.add(SearchFilter.build("pid",1));
        filters.add(SearchFilter.build("pid",0, SearchFilter.Join.or));
        List<Dept> list = deptService.queryAll(filters);
        System.out.println(Json.toJson(list));
    }

    @Test
    public void queryBySql() {
    }

    @Test
    public void getMapBySql() {
    }

    @Test
    public void queryPage() {
    }

    @Test
    public void queryAll1() {
    }

    @Test
    public void queryAll2() {
    }

    @Test
    public void queryAll3() {
    }

    @Test
    public void queryAll4() {
    }

    @Test
    public void count() {
    }

    @Test
    public void count1() {
    }

    @Test
    public void count2() {
    }

    @Test
    public void update() {
    }

    @Test
    public void update1() {
    }

    @Test
    public void clear() {
    }
}
