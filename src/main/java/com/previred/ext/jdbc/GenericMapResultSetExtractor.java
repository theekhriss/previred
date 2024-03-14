/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.previred.ext.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

/**
 *
 * @author cristopher
 * @param <K>
 * @param <T>
 */
public class GenericMapResultSetExtractor<K, T> extends GenericResultSetExtractor<T> implements ResultSetExtractor<Map<K, T>> {

    private final String columnKey;

    public GenericMapResultSetExtractor(String columnKey, Class<T> clz, ResultSetColumn[] columns) {
        super(clz, columns);
        this.columnKey = columnKey;
    }

    public GenericMapResultSetExtractor(String columnKey, Class<T> clz) {
        this(columnKey, clz, null);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map<K, T> extractData(ResultSet rs) {
        Map<K, T> objs = new HashMap<>();
        try {
            while (rs.next()) {
                objs.put((K) getMapper(rs).getValue(rs, columnKey), getMapper(rs).populate(rs));
            }
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            throw new UncategorizedScriptException(ex.getMessage(), ex);
        }
        return objs;
    }

}
