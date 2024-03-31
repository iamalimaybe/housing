package com.legit.housing.entity.main;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntityBase {
    @CreationTimestamp
    @Column(name = "created_at")
    protected Instant createdAt;
    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Instant updatedAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    protected String updatedBy;
}
