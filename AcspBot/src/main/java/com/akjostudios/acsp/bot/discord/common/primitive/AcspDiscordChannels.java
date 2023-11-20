package com.akjostudios.acsp.bot.discord.common.primitive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum AcspDiscordChannels implements DiscordPrimitive {
    WELCOME_CHANNEL(1170398577722404916L,1073275189338591233L,AcspDiscordChannelCategories.WELCOME_CATEGORY),
    RULES_CHANNEL(1170398577722404917L,1073273398693724332L,AcspDiscordChannelCategories.WELCOME_CATEGORY),
    BEGIN_CHANNEL(1170398577722404919L,1073346501314629732L,AcspDiscordChannelCategories.BEGIN_CATEGORY),
    TEAM_CHANNEL(1170398577722404921L,1170387449885765682L,AcspDiscordChannelCategories.TEAM_CATEGORY),
    AUDIT_CHANNEL(1170398577722404922L,1170387921463955546L,AcspDiscordChannelCategories.TEAM_CATEGORY),
    TEAM_VOICE_CHANNEL(1170771960616194048L,1170770681508671518L,AcspDiscordChannelCategories.TEAM_CATEGORY),
    ADMIN_CHANNEL(1170398577722404924L,1073273398693724333L,AcspDiscordChannelCategories.ADMIN_CATEGORY),
    ADMIN_VOICE_CHANNEL(1170398578007621663L,1075490158729760829L,AcspDiscordChannelCategories.ADMIN_CATEGORY);

    private final long localId;
    private final long prodId;
    private final AcspDiscordChannelCategories category;
}