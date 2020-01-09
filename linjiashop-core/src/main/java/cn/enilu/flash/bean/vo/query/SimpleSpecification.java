package cn.enilu.flash.bean.vo.query;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Date;

/**
 * @author ：enilu
 * @date ：Created in 12/10/2019 8:59 PM
 */
public class SimpleSpecification <T> implements Specification<T> {

    /**
     * 查询的条件列表，是一组列表
     */
    private Collection<SearchFilter> filters;

    public SimpleSpecification(Collection<SearchFilter> operators) {
        this.filters = operators;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (filters !=null && !filters.isEmpty()) {
            int index = 0;
            //通过resultPre来组合多个条件
            Predicate resultPre = null;
            for (SearchFilter op : filters) {
                if (index++ == 0) {
                    resultPre = generatePredicate(root, cb, op);
                    continue;
                }
                Predicate pre = generatePredicate(root, cb, op);
                if (pre == null) {
                    continue;
                }
                switch (op.join) {
                    case and:
                        resultPre = cb.and(resultPre, pre);
                        break;
                    case or:
                        resultPre = cb.or(resultPre, pre);
                        break;
                    default:
                        break;
                }
            }
            return resultPre;
        }
       return  cb.conjunction();
    }

    /**
     * 根据不同的操作符返回特定的查询
     *
     * @param root root
     * @param cb   条件构造器
     * @param op   操作对象
     * @return 逻辑表达式
     */
    private Predicate generatePredicate(Root<T> root, CriteriaBuilder cb, SearchFilter op) {
        Object value = op.value;
        switch (op.operator) {
            case EQ:
                return cb.equal(root.get(op.fieldName), value);
            case NE:
                return cb.notEqual(root.get(op.fieldName), value);
            case GTE:
                return cb.greaterThanOrEqualTo(root.get(op.fieldName), (Comparable) value);
            case LTE:
                return cb.lessThanOrEqualTo(root.get(op.fieldName) , (Comparable) value);
            case GT:
                return cb.greaterThan(root.get(op.fieldName), (Comparable) value);
            case LT:
                return cb.lessThan(root.get(op.fieldName), (Comparable) value);
            case IN:
                if(value.getClass().isArray()){
                    return root.get(op.fieldName).in((Object[]) value);
                } else{
                    return root.get(op.fieldName).in((Collection) value);
                }
            case NOTIN:
                if (value instanceof Collection) {
                    return cb.not(root.get(op.fieldName).in((Collection) value));
                }
                return cb.not(root.get(op.fieldName).in(value));
            case LIKE:
                return cb.like(root.get(op.fieldName).as(String.class), "%" + value + "%");
            case LIKEL:
                return cb.like(root.get(op.fieldName).as(String.class), value + "%");
            case LIKER:
                return cb.like(root.get(op.fieldName).as(String.class), "%" + value);
            case ISNULL:
                return cb.isNull(root.get(op.fieldName));
            case ISNOTNULL:
                return cb.isNotNull(root.get(op.fieldName));

            case BETWEEN:
                if (value instanceof Date[]) {
                    Date[] dateArray = (Date[]) value;
                    return cb.between(root.get(op.fieldName), dateArray[0], dateArray[1]);
                }
            default:
                return null;
        }
    }
}
