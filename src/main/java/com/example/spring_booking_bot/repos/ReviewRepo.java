package com.example.spring_booking_bot.repos;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewModel, String> {
    ReviewModel findReviewModelByDoctorEnum(DoctorEnum d);
}
