package ru.smartel.strike.dto.request.conflict;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class ConflictReportRequestDTO {
    @ApiParam(value = "Начальная дата отчета")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    @ApiParam(value = "Конечная дата отчета")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
    @ApiParam(value = "Идентификаторы стран. В отчет попадут конфликты, которые в отчетном периоде имеют события этих стран")
    private List<Long> countriesIds;

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
