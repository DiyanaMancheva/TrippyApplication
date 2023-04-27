package com.diyanamancheva.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
  private TypeAccessor typeAccessor;
  private TypeMapper typeMapper;

  @Autowired
  public TypeService(TypeAccessor typeAccessor, TypeMapper typeMapper){
    this.typeAccessor = typeAccessor;
    this.typeMapper = typeMapper;
  }

  public List<TypeDto> getAllTypes(){
    List<Type> types = typeAccessor.readAllTypes();
    List<TypeDto> typeDtos = typeMapper.mapTypesToDtos(types);

    return typeDtos;
  }

  public Type getTypeById(int id){
    Type type = typeAccessor.readTypeById(id);

    return type;
  }
}
