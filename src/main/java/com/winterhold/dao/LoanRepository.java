package com.winterhold.dao;

import com.winterhold.dto.loan.LoanDetailDto;
import com.winterhold.dto.loan.LoanRowDto;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {
   @Query("""
    SELECT new com.winterhold.dto.loan.LoanRowDto(
        lo.id,
        CONCAT(cus.firstName,' ',cus.lastName),
        bk.title,
        lo.loanDate,
        lo.dueDate,
        lo.returnDate
    )
    FROM Loan AS lo
        JOIN lo.customer AS cus
        JOIN lo.book AS bk
    WHERE bk.title LIKE %:bookTitle% AND
    CONCAT(cus.firstName,' ',cus.lastName) LIKE %:customerName%
    
    """)
    public Page<LoanRowDto> getLoanTable(@Param("bookTitle") String bookTitle,
                                         @Param("customerName") String customerName,
                                         Pageable pageable);

   @Query("""
    SELECT new com.winterhold.dto.loan.LoanDetailDto(
        bk.title,
        cat.name,
        CONCAT(aut.title,'. ', aut.firstName,' ',aut.lastName),
        cat.floor,
        cat.isle,
        cat.bay,
        cus.membershipNumber,
        CONCAT(cus.firstName,' ',cus.lastName),
        cus.phone,
        cus.membershipExpireDate
    )
    FROM Loan AS lo
        JOIN  lo.book AS bk
        JOIN lo.customer AS cus
        JOIN bk.category AS cat
        JOIN bk.author AS aut
    WHERE lo.id = :id
    """)
   public LoanDetailDto getLoanDetail(@Param("id") Long id);

    @Query("""
        SELECT COUNT(lo.id)
        FROM Loan AS lo
        WHERE lo.customerNumber = :customerNumber
    """)
    public Long countByCustomerNumber(@Param("customerNumber") String customerNumber);

    @Query("""
        SELECT COUNT(lo.id)
        FROM Loan AS lo
        WHERE lo.bookCode = :code
    """)
    public Long countByBookCode(@Param("code") String code);
}
