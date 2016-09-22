package com.boomerang.workflowconnector.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by kanhaiya on 21/09/16.
 */
@Entity
@Data
public class Project {

    @Id
    @GeneratedValue
    private Long id ;

    private String name;

    private Boolean active;

    private String description;

    private Integer version;
}
