package com.diyanamancheva.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TypeController {
  private TypeService typeService;

  @Autowired
  public TypeController(TypeService typeService){
    this.typeService = typeService;
  }

  @GetMapping("/types")
  public ResponseEntity<List<TypeDto>> getAllTypes(){
    List<TypeDto> typeDtos = typeService.getAllTypes();

    return ResponseEntity.ok(typeDtos);
  }

  @GetMapping("/types/{id}")
  public ResponseEntity<TypeDto> getTypeById(@PathVariable int id){
    Type type = typeService.getTypeById(id);
    TypeDto typeDto = new TypeDto(type.getId(), type.getName());

    return ResponseEntity.ok(typeDto);
  }
}
