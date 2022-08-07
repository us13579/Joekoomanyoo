package com.project.common.dto.My;


import com.project.common.entity.My.MyScheduleEntity;
import com.project.common.mapper.StructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MyScheduleMapper extends StructMapper<MyScheduleDto, MyScheduleEntity> {
    MyScheduleMapper MAPPER = Mappers.getMapper(MyScheduleMapper.class);

    @Override
    MyScheduleEntity toEntity(final MyScheduleDto myScheduleDto);

    @Override
    MyScheduleDto toDto(final MyScheduleEntity myScheduleEntity);
}
