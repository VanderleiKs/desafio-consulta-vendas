package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public record ReportDTO(
        Long id,
        LocalDate date,
        Double amount,
        String sellerName) {
}
