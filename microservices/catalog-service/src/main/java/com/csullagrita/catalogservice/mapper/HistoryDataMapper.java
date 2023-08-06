package com.csullagrita.catalogservice.mapper;

import com.csullagrita.catalogservice.api.model.HistoryDataProductPriceHistoryDto;
import com.csullagrita.catalogservice.model.HistoryData;
import com.csullagrita.catalogservice.model.Product;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

    HistoryDataProductPriceHistoryDto productHistoryDataToDto(HistoryData<Product> productHistory);

    default OffsetDateTime dateToOffsetDateTime(@NotNull Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("Z"));
    }
}
