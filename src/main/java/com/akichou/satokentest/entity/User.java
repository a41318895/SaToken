package com.akichou.satokentest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true, nullable = false)
    private String username ;

    @Column(unique = true, nullable = false)
    private String password ;

    private Integer age ;

    private String note ;

    private Long createdBy ;

    private LocalDateTime createdTime;

    private Long updatedBy ;

    private LocalDateTime updatedTime ;

    protected User() {}

    private User(Long id, String username, String password, Integer age,
                 String note, Long createdBy, LocalDateTime createdTime, Long updatedBy, LocalDateTime updatedTime) {

        this.id = id ;
        this.username = username ;
        this.password = password ;
        this.age = age ;
        this.note = note ;
        this.createdBy = createdBy ;
        this.createdTime = createdTime ;
        this.updatedBy = updatedBy ;
        this.updatedTime = updatedTime ;
    }

    public static class Builder {

        private Long id ;
        private String username ;
        private String password ;
        private Integer age ;
        private String note ;
        private Long createdBy ;
        private LocalDateTime createdTime ;
        private Long updatedBy ;
        private LocalDateTime updatedTime ;

        public Builder id(Long id) {

            this.id = id ;
            return this ;
        }

        public Builder username(String username) {

            this.username = username ;
            return this ;
        }

        public Builder password(String password) {

            this.password = password ;
            return this ;
        }

        public Builder age(Integer age) {

            this.age = age ;
            return this ;
        }

        public Builder note(String note) {

            this.note = note ;
            return this ;
        }

        public Builder createdBy(Long createdBy) {

            this.createdBy = createdBy ;
            return this ;
        }

        public Builder createdTime(LocalDateTime createdTime) {

            this.createdTime = createdTime ;
            return this ;
        }

        public Builder updatedBy(Long updatedBy) {

            this.updatedBy = updatedBy ;
            return this ;
        }

        public Builder updatedTime(LocalDateTime updatedTime) {

            this.updatedTime = updatedTime ;
            return this ;
        }

        public User build() {

            return new User(id, username, password, age, note, createdBy, createdTime, updatedBy, updatedTime) ;
        }
    }

    public static Builder builder() {

        return new Builder() ;
    }
}
