package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.DropdownDTO;
import com.winterhold.dto.book.UpsertBookDto;
import com.winterhold.dto.customer.CustomerRowDto;
import com.winterhold.dto.customer.UpsertCustomerDto;
import com.winterhold.dto.loan.LoanDetailDto;
import com.winterhold.dto.loan.LoanRowDto;
import com.winterhold.dto.loan.UpsertLoanDto;
import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import com.winterhold.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    private final int rowsPerPage = 10;

    public Page<LoanRowDto> getLoanTable(String bookTitle, String customerName, Integer page){
        var pageable = PageRequest.of(page - 1, rowsPerPage, Sort.by("id"));
        return loanRepository.getLoanTable( bookTitle,  customerName,pageable);
    }

    public List<DropdownDTO> getBookDropdownList(){
        return bookRepository.getDropdownList();
    }

    public List<DropdownDTO> getCustomerDropdownList(){
        return customerRepository.getDropdownList();
    }

    public Loan saveLoan(UpsertLoanDto dto){
        if(dto.getId() != null){
            var dtoBook = getSingleBook(getSingleLoan(dto.getId()).getBookCode());
            dtoBook.setIsBorowwed(false);
            saveBook(dtoBook);
        }

        var loanEntity = new Loan(
                dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dto.getLoanDate().plusDays(5),
                dto.getReturnDate(),
                dto.getNote()
        );

        var dtoBook = getSingleBook(dto.getBookCode());
        dtoBook.setIsBorowwed(true);
        saveBook(dtoBook);

        return loanRepository.save(loanEntity);
    }

    public UpsertLoanDto getSingleLoan(Long id){
        var selectedEntity = loanRepository.findById(id).get();
        var dto = new UpsertLoanDto(
                selectedEntity.getId(),
                selectedEntity.getCustomerNumber(),
                selectedEntity.getBookCode(),
                selectedEntity.getLoanDate(),
                selectedEntity.getReturnDate(),
                selectedEntity.getNote()
        );
        return dto;
    }

    private UpsertBookDto getSingleBook(String code){
        var selectedEntity = bookRepository.findById(code).get();
        var dto = new UpsertBookDto(
                selectedEntity.getCode(),
                selectedEntity.getTitle(),
                selectedEntity.getCategoryName(),
                selectedEntity.getAuthorId(),
                selectedEntity.getIsBorrowed(),
                selectedEntity.getSummary(),
                selectedEntity.getReleaseDate(),
                selectedEntity.getTotalPage()
        );
        return dto;
    }

    private Book saveBook(UpsertBookDto dto){
        var entity = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorowwed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage()
        );

        return bookRepository.save(entity);
    }

    public String getSelectedBookTitle(String code){
        return bookRepository.findById(code).get().getTitle();
    }

    public String getSelectedMemberName(String membershipNumber){
        var selectedCustomer = customerRepository.findById(membershipNumber).get();

        return String.format("%s %s", selectedCustomer.getFirstName(), selectedCustomer.getLastName());
    }

    public LoanDetailDto getLoanDetail(Long id){
        return loanRepository.getLoanDetail(id);
    }

    public Loan returnBook(Long id){
        var currentLoan = loanRepository.findById(id).get();
        var book = bookRepository.findById(currentLoan.getBookCode()).get();
        book.setIsBorrowed(false);
        bookRepository.save(book);

        currentLoan.setReturnDate(LocalDate.now());
        return loanRepository.save(currentLoan);
    }

}
