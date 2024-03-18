package com.example.spring_booking_bot.commands;

import com.example.spring_booking_bot.helpers.RatingHelper;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.ReviewModel;
import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.models.VisitModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ChooseRating implements WorkerCommand{
    @Override
    public SendMessage start(Update update) {
        boolean ifThisCommand = false;
        String[]ratingArray = {"1","2","3","4","5"};
        String messageInput = update.getMessage().getText().toString();
        for (int i = 0; i < ratingArray.length; i++) {
            if (messageInput.equals(ratingArray[i])){
                ifThisCommand = true;
            }
        }
        if (!ifThisCommand){
            return null;
        }

        VisitModel visitModel = new VisitModel();
        UserModel userModel;
        userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        visitModel.setDoctorEnum(userModel.getDoctorEnum());
        visitModel.setTgId(userModel.getTgId());
        visitModel.setMark(Integer.parseInt(update.getMessage().getText().toString()));
        ReviewModel reviewModel = RatingHelper.getReview(userModel.getDoctorEnum());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        sendMessage.setText("Спасибо за вашу оценку :)");
        RatingHelper.saveDoctorRating(visitModel);
        RatingHelper.updateRatingTable(reviewModel);
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
