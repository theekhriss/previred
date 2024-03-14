/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.previred.ext.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

/**
 *
 * @author cristopher
 * @param <T>
 */
public class GenericListResultSetExtractor<T> extends GenericResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

    public GenericListResultSetExtractor(Class<T> clz, ResultSetColumn[] columns) {
        super(clz, columns);
    }

    public GenericListResultSetExtractor(Class<T> clz) {
        super(clz);
    }

    @Override
    public List<T> extractData(ResultSet rs) {
        List<T> objs = new ArrayList<>();
        try {
            while (rs.next()) {
                objs.add(getMapper(rs).populate(rs));
            }
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            throw new UncategorizedScriptException(ex.getMessage(), ex);
        }
        return objs;
    }

}
