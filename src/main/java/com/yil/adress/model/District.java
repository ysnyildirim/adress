package com.yil.adress.model;

import com.yil.adress.base.IEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "ADR",
        name = "DISTRICT")
public class District implements IEntity {
    @Column(name = "NAME", nullable = false, length = 100)
    public String name;
    @Id
    @SequenceGenerator(name = "DISTRICT_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_DISTRICT_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "DISTRICT_SEQUENCE_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "CITY_ID", nullable = false)
    private Long cityId;
}
