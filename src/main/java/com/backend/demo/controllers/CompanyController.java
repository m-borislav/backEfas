package com.backend.demo.controllers;

import com.backend.demo.models.Company;
import com.backend.demo.models.User;
import com.backend.demo.repos.CompanyRepository;
import com.backend.demo.repos.UserRepository;
import com.backend.demo.services.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "company methods", description = "crud перации с компаниями")
@RestController
public class CompanyController {
    private CompanyRepository companyRepository;
    private CompanyService companyService;
    private UserRepository userRepository;

    @Autowired

    public CompanyController(CompanyRepository companyRepository, CompanyService companyService, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "добавить новую компанию")
    @PostMapping(value = "/api/company/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Company> addNewCompany(@RequestBody Company company){
        /*Company companyDetails = companyService.loadCompanyById(company.getId());

        if (companyDetails != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {*/
            companyService.addCompany(company);
            return ResponseEntity.ok(company);
        //}


    }
    @DeleteMapping(value = "/company/delete/{id}")
    public Company deleteCompanyById(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return null;
    }

   /* @GetMapping(value = "/company")
    public List<Company> findAllDevices(){
        return (List<Company>) companyRepository.findAll();
    }*/

    /*@GetMapping(value = "/company/{id}")
    public Company findDeviceById(@PathVariable Long id) {
        return companyRepository.findById(id).get();
    }*/

    @ApiOperation(value = "возвращает все компании залогиненого юзера")
    @GetMapping(value = "/api/company/{user_id}")
    public ResponseEntity<List<Company>> findAllCompanies(@PathVariable Long user_id){
        User user = userRepository.findById(user_id).get();
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(companyService.findByUser(user), HttpStatus.OK);
        }
    }
}
