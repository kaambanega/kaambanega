/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Fenil Jariwala
 */
public class SQLTranUtility {

    private static DBConnManager dbcm = new DBConnManager();
    private Connection conn = null;

    public void openConn(final String alias) throws ClassNotFoundException, SQLException {
        conn = dbcm.getMySQLConnection(alias);
        conn.setAutoCommit(false);
    }

    public Connection getConnection() {
        return conn;
    }

    public void commitChanges() throws SQLException {
        conn.commit();
    }

    public void rollbackChanges() throws SQLException {
        conn.rollback();
    }

    public void closeConn() throws SQLException {
        conn.close();
    }

    public int persist(final String sqlQuery) {

        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.update(sqlQuery);
    }

    public int persist(final String sqlQuery, final SqlParameterSource paramSource) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.update(sqlQuery, paramSource);
    }

    public int persist(final String sqlQuery, KeyHolder generatedKeys, final SqlParameterSource paramSource) {

        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.update(sqlQuery, paramSource, generatedKeys);
    }

    public int[] persistBatch(final String[] sqlQuery) {

        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.batchUpdate(sqlQuery);
    }

    public int getInt(final String sqlQuery) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.queryForInt(sqlQuery);
    }

    public int getInt(final String sqlQuery, final SqlParameterSource paramSource) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.queryForInt(sqlQuery, paramSource);
    }

    public long getLong(final String sqlQuery) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.queryForLong(sqlQuery);
    }

    public long getLong(final String sqlQuery, final SqlParameterSource paramSource) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return npJdbcTemplate.queryForLong(sqlQuery, paramSource);
    }

    public String getString(final String sqlQuery) {


        try {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return (String) jdbcTemplate.queryForObject(sqlQuery, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getString(final String sqlQuery, final SqlParameterSource paramSource) {


        try {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return (String) npJdbcTemplate.queryForObject(sqlQuery, paramSource, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List getList(final String sqlQuery) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List l = jdbcTemplate.queryForList(sqlQuery);
        return l;
    }

    public List getList(final String sqlQuery, final SqlParameterSource paramSource) {


        DataSource ds = new SingleConnectionDataSource(conn, true);
        NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
        List l = npJdbcTemplate.queryForList(sqlQuery, paramSource);
        return l;
    }

    public SqlRowSet getRowSet(final String sqlQuery) {


        try {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
            return srs;
        } catch (BadSqlGrammarException ex) {

            throw new IllegalArgumentException(ex);
        }
    }

    public SqlRowSet getRowSet(final String sqlQuery, final SqlParameterSource paramSource) {


        try {
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            SqlRowSet srs = npJdbcTemplate.queryForRowSet(sqlQuery, paramSource);
            return srs;
        } catch (BadSqlGrammarException ex) {

            throw new IllegalArgumentException(ex);

        }

    }

    public ResultSet getResultSet(final String sqlQuery) throws ClassNotFoundException, SQLException {
        return new ResultSetImpl(conn, sqlQuery);
    }

    public Map callProcedure(final String procName, final Map paramValues, final ProcedureParam params[]) {

        CallStoredProcedure sproc = new CallStoredProcedure();

        DataSource ds = new SingleConnectionDataSource(conn, true);
        return sproc.execute(ds, procName, paramValues, params);
    }

    private class CallStoredProcedure extends StoredProcedure {

        public Map execute(DataSource ds, String procSQL, Map paramValues, ProcedureParam params[]) {

            setDataSource(ds);
            setSql(procSQL);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i].getParamInOutType().equalsIgnoreCase("IN")) {
                        declareParameter(new SqlParameter(params[i].getParamName(), params[i].getParamDataType()));
                    } else if (params[i].getParamInOutType().equalsIgnoreCase("OUT")) {
                        declareParameter(new SqlOutParameter(params[i].getParamName(), params[i].getParamDataType()));
                    } else if (params[i].getParamInOutType().equalsIgnoreCase("INOUT")) {
                        declareParameter(new SqlInOutParameter(params[i].getParamName(), params[i].getParamDataType()));
                    }
                }
            }
            compile();

            return execute(paramValues);
        }
    }
}
