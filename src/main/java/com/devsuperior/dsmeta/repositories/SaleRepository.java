package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

        @Query("SELECT new com.devsuperior.dsmeta.dto.SumaryDTO(obj.seller.name, SUM(obj.amount)) "
                        + "FROM Sale obj "
                        + "WHERE obj.date BETWEEN :minDate AND :maxDate "
                        + "GROUP BY obj.seller.id "
                        + "ORDER BY obj.seller.name")
        List<SumaryDTO> findSumary(LocalDate minDate, LocalDate maxDate);

        @Query("SELECT new com.devsuperior.dsmeta.dto.ReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
                        + "FROM Sale obj "
                        + "WHERE obj.date BETWEEN :minDate AND :maxDate  AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
        Page<ReportDTO> getReport(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);
}
