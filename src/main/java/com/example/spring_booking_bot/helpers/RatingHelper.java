package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.ReviewModel;
import com.example.spring_booking_bot.models.VisitModel;
import com.example.spring_booking_bot.repos.ReviewRepo;
import com.example.spring_booking_bot.repos.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingHelper {
    @Autowired
    VisitRepo visitRepo;
    @Autowired
    ReviewRepo reviewRepo;
    public static RatingHelper ratingHelper = null;
    public RatingHelper(){ratingHelper = this;}
    public static void saveDoctorRating(VisitModel v){
        ratingHelper.visitRepo.save(v);
    }

    public static List<ReviewModel> getReviews(){
        List<ReviewModel> list = ratingHelper.reviewRepo.findAll();
        return list;
    }
    public static ReviewModel getReview(DoctorEnum e){
        ReviewModel reviewModel = ratingHelper.reviewRepo.findReviewModelByDoctorEnum(e);
        return reviewModel;
    }
    public static void saveReview(ReviewModel r){
        ratingHelper.reviewRepo.save(r);
    }
    public static void updateRatingTable(ReviewModel r){
    ReviewModel reviewModel = r;
    Double averageMark;
    Integer j = 0;
    Integer sum=0;
        List<VisitModel> list = ratingHelper.visitRepo.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDoctorEnum().equals(r.getDoctorEnum())){
               sum = sum + Integer.parseInt(String.valueOf(list.get(i).getMark()));
                j++;
            }
        }
        averageMark = (double) (sum/j);
        r.setRating(averageMark);
   ratingHelper.reviewRepo.save(r);
    }
    public static void setDefaults(ReviewModel r){
        ReviewModel defaultModel1 = r;
        ratingHelper.reviewRepo.save(defaultModel1);
    }
}
