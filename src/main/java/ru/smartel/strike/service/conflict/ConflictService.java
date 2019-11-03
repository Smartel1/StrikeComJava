package ru.smartel.strike.service.conflict;

import ru.smartel.strike.dto.request.conflict.ConflictListRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictCreateRequestDTO;
import ru.smartel.strike.dto.request.conflict.ConflictUpdateRequestDTO;
import ru.smartel.strike.dto.response.ListWrapperDTO;
import ru.smartel.strike.dto.response.conflict.ConflictDetailDTO;
import ru.smartel.strike.dto.response.conflict.ConflictListDTO;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.DTOValidationException;
import ru.smartel.strike.service.Locale;

public interface ConflictService {

    ListWrapperDTO<ConflictListDTO> list(ConflictListRequestDTO dto)
            throws DTOValidationException;

    ConflictDetailDTO get(Integer conflictId, Locale locale);

    ConflictDetailDTO create(ConflictCreateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    ConflictDetailDTO update(ConflictUpdateRequestDTO dto)
            throws BusinessRuleValidationException, DTOValidationException;

    void delete(Integer conflictId) throws BusinessRuleValidationException;
}
