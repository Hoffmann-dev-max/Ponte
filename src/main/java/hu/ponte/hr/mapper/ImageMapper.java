package hu.ponte.hr.mapper;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public interface ImageMapper {
    ImageMeta toDto(ImageEntity entity);

    ImageEntity toEntity(ImageMeta dto);

}