package com.example.spring_booking_bot.commands;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.DoctorHelper;
import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.models.UserModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ChooseReview implements WorkerCommand{
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Оставить отзыв на врача")){
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("На кого желаете оставить отзыв?");
        boolean isAllergolog = false;
        boolean isGinekolog = false;
        boolean isHirurg = false;
        boolean isLor = false;
        boolean isOkulist = false;
        boolean isTerapevt = false;
        List<BookModel> bookList = DoctorHelper.getBooks();
        for (BookModel b : bookList) {
         if (b.getDoctorEnum().equals(DoctorEnum.TERAPEVT)){
             isTerapevt = true;
         }
            if (b.getDoctorEnum().equals(DoctorEnum.OKULIST)){
                isOkulist = true;
            }
            if (b.getDoctorEnum().equals(DoctorEnum.HIRURG)){
                isHirurg = true;
            }
            if (b.getDoctorEnum().equals(DoctorEnum.LOR)){
                isLor = true;
            }
            if (b.getDoctorEnum().equals(DoctorEnum.GINEKOLOG)){
                isGinekolog = true;
            }
            if (b.getDoctorEnum().equals(DoctorEnum.ALLERGOLOG)){
                isAllergolog = true;
            }
        }
        KeyboardRow k1 = new KeyboardRow();
        KeyboardRow k2 = new KeyboardRow();
        KeyboardRow k3 = new KeyboardRow();
        if (isTerapevt){
            k1.add(new KeyboardButton("На терапевта"));
        }
        if (isOkulist){
            k1.add(new KeyboardButton("На окулиста"));
        }
        if (isHirurg){
            k2.add(new KeyboardButton("На хирурга"));
        }
        if (isLor){
            k2.add(new KeyboardButton("На лора"));
        }
        if (isGinekolog){
            k3.add(new KeyboardButton("На гинеколога"));
        }
        if (isAllergolog){
            k3.add(new KeyboardButton("На аллерголога"));
        }
        List<KeyboardRow> list = new ArrayList<>();
        list.add(k1);
        list.add(k2);
        list.add(k3);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
