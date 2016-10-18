package com.boomerang.workflowconnector.internal.model;

import com.boomerang.workflowconnector.internal.lib.Auditable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Created by kanhaiya on 21/09/16.
 */
@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = Auditable.class , isAudited = true)
public class ProjectFlowNode extends Auditable {

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @Column(name = "project_id")
    Long projectId;
//ProjectId and Name are idempotent fields
    String name;

    String url;

    String attributes;

    Boolean isRoot;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalTime")
    LocalTime startBy;
}
