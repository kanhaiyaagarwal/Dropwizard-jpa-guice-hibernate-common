package com.boomerang.workflowconnector.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;

/**
 * Created by kanhaiya on 21/09/16.
 */
@Entity
@Data
public class ProjectFlowNode {

    @Id
    @GeneratedValue
    Long id;

    String name;

    URL url;
}
