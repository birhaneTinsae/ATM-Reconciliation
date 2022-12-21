package com.enatbanksc.ATMReconciliation.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable {

    @Schema(hidden = true)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Schema(hidden = true)
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Schema(hidden = true)
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;
    @Schema(hidden = true)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Schema(hidden = true)
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
