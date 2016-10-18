package com.boomerang.workflowconnector.internal.model;

import com.boomerang.workflowconnector.internal.lib.Auditable;
import com.wordnik.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kanhaiya on 21/09/16.
 */
@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = Auditable.class , isAudited = true)
public class Project extends Auditable {

    @Id
    @GeneratedValue
    Long id ;

    String name;

    Boolean active;

    String description;

    Integer version;

    @OneToMany
    @JoinColumn(name = "project_id")
    @NotAudited
    private List<ProjectFlowNode> projectFlowNodes;
}
