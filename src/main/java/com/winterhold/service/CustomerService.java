package com.winterhold.service;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.customer.CustomerRowDto;
import com.winterhold.dto.customer.UpsertCustomerDto;
import com.winterhold.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LoanRepository loanRepository;

    private final int rowsPerPage = 10;

    public Page<CustomerRowDto> getCustomerTable(String membershipNumber,String name, Integer page){
        var pageable = PageRequest.of(page - 1, rowsPerPage, Sort.by("id"));
        return customerRepository.getCustomerTable(name, membershipNumber, pageable);
    }

    public Customer saveCustomer(UpsertCustomerDto dto){
        var entity = new Customer(
                dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate()
        );

        return customerRepository.save(entity);
    }

    public UpsertCustomerDto getSingleCustomer(String membershipNumber){
        var selectedEntity = customerRepository.findById(membershipNumber).get();
        var dto = new UpsertCustomerDto(
                selectedEntity.getMembershipNumber(),
                selectedEntity.getFirstName(),
                selectedEntity.getLastName(),
                selectedEntity.getBirthDate(),
                selectedEntity.getGender(),
                selectedEntity.getPhone(),
                selectedEntity.getAddress(),
                selectedEntity.getMembershipExpireDate()
        );
        return dto;
    }

    public void deleteCustomer(String membershipNumber){
        customerRepository.deleteById(membershipNumber);
    }

    public Long totalDependentLoan(String membershipNumber){
        return loanRepository.countByCustomerNumber(membershipNumber);
    }

    public Customer extendExpiredDate(String membershipNumber){
        var customer = customerRepository.findById(membershipNumber).get();
        customer.setMembershipExpireDate(customer.getMembershipExpireDate().plusYears(2));

        return customerRepository.save(customer);
    }

    public Boolean isMembershipNumberValid(String membershipNumber){
        var foundEntity = customerRepository.findById(membershipNumber);
        return foundEntity.isEmpty();
    }
}
