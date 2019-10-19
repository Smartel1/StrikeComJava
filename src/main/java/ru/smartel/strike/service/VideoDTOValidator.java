package ru.smartel.strike.service;

import ru.smartel.strike.dto.request.video.VideoDTO;
import ru.smartel.strike.exception.DTOValidationException;

import java.util.Map;

public interface VideoDTOValidator {
    void validate(VideoDTO dto) throws DTOValidationException;
    Map<String, String> getErrors(VideoDTO dto);
}
