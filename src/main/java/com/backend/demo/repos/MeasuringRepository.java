package com.backend.demo.repos;

import com.backend.demo.models.Device;
import com.backend.demo.models.Measuring;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeasuringRepository extends CrudRepository<Measuring, Long> {

    Measuring findByDateOfMeasuring(LocalDateTime dateOfMeasuring);
    List<Measuring> findByDevice(Device device);
}