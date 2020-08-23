package com.backend.demo.repos;

import com.backend.demo.models.Company;
import com.backend.demo.models.Device;
import com.backend.demo.models.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findByEquipment(Equipment equipment);

    Device findByPlace(String place);
}
