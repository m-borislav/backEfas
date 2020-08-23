package com.backend.demo.controllers;

import com.backend.demo.models.Device;
import com.backend.demo.models.Measuring;
import com.backend.demo.repos.DeviceRepository;
import com.backend.demo.repos.MeasuringRepository;
import com.backend.demo.services.MeasuringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value = "measuring controller", description = "crud со всеми измерениями")
@RestController
public class MeasuringController {

    private MeasuringRepository measuringRepository;
    private MeasuringService measuringService;
    private DeviceRepository deviceRepository;


    @Autowired
    public MeasuringController(MeasuringRepository measuringRepository, DeviceRepository deviceRepository, MeasuringService measuringService) {
        this.measuringRepository = measuringRepository;
        this.deviceRepository = deviceRepository;
        this.measuringService = measuringService;

    }

    @GetMapping(value = "/measuring/{dateOfMeasuring}")
    public Measuring findMeasuringByDate (@PathVariable LocalDateTime dateOfMeasuring){
        return measuringRepository.findByDateOfMeasuring(dateOfMeasuring);
    }

    @ApiOperation(value = "возвращает все измерения конкретного сенсора")
    @GetMapping(value = "/device/{device_id}/measuring")
    public ResponseEntity<List<Measuring>> findMeasuringOnDevice(@PathVariable Long device_id){
        Device device = deviceRepository.findById(device_id).get();
        if (device == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity(measuringService.findByDevice(device), HttpStatus.OK);
    }
}
