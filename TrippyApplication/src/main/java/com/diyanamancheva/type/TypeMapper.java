package com.diyanamancheva.type;

import com.diyanamancheva.exception.DatabaseConnectivityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TypeMapper {

  private static final Logger log = LoggerFactory.getLogger(TypeAccessor.class);

  public List<Type> mapResultSetToTypes (ResultSet typesResultSet){
    List<Type> types = new ArrayList<>();
    try(typesResultSet){
      while (typesResultSet.next()){
        int id = typesResultSet.getInt(1);
        String name = typesResultSet.getString(2);
        Type type = new Type(id, name);
        types.add(type);
      }
    }catch (SQLException e){
      log.error("Unexpected exception occurred when trying to query database. Rethrowing unchecked exception");
      throw new DatabaseConnectivityException(e);
    }

    return types;
  }

  public List<TypeDto> mapTypesToDtos(List<Type> types){
    List<TypeDto> typeDtos = new ArrayList<>();

    for (Type type : types){
      TypeDto typeDto = new TypeDto(type.getId(), type.getName());
      typeDtos.add(typeDto);
    }

    return typeDtos;
  }
}
