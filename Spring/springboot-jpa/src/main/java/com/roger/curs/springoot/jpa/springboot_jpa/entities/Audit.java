package com.roger.curs.springoot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
public class Audit {

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_ap")
    private LocalDateTime updateAt;

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void prePersist() {
        System.out.println("Evento del ciclo de vida del entity prepersist");
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("Evento del ciclo de vida del entity preUpdate");
        this.updateAt = LocalDateTime.now();
    }
}
