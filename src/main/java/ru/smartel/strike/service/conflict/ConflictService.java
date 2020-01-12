package ru.smartel.strike.service.conflict;

import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.dto.response.reference.locality.ExtendedLocalityDTO;
import ru.smartel.strike.service.Locale;

public interface ConflictService {

    ListWrapperDTO<ConflictListDTO> list(ConflictListRequestDTO dto);

    ConflictDetailDTO get(long conflictId, Locale locale);

    ExtendedLocalityDTO getLatestCoordinates(long conflictId, Locale locale);

    ConflictDetailDTO create(ConflictCreateRequestDTO dto);

    ConflictDetailDTO update(ConflictUpdateRequestDTO dto);

    void delete(long conflictId);
}
