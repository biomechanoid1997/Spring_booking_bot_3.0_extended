package com.example.spring_booking_bot.commands.loginCommand;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.UserModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LeaveYourNameCommand implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        if (update.getMessage().getText().equals("Оставить своё имя")) {
            userModel.setUsername(String.valueOf(update.getMessage().getFrom().getUserName().toString()));
            userModel.setTgId(String.valueOf(update.getMessage().getFrom().getId().toString()));
            userModel.setPerson_name(String.valueOf(update.getMessage().getFrom().getFirstName().toString()));
            sendMessage.setText("Пользователь сохранён");
            UserHelper.saveUser(userModel);
            return sendMessage;
        }
        return null;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}

