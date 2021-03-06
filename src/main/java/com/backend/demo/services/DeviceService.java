package com.backend.demo.services;

import com.backend.demo.exceptions.DeviceNotFoundException;
import com.backend.demo.exceptions.EquipmentNotFoundException;
import com.backend.demo.models.Device;
import com.backend.demo.models.Equipment;
import com.backend.demo.repos.DeviceRepository;
import com.backend.demo.repos.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceService {
    private DeviceRepository deviceRepository;
    private EquipmentRepository equipmentRepository;


    @Autowired
    public DeviceService(DeviceRepository deviceRepository, EquipmentRepository equipmentRepository) {
        this.deviceRepository = deviceRepository;
        this.equipmentRepository = equipmentRepository;
    }

  /* public Device addDevice(Device device){
        Device deviceFromDb = deviceRepository.findByPlace(device.getPlace());
        if (deviceFromDb != null){
            return deviceFromDb;
        } else{
            Device newDevice = new Device();
            newDevice.setPlace(device.getPlace());
            newDevice.setDeviceType(device.getDeviceType());
            deviceRepository.save(newDevice);
            return newDevice;
        }
   }
*/

    public Device addDeviceToEquipment(Device device, Long equipment_id) {
        Device deviceFromDb = deviceRepository.findByPlace(device.getPlace());
        Equipment equipment = equipmentRepository.findById(equipment_id).get();
        if (deviceFromDb != null) {
            return deviceFromDb;
        }
        if (equipment == null) {
            throw new EquipmentNotFoundException();
        }
        else{
                Device newDevice = new Device();
                newDevice.setPlace(device.getPlace());
                newDevice.setDeviceType(device.getDeviceType());
                newDevice.setEquipment(equipment);
                //equipment.addDevice(newDevice);
                 deviceRepository.save(newDevice);

                equipmentRepository.save(equipment);
                return newDevice;
            }
        }
   public Device loadDeviceByPlace(String place) throws DeviceNotFoundException {
        return deviceRepository.findByPlace(place);
   }

   public List<Device> findByEquipment(Equipment equipment){
        return deviceRepository.findByEquipment(equipment);
   }
}
