package com.diyanamancheva.repository;

import com.diyanamancheva.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
}