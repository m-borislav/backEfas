package com.backend.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "модель измерений")
public class Measuring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(value = "дата измерения", example = "2020-12-31")
    @Column(name = "dateOfMeasuring", nullable = false)
    private LocalDateTime dateOfMeasuring;

    @ApiModelProperty(value = "сила тока", example = "30 А")
    private double amperage;
    @ApiModelProperty(value = "давление", example = "5.5 бар")
    private double pressure;
    @ApiModelProperty(value = "влажность воздуха", example = "75%")
    private double wetness;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measuring measuring = (Measuring) o;
        return id.equals(measuring.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Measuring{" +
                "id=" + id +  '}';
    }

}