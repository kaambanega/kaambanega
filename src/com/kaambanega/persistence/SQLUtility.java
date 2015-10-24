/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
public class SQLUtility {

    private static DBConnManager dbConnPoolManager = new DBConnManager();

    public int persist(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate.update(sqlQuery);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public int persist(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return npJdbcTemplate.update(sqlQuery, paramSource);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public int persist(final String aliasName, final String sqlQuery, KeyHolder generatedKeys, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return npJdbcTemplate.update(sqlQuery, paramSource, generatedKeys);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public int[] persistBatch(final String aliasName, final String sqlQuery[]) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate.batchUpdate(sqlQuery);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public int getInt(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate.queryForInt(sqlQuery);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public int getInt(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return npJdbcTemplate.queryForInt(sqlQuery, paramSource);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public long getLong(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate.queryForLong(sqlQuery);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public long getLong(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return npJdbcTemplate.queryForLong(sqlQuery, paramSource);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public String getString(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return (String) jdbcTemplate.queryForObject(sqlQuery, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public String getString(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            return (String) npJdbcTemplate.queryForObject(sqlQuery, paramSource, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public List getList(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            List l = jdbcTemplate.queryForList(sqlQuery);
            return l;
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public List getList(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            List l = npJdbcTemplate.queryForList(sqlQuery, paramSource);
            return l;
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public SqlRowSet getRowSet(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {


        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
            return srs;
        } catch (BadSqlGrammarException ex) {

            throw new SQLException(ex);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }


    }

    public SqlRowSet getRowSet(final String aliasName, final String sqlQuery, final SqlParameterSource paramSource) throws ClassNotFoundException, SQLException {

        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(ds);
            SqlRowSet srs = npJdbcTemplate.queryForRowSet(sqlQuery, paramSource);
            return srs;
        } catch (BadSqlGrammarException ex) {
            throw new SQLException(ex);

        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }


    }

    public ResultSet getResultSet(final String aliasName, final String sqlQuery) throws ClassNotFoundException, SQLException {
        return new ResultSetImpl(aliasName, sqlQuery);
    }

    public Map callProc(final String aliasName, final String procName, final Map paramValues, final ProcedureParam[] params) throws ClassNotFoundException, SQLException {

        CallStoredProcedure sproc = new CallStoredProcedure();
        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            return sproc.execute(ds, procName, paramValues, params);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    public Map callProcedure(final String aliasName, final String procName, final HashMap paramValues, final ProcedureParam[] params) throws ClassNotFoundException, SQLException {

        CallStoredProcedure sproc = new CallStoredProcedure();
        Connection conn = null;
        try {
            conn = dbConnPoolManager.getMySQLConnection(aliasName);
            DataSource ds = new SingleConnectionDataSource(conn, true);
            return sproc.execute(ds, procName, paramValues, params);
        } finally {
            dbConnPoolManager.releaseMySQLConnection(conn);
        }
    }

    /**
     * Procedure calling class
     */
    private class CallStoredProcedure extends StoredProcedure {

        /**
         *
         * @param ds Connection
         * @param procSQL Procedure SQL
         * @param paramValues Values to pass
         * @param params Parameters Info
         * @return Map
         */
        public Map execute(DataSource ds, String procSQL, Map paramValues, ProcedureParam[] params) {

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
