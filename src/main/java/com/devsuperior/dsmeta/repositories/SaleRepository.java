package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, sel.name) " +
            "FROM Sale s JOIN s.seller sel " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "AND LOWER(sel.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<SaleReportDTO> findSalesReport(LocalDate minDate, LocalDate maxDate, String name);
//    @Query("SELECT s FROM Sale s JOIN s.seller sel " +
//            "WHERE s.date BETWEEN :minDate AND :maxDate " +
//            "AND LOWER(sel.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))")
//    Page<Sale> findSalesByDateAndSellerName(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);
    //Page<Sale> findByDateBetweenAndSellerNameContaining(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesSummaryDTO(sel.name, SUM(s.amount)) " +
            "FROM Sale s JOIN s.seller sel " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY sel.name")
    List<SalesSummaryDTO> findSalesSummaryBySeller(LocalDate minDate, LocalDate maxDate);
    //List<Sale> findByDateBetween(LocalDate minDate, LocalDate maxDate);
}

