package com.akjostudios.acsp.bot.discord.common.primitive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum AcspDiscordChannelCategories implements DiscordPrimitive {
    WELCOME_CATEGORY(1170398577722404915L,1073274747468644373L),
    BEGIN_CATEGORY(1170398577722404918L,1073346445958201375L),
    TEAM_CATEGORY(1170398577722404920L,1170386982011142184L),
    ADMIN_CATEGORY(1170398577722404923L,1073273754110668931L);

    private final long localId;
    private final long prodId;
}