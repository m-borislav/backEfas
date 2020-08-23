package com.backend.demo.repos;

import com.backend.demo.models.Company;
import com.backend.demo.models.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    Equipment findByLocation(String location);
    List<Equipment> findByCompany(Company company);
}
