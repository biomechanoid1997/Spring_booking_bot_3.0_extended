package com.example.spring_booking_bot;

import com.example.spring_booking_bot.commands.*;
import com.example.spring_booking_bot.commands.bookcommand.*;
import com.example.spring_booking_bot.commands.loginCommand.LeaveYourNameCommand;
import com.example.spring_booking_bot.commands.loginCommand.RemainAnonymousCommand;
import com.example.spring_booking_bot.commands.reviewcommand.*;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.RatingHelper;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.ReviewModel;
import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.repos.UserRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Bio_DocBooking_bot";
    }
    @Override
    public String getBotToken(){
        return "6352247546:AAFWyC_txjP4__i3qHJ5pqd0Bfiv9xb4ICc";
    }


    @Override
    public void onUpdateReceived(Update update) {
        //////////////////////////////////////////////////////////////////////////////
       List<ReviewModel> reviewModelList = RatingHelper.getReviews();
        ReviewModel defaultModel1 = new ReviewModel();
        ReviewModel defaultModel2 = new ReviewModel();
        ReviewModel defaultModel3 = new ReviewModel();
        ReviewModel defaultModel4 = new ReviewModel();
        ReviewModel defaultModel5 = new ReviewModel();
        ReviewModel defaultModel6 = new ReviewModel();
        defaultModel1.setRating(0.0);
        defaultModel1.setDoctorEnum(DoctorEnum.ALLERGOLOG);
        defaultModel1.setDoctorName("Аллерголог");
        defaultModel1.setVisitCount(0);
        defaultModel2.setRating(0.0);
        defaultModel2.setDoctorEnum(DoctorEnum.GINEKOLOG);
        defaultModel2.setDoctorName("Гинеколог");
        defaultModel2.setVisitCount(0);
        defaultModel3.setRating(0.0);
        defaultModel3.setDoctorEnum(DoctorEnum.LOR);
        defaultModel3.setDoctorName("Лор");
        defaultModel3.setVisitCount(0);
        defaultModel4.setRating(0.0);
        defaultModel4.setDoctorEnum(DoctorEnum.HIRURG);
        defaultModel4.setDoctorName("Хирург");
        defaultModel4.setVisitCount(0);
        defaultModel5.setRating(0.0);
        defaultModel5.setDoctorEnum(DoctorEnum.OKULIST);
        defaultModel5.setDoctorName("Окулист");
        defaultModel5.setVisitCount(0);
        defaultModel6.setRating(0.0);
        defaultModel6.setDoctorEnum(DoctorEnum.TERAPEVT);
        defaultModel6.setDoctorName("Терапевт");
        defaultModel6.setVisitCount(0);
       if (reviewModelList.isEmpty()){
           RatingHelper.setDefaults(defaultModel1);
           RatingHelper.setDefaults(defaultModel2);
           RatingHelper.setDefaults(defaultModel3);
           RatingHelper.setDefaults(defaultModel4);
           RatingHelper.setDefaults(defaultModel5);
           RatingHelper.setDefaults(defaultModel6);
       }
        //////////////////////////////////////////////////////////////////////////////
        Boolean IsUser = false;
        String userId = update.getMessage().getFrom().getId().toString();
        UserModel userModel = UserHelper.findUser(userId);
        if (userModel.getTgId() != null){
            IsUser = true;
        }
        ///////////////////////////////////////////////////////////////////////////////
        KeyboardRow k = new KeyboardRow();
        k.add(new KeyboardButton("Log In"));
        if (IsUser == true){
            k.add(new KeyboardButton("Оставить отзыв на врача"));
        }
        k.add(new KeyboardButton("Записаться к врачу"));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите действие");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<WorkerCommand> list = new ArrayList<>();
        list.add(new LoginCommand());
        list.add(new BookCommand());
        list.add(new TerapevtBookCommand());
        list.add(new AllergologBookCommand());
        list.add(new GinekologBookCommand());
        list.add(new HirurgBookCommand());
        list.add(new LorBookCommand());
        list.add(new OkulistBookCommand());
        list.add(new ChooseTime());
        list.add(new RemainAnonymousCommand());
        list.add(new LeaveYourNameCommand());
        list.add(new ChooseReview());
        list.add(new AllergologReviewCommand());
        list.add(new GinekologReviewCommand());
        list.add(new HirurgReviewCommand());
        list.add(new LorReviewCommand());
        list.add(new OkulistReviewCommand());
        list.add(new TerapevtReviewCommand());
        list.add(new ChooseRating());
        //////////////////////////////
            for (WorkerCommand w: list){
                SendMessage res = w.start(update);
            if (res!= null){
           sendMessage = res;
                break;
            }
            ///////////////////////////
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
