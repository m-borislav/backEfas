package com.backend.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "модель сенсора")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(value = "место где расположен сенсор", example = "чип 1")
    @Column(name = "place", nullable = false, unique = true, length = 50)
    private String place;
    @ApiModelProperty(value = "тип сенсора", example = "амперметр")
    @Column(name = "deviceType", nullable = false, length = 50)
    private String deviceType;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="deviceType_id", unique = true, nullable = false, updatable = false)
    private DeviceType deviceType;*/

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @JsonManagedReference
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Measuring> measuringSet;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id.equals(device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +  '}';
    }
}
