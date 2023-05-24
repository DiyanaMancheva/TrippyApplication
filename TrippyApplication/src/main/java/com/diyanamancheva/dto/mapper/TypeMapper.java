package com.diyanamancheva.dto.mapper;

import com.diyanamancheva.dto.type.TypeDto;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.repository.TypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeMapper {

  private static final Logger log = LoggerFactory.getLogger(TypeRepository.class);

  public List<TypeDto> mapTypesToDtos(Iterable<Type> types){
    List<TypeDto> typeDtos = new ArrayList<>();

    types.forEach(type -> typeDtos.add(mapTypeToDto(type)));
    return typeDtos;
  }

  public TypeDto mapTypeToDto(Type type) {

    return new TypeDto(type.getId(), type.getName());
  }
}
