package com.akjostudios.acsp.bot.discord.common.primitive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum AcspDiscordRoles implements DiscordPrimitive {
    EVERYONE_ROLE(1170398575302295663L,1073266273619816549L),
    BOTS_ROLE(1170398575302295664L,1073678363237027915L),
    MEMBER_ROLE(1170398575302295665L,1170390546188083200L),
    TEAM_ROLE(1170398575302295666L,1170381913635168507L),
    ADMIN_ROLE(1170398575302295667L,1073273895395791009L),
    STAR_ROLE(1170398575302295668L,1170396120275501246L);

    private final long localId;
    private final long prodId;
}