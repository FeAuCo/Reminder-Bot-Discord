package me.feauco.commands;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends ListenerAdapter {
    private static List<String> tasks = new ArrayList<>();

    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commands = new ArrayList<>();
        List<OptionData> add_options = new ArrayList<>();

        OptionData add_option_name = new OptionData(OptionType.STRING, "name", "Name of task to add.", true);
        add_options.add(add_option_name);

        OptionData remove_option_name = new OptionData(OptionType.STRING, "name", "Name of task to remove.", true);

        commands.add(Commands.slash("show_tasks", "Shows all tasks you have.").addOptions(add_options));
        commands.add(Commands.slash("add", "Adds a task to remind of.").addOptions(add_options));
        commands.add(Commands.slash("remove", "Removes a task to remind of.").addOptions(remove_option_name));

        event.getGuild().updateCommands().addCommands(commands).queue();
    }

    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("add")) {
            event.deferReply().setEphemeral(true).queue();

            if (tasks.contains(event.getOption("name").getAsString())) {
                event.getHook().sendMessage("Task with name '" + event.getOption("name").getAsString() + "' already exist.").queue();
            } else {
                tasks.add(event.getOption("name").getAsString());
                event.getHook().sendMessage("Task with name '" + event.getOption("name").getAsString() + "' was added.").queue();
            }

        } else if (event.getName().equals("remove")) {
            event.deferReply().setEphemeral(true).queue();

            if (tasks.contains(event.getOption("name").getAsString())) {
                tasks.remove(event.getOption("name").getAsString());
                event.getHook().sendMessage("Task with name '" + event.getOption("name").getAsString() + "' was removed.").queue();
            } else {
                event.getHook().sendMessage("No task with name '" + event.getOption("name").getAsString() + "' was not found.").queue();
            }
        }

    }
}
