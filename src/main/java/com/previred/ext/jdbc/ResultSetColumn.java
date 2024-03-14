/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.previred.ext.jdbc;

/**
 *
 * @author cristopher
 */
public class ResultSetColumn {
    private final String field;
    private final String column;
    private final ResultSetColumn[] mapper;
    private final boolean identity;

    public ResultSetColumn(String field, String column, boolean identity) {
        this.field = field;
        this.column = column;
        this.identity = identity;
        this.mapper = null;
    }

    public ResultSetColumn(String field, String column) {
        this(field, column, false);
    }

    public ResultSetColumn(String field, ResultSetColumn[] mapper) {
        this.field = field;
        this.column = null;
        this.identity = false;
        this.mapper = mapper;
    }

    protected String getField() {
        return field;
    }

    protected String getColumn() {
        return column;
    }

    protected ResultSetColumn[] getReference() {
        return mapper;
    }

    protected boolean isReference() {
        return column == null && mapper != null;
    }

    protected boolean isIdentity() {
        return identity;
    }
}
