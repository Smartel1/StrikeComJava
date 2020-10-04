package ru.smartel.strike.dto.request.conflict;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

public class ConflictReportRequestDTO {
    @ApiParam(value = "Начальная дата отчета", defaultValue = "0")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Long from = 0L;
    @ApiParam(value = "Конечная дата отчета", defaultValue = "сегодня")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Long to = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    @ApiParam(value = "Идентификаторы стран. В отчет попадут конфликты, которые в отчетном периоде имеют события этих стран", defaultValue = "пустой список")
    private List<Long> countriesIds = new LinkedList<>();

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public List<Long> getCountriesIds() {
        return countriesIds;
    }

    public void setCountriesIds(List<Long> countriesIds) {
        this.countriesIds = countriesIds;
    }
}
