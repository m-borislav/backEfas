package com.backend.demo.controllers;


import com.backend.demo.models.Device;
import com.backend.demo.models.Equipment;
import com.backend.demo.repos.DeviceRepository;
import com.backend.demo.repos.EquipmentRepository;
import com.backend.demo.services.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "device controller", description = "crud операции сенсоров")
@RestController
public class DeviceController {

    private DeviceRepository deviceRepository;
    private DeviceService deviceService;
    private EquipmentRepository equipmentRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository, DeviceService deviceService, EquipmentRepository equipmentRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceService = deviceService;
        this.equipmentRepository = equipmentRepository;
    }


    @GetMapping(value = "/device/{id}")
    public Device findDeviceById(@PathVariable Long id) {
        return deviceRepository.findById(id).get();
    }

    @ApiOperation(value = "возвращает все существующие сенсоры")
    @GetMapping(value = "/device")
    public List<Device> findAllDevices() {
        return (List<Device>) deviceRepository.findAll();
    }

    // TO DO: test after adding equipment

    @ApiOperation(value = "возвращает все сенсоры конкретного оборудования")
    @GetMapping(value = "/equipment/{equipment_id}/device")
    public ResponseEntity<List<Device>> findDevicesByEquipment(@PathVariable Long equipment_id) {
        Equipment equipment = equipmentRepository.findById(equipment_id).get();
        if (equipment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(deviceService.findByEquipment(equipment), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/device/{id}")
    public Device deleteDeviceById(@PathVariable Long id) {
        deviceRepository.deleteById(id);
        return null;
    }

    @ApiOperation(value = "добавляет новый сенсор")
    @PostMapping(value = "/device/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> addNewDevice(@RequestBody Device device) {
        Device deviceDetails = deviceService.loadDeviceByPlace(device.getPlace());

        if (deviceDetails != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            deviceService.addDevice(device);
            return ResponseEntity.ok(device);
        }

    }


    /*@GetMapping(value = "/deviceType/device")
    public Device findDeviceByType(@RequestBody DeviceType deviceType){
        return  Optional.of(deviceRepository.findByDeviceType_Name(deviceType)).orElse(new Device());
    }*/

    /*@GetMapping(value = "/equipment/device")
    public Device findDeviceByEquipment(@RequestBody Equipment equipment) {
        return Optional.of(deviceRepository.findByEquipmentOrderByDeviceType(equipment)).orElse(new Device());
    }*/

    /*@PostMapping(value = "/device/measuring")
    public ResponseEntity<Device> addMeasuring(@RequestBody DeviceDto device){
        deviceService.amperageMeasuring(device);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}