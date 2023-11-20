package com.akjostudios.acsp.bot.discord.service;

import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordChannelCategories;
import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordChannels;
import com.akjostudios.acsp.bot.discord.common.primitive.AcspDiscordRoles;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscordPrimitiveService {
    @SuppressWarnings("unchecked")
    public <T extends Channel> @NotNull Optional<T> getChannel(@NotNull JDA instance, @NotNull AcspDiscordChannels channel) {
        return Optional.ofNullable((T) instance.getChannelById(channel.getType(), channel.getId()));
    }

    public @NotNull Optional<Role> getRole(@NotNull JDA instance, @NotNull AcspDiscordRoles role) {
        return Optional.ofNullable(instance.getRoleById(role.getId()));
    }

    public @NotNull Optional<Category> getCategory(@NotNull JDA instance, @NotNull AcspDiscordChannelCategories category) {
        return Optional.ofNullable(instance.getCategoryById(category.getId()));
    }

    public @NotNull Optional<List<GuildChannel>> getChannelsInCategory(@NotNull JDA instance, @NotNull AcspDiscordChannelCategories category) {
        return Optional.ofNullable(instance.getCategoryById(category.getId())).map(Category::getChannels);
    }
}