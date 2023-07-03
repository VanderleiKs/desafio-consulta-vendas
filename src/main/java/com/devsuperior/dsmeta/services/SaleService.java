package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SumaryDTO> findSumary(String minDateString, String maxDateString) {
		LocalDate maxDate = getMaxDate(maxDateString);
		LocalDate minDate = getMinDate(minDateString, maxDate);
		return repository.findSumary(minDate, maxDate);
	}

	public Page<ReportDTO> getReport(String minDateString, String maxDateString, String sellerName, Pageable pageable) {
		LocalDate maxDate = getMaxDate(maxDateString);
		LocalDate minDate = getMinDate(minDateString, maxDate);
		return repository.getReport(minDate, maxDate, sellerName, pageable);
	}

	private LocalDate getMinDate(String minDateString, LocalDate maxDate) {
		return minDateString.equals("") ? maxDate.minusYears(1L) : LocalDate.parse(minDateString);
	}

	private LocalDate getMaxDate(String maxDateString) {
		return maxDateString.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDateString);
	}
}
