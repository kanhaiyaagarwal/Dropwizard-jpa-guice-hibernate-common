package com.boomerang.workflowconnector.internal.model;

import com.boomerang.workflowconnector.internal.lib.Auditable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by kanhaiya on 22/09/16.
 */
@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class EdgeMapping extends Auditable{
    @Id
    @GeneratedValue
    Long id;
    Long parentId;
    Long childId;
    Long projectId;
}
