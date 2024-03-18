package com.example.spring_booking_bot.commands.reviewcommand;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.models.VisitModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class GinekologReviewCommand implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("На гинеколога")){
            return null;
        }
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        userModel.setTgId(String.valueOf(update.getMessage().getFrom().getId()));
        userModel.setPerson_name(String.valueOf(update.getMessage().getFrom().getFirstName()));
        userModel.setUsername(String.valueOf(update.getMessage().getFrom().getUserName()));
        userModel.setDoctorEnum(DoctorEnum.GINEKOLOG);
        UserHelper.saveUser(userModel);
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        VisitModel visitModel = new VisitModel();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Поставьте оценку врачу.");
        List<KeyboardRow> list1 = new ArrayList<>();
        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton("1"));
        k1.add(new KeyboardButton("2"));
        k1.add(new KeyboardButton("3"));
        k1.add(new KeyboardButton("4"));
        k1.add(new KeyboardButton("5"));
        list1.add(k1);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(list1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
