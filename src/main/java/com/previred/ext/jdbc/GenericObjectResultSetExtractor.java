/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.previred.ext.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

/**
 *
 * @author cristopher
 * @param <T>
 */
public class GenericObjectResultSetExtractor<T> extends GenericResultSetExtractor<T> implements ResultSetExtractor<T> {

    public GenericObjectResultSetExtractor(Class<T> clz, ResultSetColumn[] columns) {
        super(clz, columns);
    }

    public GenericObjectResultSetExtractor(Class<T> clz) {
        super(clz);
    }

    @Override
    public T extractData(ResultSet rs) {
        try {
            return rs.next() ? getMapper(rs).populate(rs) : null;
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            throw new UncategorizedScriptException(ex.getMessage(), ex);
        }
    }

}
