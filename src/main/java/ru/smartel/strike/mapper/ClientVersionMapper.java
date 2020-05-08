package ru.smartel.strike.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.smartel.strike.dto.request.client_version.ClientVersionCreateRequestDTO;
import ru.smartel.strike.entity.ClientVersion;

@Mapper(componentModel = "spring")
public interface ClientVersionMapper {
    ClientVersionMapper INSTANCE = Mappers.getMapper(ClientVersionMapper.class);

    ClientVersion ClientVersionCreateRequestDTOToClientVersion(ClientVersionCreateRequestDTO clientVersionCreateRequestDTO);
}
