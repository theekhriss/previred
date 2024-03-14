package com.previred.ext.jdbc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cristopher
 * @param <T>
 */
public abstract class GenericResultSetExtractor<T> {

    private final Class<T> clz;
    private ResultSetMapper<T> mapper;

    public GenericResultSetExtractor(Class<T> clz, ResultSetColumn[] columns) {
        this.clz = clz;
        if (columns != null) {
            this.mapper = ResultSetMapper.createResultSetMapper(clz, columns);
        }
    }

    public GenericResultSetExtractor(Class<T> clz) {
        this(clz, null);
    }

    @SuppressWarnings("unchecked")
	protected ResultSetMapper<T> getMapper(ResultSet rs) throws SQLException {
        if (mapper == null) {
            mapper = ResultSetMapper.guessResultSetMapper(clz, rs);
        }
        return mapper;
    }

}
