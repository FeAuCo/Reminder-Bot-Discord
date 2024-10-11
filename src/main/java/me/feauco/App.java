package me.feauco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import me.feauco.commands.CommandManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App {
    public App() {
    }

    public static void main(String[] args) throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader("C:\\Users\\Acer\\java_directories\\Reminder-Bot-Discord\\useful.txt"));
        String token = brTest.readLine();
        JDABuilder
                .createLight(token)
                .enableIntents(List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new CommandManager())
                .build();
    }
}
