package com.GuideIn.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Learner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public  long id;
    public  String fullName;
    public String mobile;
    public  String email;
    public  String city;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
