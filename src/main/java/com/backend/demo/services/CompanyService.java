package com.backend.demo.services;

import com.backend.demo.exceptions.CompanyNotFoundException;
import com.backend.demo.models.Company;
import com.backend.demo.models.User;
import com.backend.demo.repos.CompanyRepository;
import com.backend.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    @Autowired

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company addCompanyToUser(Company company, Long user_id){
        /*Company companyFromDb = companyRepository.findById(company.getId()).get();

        if (companyFromDb != null){
            return companyFromDb;
        } else {*/
            User user = userRepository.findById(user_id).get();
//            Company newCompany = new Company();
//            newCompany.setName(company.getName());
//            newCompany.setLocation(company.getLocation());
            company.setUser(user);
            companyRepository.save(company);
            return company;
        //}
    }

    public Company loadCompanyById(Long id) throws CompanyNotFoundException {
        return companyRepository.findById(id).orElse(new Company());
    }

    public List<Company> findByUser(User user){
        return (List<Company>) companyRepository.findByUser(Optional.ofNullable(user));
    }
}
