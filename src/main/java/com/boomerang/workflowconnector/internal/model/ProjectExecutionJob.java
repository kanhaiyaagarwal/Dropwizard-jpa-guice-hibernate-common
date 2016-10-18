package com.boomerang.workflowconnector.internal.model;

import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.lib.Auditable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by kanhaiya on 22/09/16.
 */
@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = Auditable.class , isAudited = true)
public class ProjectExecutionJob extends Auditable {
    @Id
    @GeneratedValue
    Long id;
    Long projectId;
    String name;
    String userName;
    String attributes;
    Status status;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    LocalDateTime startTime;
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    LocalDateTime endTime;

}
