package com.backend.demo.controllers;

import com.backend.demo.models.Company;
import com.backend.demo.models.Equipment;
import com.backend.demo.repos.CompanyRepository;
import com.backend.demo.repos.EquipmentRepository;
import com.backend.demo.services.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "equipment controller", description = "crud операции с оборудованием")
@RestController
public class EquipmentController {
    private EquipmentRepository equipmentRepository;
    private EquipmentService equipmentService;
    private CompanyRepository companyRepository;

    @Autowired

    public EquipmentController(EquipmentRepository equipmentRepository, EquipmentService equipmentService, CompanyRepository companyRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentService = equipmentService;
        this.companyRepository = companyRepository;
    }

    @PostMapping(value = "/equipment/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Equipment> addNewEquipment(@RequestBody Equipment equipment){
        Equipment equipmentDetails = equipmentService.loadDeviceByLocation(equipment.getLocation());

        if (equipmentDetails != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            equipmentService.addEquipment(equipment);
            return ResponseEntity.ok(equipment);
        }
    }

    @DeleteMapping(value = "/equipment/delete/{id}")
    public Equipment deleteDeviceById(@PathVariable Long id) {
        equipmentRepository.deleteById(id);
        return null;
    }

    @GetMapping(value = "/equipment")
    public List<Equipment> findAllEquipment(){
        return (List<Equipment>) equipmentRepository.findAll();
    }

    @GetMapping(value = "/equipment/{id}")
    public Equipment findEquipmentById(@PathVariable Long id) {
        return equipmentRepository.findById(id).get();
    }

    @ApiOperation(value = "возвращает все оборудование конкретной компании")
    @GetMapping(value = "/api/equipment/{company_id}")
    public ResponseEntity<List<Equipment>> findEquipmentByCompany(@PathVariable Long company_id){
        Company company = companyRepository.findById(company_id).get();
        if (company == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(equipmentService.findByCompany(company), HttpStatus.OK);
        }
    }
}
