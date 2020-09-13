package ru.smartel.strike.dto.request.conflict;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ConflictReportRequestDTO {
    @ApiParam(value = "Начальная дата отчета", defaultValue = "1970-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from = LocalDate.EPOCH;
    @ApiParam(value = "Конечная дата отчета", defaultValue = "сегодня")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to = LocalDate.now();
    @ApiParam(value = "Идентификаторы стран. В отчет попадут конфликты, которые в отчетном периоде имеют события этих стран", defaultValue = "пустой список")
    private List<Long> countriesIds = new LinkedList<>();

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<Long> getCountriesIds() {
        return countriesIds;
    }

    public void setCountriesIds(List<Long> countriesIds) {
        this.countriesIds = countriesIds;
    }
}
