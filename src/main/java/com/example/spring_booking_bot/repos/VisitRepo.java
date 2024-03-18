package com.example.spring_booking_bot.repos;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.models.ReviewModel;
import com.example.spring_booking_bot.models.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepo extends JpaRepository<VisitModel,Long> {
    VisitModel findVisitModelByDoctorEnum(DoctorEnum d);


}
