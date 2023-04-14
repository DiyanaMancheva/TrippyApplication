package com.diyanamancheva.configuration;

import com.diyanamancheva.exception.UninstantiableClassException;
public final class DatabaseConfiguration {
  private DatabaseConfiguration() throws UninstantiableClassException {
    throw new UninstantiableClassException();
  }
  static final String URL = "jdbc:postgresql://localhost:5432/LibraryManagement?currentSchema=public";
  static final String USER = "postgres";
  static final String PASSWORD = "St713201";
  static final String CACHE_PREPARED_STATEMENTS = "true";
  static final String PREPARED_STATEMENT_CACHE_SIZE = "250";
  static final String PREPARED_STATEMENT_CACHE_SQL_LIMIT = "2048";

}
