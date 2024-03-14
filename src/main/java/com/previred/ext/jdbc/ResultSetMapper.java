/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.previred.ext.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

/**
 *
 * @author cristopher
 * @param <T>
 */
public class ResultSetMapper<T> {

    private final Class<T> clz;
    private final Map<Field, Object> columns = new HashMap<>();

    private ResultSetMapper(Class<T> clz) {
        this.clz = clz;
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public static <T> ResultSetMapper<T> createResultSetMapper(Class<T> clz, ResultSetColumn[] columns) {
		
		ResultSetMapper rsm = new ResultSetMapper(clz);
        if (columns != null) {
            Field field;
            List<Field> fields = getFields(clz);
            for (ResultSetColumn rsc : columns) {
                field = getField(rsc.getField(), fields);
                field.setAccessible(true);
                if (rsc.isReference()) {
                    rsm.columns.put(field, createResultSetMapper(field.getType(), rsc.getReference()));
                } else {
                    rsm.columns.put(field, rsc.getColumn());
                }
            }
        }
        return rsm;
    }
    @SuppressWarnings("rawtypes")
    protected static ResultSetMapper guessResultSetMapper( Class clz, ResultSet rs) throws SQLException {
        return guessResultSetMapper(clz, "", getColumns(rs));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void guessResultSetFields(ResultSetMapper rsm, String prefix, List<Field> fields, List<String> columns) {
        String column;
        List<Field> matchedFields = new ArrayList<>();
        for (Field field : fields) {
            column = findColumn(normalizeField(field.getName(), prefix), columns);
            if (column != null) {
                matchedFields.add(field);
                columns.remove(column);
                field.setAccessible(true);
                rsm.columns.put(field, column);
            }
        }
        fields.removeAll(matchedFields);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void guessResultSetReferences(ResultSetMapper rsm, String prefix, List<Field> fields, List<String> columns) {
        List<String> possibleColumns;
        List<Field> matchedFields = new ArrayList<>();
        for (Field field : fields) {
            possibleColumns = new ArrayList<>();
            for (String ocolumn : columns) {
                if (ocolumn.startsWith(normalizeField(field.getName(), prefix))) {
                    possibleColumns.add(ocolumn);
                }
            }
            if (!possibleColumns.isEmpty()) {
                matchedFields.add(field);
                columns.removeAll(possibleColumns);
                field.setAccessible(true);
                rsm.columns.put(field, guessResultSetMapper(field.getType(), normalizeField(field.getName(), prefix) + "_", possibleColumns));
            }
        }
        fields.removeAll(matchedFields);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static ResultSetMapper guessResultSetMapper(Class clz, String prefix, List<String> columns) {
        ResultSetMapper rsm = new ResultSetMapper(clz);
        if (!columns.isEmpty()) {
            List<Field> fields = getFields(clz);
            guessResultSetFields(rsm, prefix, fields, columns);
            Collections.sort(fields, (Field o1, Field o2) -> -1 * ((Integer) o1.getName().length()).compareTo(o2.getName().length()));
            guessResultSetReferences(rsm, prefix, fields, columns);
            if (!columns.isEmpty()) {
                throw new UncategorizedScriptException(String.format("no se encontraron los campos: %s", columns.toString()));
            }
        }
        return rsm;
    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
	public T populate(ResultSet rs) throws InstantiationException, IllegalAccessException, SQLException {
        T object = clz.newInstance();
        Field field;
        Object column;
        Object value;
        Boolean exist = false;
        for (Map.Entry<Field, Object> entry : columns.entrySet()) {
            field = entry.getKey();
            column = entry.getValue();
            if (column instanceof ResultSetMapper) {
                value = ((ResultSetMapper) column).populate(rs);
            } else {
                value = getValue(rs, (String) column);
            }
            exist = exist || value != null;
            field.set(object, value);
        }
        return exist ? object : null;
    }

    public Object getValue(ResultSet rs, String column) throws SQLException {
        return rs.getObject(column);
    }

    protected static List<String> getColumns(ResultSet rs) throws SQLException {
        List<String> columns = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        for (int ci = 1; ci <= md.getColumnCount(); ci++) {
            columns.add(md.getColumnName(ci));
        }
        return columns;
    }

    private static String normalizeField(String field, String prefix) {
        return (prefix + field).replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase(Locale.US);
    }

    protected static String findColumn(String field, List<String> columns) {
        for (String column : columns) {
            if (column.equals(field)) {
                return column;
            }
        }
        return null;
    }

    protected static Field getField(String name, List<Field> fields) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        throw new UncategorizedScriptException("no existe el atributo " + name);
    }

    @SuppressWarnings("rawtypes")
	protected static List<Field> getFields(Class cl) {
        List<Field> fields = new ArrayList<>();
        if (cl != null) {
            fields.addAll(getFields(cl.getSuperclass()));
            Field[] sfields = cl.getDeclaredFields();
            fields.addAll(Arrays.asList(sfields));
        }
        return fields;
    }

}
