package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
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

	public List<SaleReportDTO> getSalesReport(LocalDate minDate, LocalDate maxDate, String name) {
		//Page<Sale> result = repository.findByDateBetweenAndSellerNameContaining(minDate, maxDate, sellerName, PageRequest.of(0, 10)); // Exemplo de paginamento com 10 itens por página
		return repository.findSalesReport(minDate, maxDate, name);
//		Page<Sale> result = repository.findSalesByDateAndSellerName(minDate, maxDate, sellerName, PageRequest.of(0, 10)); // Exemplo de paginamento com 10 itens por página
//		return result.map(SaleMinDTO::new).getContent();
	}

	public List<SalesSummaryDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
		return repository.findSalesSummaryBySeller(minDate, maxDate);
	}
//	public Map<String, Double> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
//		List<Sale> sales = repository.findByDateBetween(minDate, maxDate);
//
//		Map<String, Double> summary = new HashMap<>();
//		for (Sale sale : sales) {
//			String sellerName = sale.getSeller().getName();
//			summary.put(sellerName, summary.getOrDefault(sellerName, 0.0) + sale.getAmount());
//		}
//		return summary;
//	}
}
