package com.borabogdan.javaapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

    @NotNull
    protected int microcontrollerID;


}
