package com.diyanamancheva.configuration;

import com.diyanamancheva.exception.UninstantiableClassException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.diyanamancheva.configuration.DatabaseConfiguration.*;

public final class DataSource {

  private static final HikariConfig config = new HikariConfig();
  private static final HikariDataSource ds;

  static {
    config.setJdbcUrl(URL);
    config.setUsername(USER);
    config.setPassword(PASSWORD);
    config.addDataSourceProperty("cachePrepStmts", CACHE_PREPARED_STATEMENTS);
    config.addDataSourceProperty("prepStmtCacheSize", PREPARED_STATEMENT_CACHE_SIZE);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", PREPARED_STATEMENT_CACHE_SQL_LIMIT);
    ds = new HikariDataSource(config);
  }

  private DataSource() throws Exception{
    throw new UninstantiableClassException();
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}

