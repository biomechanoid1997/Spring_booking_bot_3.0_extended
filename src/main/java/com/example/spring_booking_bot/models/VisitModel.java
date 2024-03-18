package com.example.spring_booking_bot.models;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "Doctor_visit")
@Entity
@Data
public class VisitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "Doctor")
    @Enumerated
    DoctorEnum doctorEnum;

    @Column(name = "Mark")
    Integer Mark;

    @Column(name = "tg_id")
    String tgId;
}
