package com.efivestar.examplejava.utils;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
@Component
public class JqBuilder {
    @Resource
    private DataSource dataSource;
    private DSLContext ctx;

    public DSLContext getContext() {
        if(ctx == null)  {
            ctx = DSL.using(dataSource, SQLDialect.MYSQL);
            var settings = ctx.configuration().settings();
            settings.withRenderSchema(false);
        }
        return ctx;
    }

    public ConditionBuilder getConditionBuilder() {
        return new ConditionBuilder();
    }

    public Collection<SortField<?>> getSortFields(Sort sortSpecification, Object table) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();

        if (sortSpecification == null) {
            return querySortFields;
        }

        for (Sort.Order specifiedField : sortSpecification) {
            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();

            TableField tableField = getTableField(sortFieldName, table);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }

        return querySortFields;
    }

    private TableField getTableField(String sortFieldName, Object table) {
        TableField sortField = null;
        try {
            java.lang.reflect.Field tableField = table.getClass().getField(sortFieldName.toUpperCase());
            sortField = (TableField) tableField.get(table);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = String.format("Could not find table field: %s", sortFieldName);
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return sortField;
    }

    private SortField<?> convertTableFieldToSortField(TableField tableField, Sort.Direction sortDirection) {
        if (sortDirection == Sort.Direction.ASC) {
            return tableField.asc();
        }
        else {
            return tableField.desc();
        }
    }
}
