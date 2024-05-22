package com.winterhold.dao;

import com.winterhold.dto.DropdownDTO;
import com.winterhold.dto.customer.CustomerRowDto;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("""
    SELECT new com.winterhold.dto.customer.CustomerRowDto(
        cus.membershipNumber,
        CONCAT(cus.firstName,' ',cus.lastName),
        cus.membershipExpireDate
    )
    FROM Customer AS cus
    WHERE CONCAT(cus.firstName,' ',cus.lastName) LIKE %:name% AND 
    cus.membershipNumber LIKE %:membershipNumber%
    """)
    public Page<CustomerRowDto> getCustomerTable (@Param("name") String name,
                                                  @Param("membershipNumber") String membershipNumber,
                                                  Pageable pageable);

    @Query("""
         SELECT new com.winterhold.dto.DropdownDTO(
             cus.membershipNumber, 
             CONCAT(cus.firstName,' ',cus.lastName)
         )
         FROM Customer AS cus
     """)
    public List<DropdownDTO> getDropdownList();
}
