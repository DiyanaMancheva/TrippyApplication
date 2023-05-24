package com.diyanamancheva.service;

import com.diyanamancheva.dto.mapper.TypeMapper;
import com.diyanamancheva.dto.type.TypeDto;
import com.diyanamancheva.exception.EntityNotFoundException;
import com.diyanamancheva.model.Type;
import com.diyanamancheva.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
  private TypeRepository typeRepository;
  private TypeMapper typeMapper;

  @Autowired
  public TypeService(TypeRepository typeRepository, TypeMapper typeMapper){
    this.typeRepository = typeRepository;
    this.typeMapper = typeMapper;
  }

  public List<TypeDto> getAllTypes(){
    Iterable<Type> types = typeRepository.findAll();
    List<TypeDto> typeDtos = typeMapper.mapTypesToDtos(types);

    return typeDtos;
  }

  public Type getTypeById(int id){
    Type type = typeRepository.findById(id)
                              .orElseThrow(() -> new EntityNotFoundException(String.format("Type with id %d not found", id)));;

    return type;
  }
}
