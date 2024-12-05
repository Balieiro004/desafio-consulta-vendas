package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<List<SaleReportDTO>> getReport(
			@RequestParam(value = "minDate", defaultValue = "") String minDateStr,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDateStr,
			@RequestParam(value = "name", defaultValue = "") String name) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate minDate = minDateStr.isEmpty() ? LocalDate.now().minusYears(1) : LocalDate.parse(minDateStr, formatter);
		LocalDate maxDate = maxDateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(maxDateStr, formatter);

		List<SaleReportDTO> report = service.getSalesReport(minDate, maxDate, name);
		return ResponseEntity.ok(report);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate startDate = minDate.isEmpty() ? today.minusYears(1) : LocalDate.parse(minDate);
		LocalDate endDate = maxDate.isEmpty() ? today : LocalDate.parse(maxDate);

		// Chama o serviço para o sumário de vendas
		return ResponseEntity.ok(service.getSalesSummary(startDate, endDate));
	}
}
