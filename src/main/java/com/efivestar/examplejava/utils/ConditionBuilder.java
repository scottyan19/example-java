package com.efivestar.examplejava.utils;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Select;
import org.jooq.impl.DSL;

import java.util.Collection;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
public class ConditionBuilder {
    private Condition condition;
    private Boolean isAnd;
    ConditionBuilder() {
        condition = null;
        isAnd = true;
    }
    private void addCondition(Condition c) {
        if(condition == null) {
            condition = c;
        } else if(isAnd){
            condition = condition.and(c);
        } else {
            condition = condition.or(c);
        }
    }
    public <T> ConditionBuilder eq(Boolean assertion, Field<T> field, T value) {
        if(assertion) {
            addCondition(field.eq(value));
        }
        return this;
    }

    public <T> ConditionBuilder gt(Boolean assertion, Field<T> field, T value) {
        if(assertion) {
            addCondition(field.gt(value));
        }
        return this;
    }

    public <T> ConditionBuilder lt(Boolean assertion, Field<T> field, T value) {
        if(assertion) {
            addCondition(field.lt(value));
        }
        return this;
    }

    public <T> ConditionBuilder ge(Boolean assertion, Field<T> field, T value) {
        if(assertion) {
            addCondition(field.ge(value));
        }
        return this;
    }

    public <T> ConditionBuilder le(Boolean assertion, Field<T> field, T value) {
        if(assertion) {
            addCondition(field.le(value));
        }
        return this;
    }

    public <T> ConditionBuilder between(Boolean assertion, Field<T> field, T value1, T value2) {
        if(assertion) {
            addCondition(field.between(value1, value2));
        }
        return this;
    }

    /**
     * 本方法还有其它实现. 如果实践中有别的需求, 再添加
     * @param assertion
     * @param field
     * @param value
     * @param <R>
     * @param <T>
     * @return
     */
    public <T> ConditionBuilder like(Boolean assertion, Field<T> field, String value) {
        if(assertion) {
            addCondition(field.like(value));
        }
        return this;
    }

    public <T> ConditionBuilder exists(Select<?> select) {
        addCondition(DSL.exists(select));
        return this;
    }

    public <T> ConditionBuilder notExists(Select<?> select) {
        addCondition(DSL.notExists(select));
        return this;
    }

    public <T> ConditionBuilder in(Boolean assertion, Field<T> field, Collection values) {
        if(assertion) {
            addCondition(field.in(values));
        }
        return this;
    }

    public <T> ConditionBuilder notIn(Boolean assertion, Field<T> field, Collection values) {
        if(assertion) {
            addCondition(field.notIn(values));
        }
        return this;
    }

    /**
     * 调用本方法后, 此后的所有条件均由and连接. 默认为and. 本方法默认不需要调用 .
     */
    public void and() {
        isAnd = true;
    }

    /**
     * 调用本方法后, 此后的所有条件均由or连接. 如果需要改回and, 应调用and方法
     */
    public void or() {
        isAnd = false;
    }

    public Condition toCondition() {
        if(condition == null) {
            return DSL.trueCondition();
        }
        return condition;
    }
}
