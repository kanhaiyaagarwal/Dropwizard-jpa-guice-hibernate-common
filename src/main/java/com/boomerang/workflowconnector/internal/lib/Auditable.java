package com.boomerang.workflowconnector.internal.lib;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by kanhaiya on 28/09/16.
 */
@Data
@MappedSuperclass
@Getter
@ToString
public class Auditable {

    @Column(name = "createdAt")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        onUpdate();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
}
