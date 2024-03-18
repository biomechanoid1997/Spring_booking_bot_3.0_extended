package com.example.spring_booking_bot.models;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Doctor_review")
@Data
public class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "Doctor")
    @Enumerated
    DoctorEnum doctorEnum;

    @Column(name = "doctor name")
    String doctorName;

    @Column (name = "number of visits")
    Integer visitCount;

    @Column(name = "rating")
    Double rating;


}
