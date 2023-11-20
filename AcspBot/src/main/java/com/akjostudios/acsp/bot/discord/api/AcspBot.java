package com.akjostudios.acsp.bot.discord.api;

import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordChannelCategories;
import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordChannels;
import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordRoles;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@SuppressWarnings("unused")
public interface AcspBot {
    void useChannel(@NotNull AcspDiscordChannels channel, @NotNull Consumer<? super Channel> consumer);
    void useRole(@NotNull AcspDiscordRoles role, @NotNull Consumer<Role> consumer);
    void useCategory(@NotNull AcspDiscordChannelCategories category, @NotNull Consumer<Channel> consumer);
    void useChannelsInCategory(@NotNull AcspDiscordChannelCategories category, @NotNull Consumer<List<GuildChannel>> consumer);
    @NotNull JDA.Status getStatus();
    void shutdown(@NotNull ConfigurableApplicationContext context, int delay);
    void restart(@NotNull ConfigurableApplicationContext context, int delay);
}