package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="Loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CustomerNumber")
    private String customerNumber;

    @ManyToOne
    @JoinColumn(name="CustomerNumber", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "BookCode")
    private String bookCode;

    @ManyToOne
    @JoinColumn(name="BookCode", insertable = false, updatable = false)
    private Book book;

    @Column(name = "LoanDate")
    private LocalDate loanDate;

    @Column(name = "DueDate")
    private LocalDate dueDate;

    @Column(name = "ReturnDate")
    private LocalDate returnDate;

    @Column(name = "Note")
    private String Note;

    public Loan(Long id, String customerNumber, String bookCode, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, String note) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.bookCode = bookCode;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        Note = note;
    }
}
