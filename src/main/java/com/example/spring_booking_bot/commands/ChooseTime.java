package com.example.spring_booking_bot.commands;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.DoctorHelper;
import com.example.spring_booking_bot.helpers.TimeControl;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

@Component
public class ChooseTime implements WorkerCommand {
    @Override
    //////////////////////////// bugged sector
    public SendMessage start(Update update) {
        TimeControl timeControl = new TimeControl();
        List<String> list = timeControl.getTimes();
        boolean ifThisCommand=false;
        for (String str: list){
            if (update.getMessage().getText().equals(str)){
                ifThisCommand = true;
                break;
            }
        }
        /////////////////////////////////////////////////////////
        if(!ifThisCommand){
            return null;
        }
        BookModel bookModel = new BookModel();
        UserModel userModel;
        bookModel.setTime(String.valueOf(update.getMessage().getText()));
        userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        bookModel.setTgId(String.valueOf(update.getMessage().getFrom().getId().toString()));
        bookModel.setDoctorEnum(userModel.getDoctorEnum());
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardRemove rmkb = new ReplyKeyboardRemove(true);
        rmkb.setSelective(true);
        sendMessage.setReplyMarkup(rmkb);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Вы успешно записались к врачу");
        DoctorHelper.save(bookModel);
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
