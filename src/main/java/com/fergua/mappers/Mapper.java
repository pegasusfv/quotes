package com.fergua.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class Mapper<Dto, Entity> {

  private final ModelMapper modelMapper;

  public Mapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public Dto mapTo(Entity entity, Class<Dto> dtoClass) {
    return modelMapper.map(entity, dtoClass);
  }

  public Entity mapFrom(Dto dto, Class<Entity> entityClass) {
    return modelMapper.map(dto, entityClass);
  }
}
