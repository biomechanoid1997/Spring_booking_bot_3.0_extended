package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorHelper_backup {
    @Autowired
    BookRepo bookRepo;

    private static DoctorHelper_backup doctorHelper = null;

    public DoctorHelper_backup() {
        doctorHelper = this;
    }
    public static void save(BookModel b){
        doctorHelper.bookRepo.save(b);
    }

    public static List<String> getFreeTimes(DoctorEnum d){
        TimeControl timeControl = new TimeControl();
        List<BookModel> bookModelList = doctorHelper.bookRepo.findBookModelByDoctorEnum(d);
        List<String> freeTimes = new ArrayList<>();
        freeTimes = timeControl.getTimes();

        List<String> list = new ArrayList<>();
        for (BookModel b : bookModelList){
            for (String str:freeTimes){
                if (b.getTime().equals(str)){
                    list.add(b.getTime());
                }
            }
        }
        freeTimes.remove(list);
        return freeTimes;
    }
    public static List<BookModel> getBooks(){
        List<BookModel> list = doctorHelper.bookRepo.findAll();
        return list;
    }
}
