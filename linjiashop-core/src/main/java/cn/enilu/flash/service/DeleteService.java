package cn.enilu.flash.service;

/**
 *
 * @author ：enilu
 * @date ：Created in 2019/6/29 22:29
 */
public interface DeleteService<T,ID> {
    /**
     * 删除指定实体
     * @param record
     */
    void  delete(T record);
    /**
     * 根据主键删除记录
     *
     * @param id 主键
     */
    void deleteById(ID id);

    /**
     * 根据主键删除记录
     *
     * @param ids 主键集合
     */
    void delete(Iterable<ID> ids);

    /**
     * 删除指定的数据列表
     * @param list
     */
    void deleteAll(Iterable<T> list);
    /**
     * 清空表数据
     */
    void clear();

}
