package com.akjostudios.acsp.bot.discord.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.update.*;
import net.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import net.dv8tion.jda.api.events.emoji.EmojiRemovedEvent;
import net.dv8tion.jda.api.events.emoji.update.EmojiUpdateNameEvent;
import net.dv8tion.jda.api.events.emoji.update.EmojiUpdateRolesEvent;
import net.dv8tion.jda.api.events.guild.GuildAvailableEvent;
import net.dv8tion.jda.api.events.guild.GuildUnavailableEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.*;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEmojiEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.events.stage.StageInstanceCreateEvent;
import net.dv8tion.jda.api.events.stage.StageInstanceDeleteEvent;
import net.dv8tion.jda.api.events.sticker.GuildStickerAddedEvent;
import net.dv8tion.jda.api.events.sticker.GuildStickerRemovedEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateAvailableEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateNameEvent;
import net.dv8tion.jda.api.events.sticker.update.GuildStickerUpdateTagsEvent;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.events.user.update.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum BotEventType {
    OTHER(-10, GenericEvent.class),
    GATEWAY_PING(-2, GatewayPingEvent.class),
    EXCEPTION(-1, ExceptionEvent.class),
    STATUS_CHANGE(0, StatusChangeEvent.class),
    USER_NAME_CHANGE(100, UserUpdateNameEvent.class),
    USER_AVATAR_CHANGE(101, UserUpdateAvatarEvent.class),
    USER_FLAGS_CHANGE(102, UserUpdateFlagsEvent.class),
    USER_STATUS_CHANGE(103, UserUpdateOnlineStatusEvent.class),
    USER_TYPING_STATUS_CHANGE(104, UserTypingEvent.class),
    USER_ACTIVITIES_CHANGE(110, UserUpdateActivitiesEvent.class),
    USER_ACTIVITY_ORDER_CHANGE(111, UserUpdateActivityOrderEvent.class),
    USER_ACTIVITY_START(112, UserActivityStartEvent.class),
    USER_ACTIVITY_END(113, UserActivityEndEvent.class),
    MESSAGE_RECEIVE(200, MessageReceivedEvent.class),
    MESSAGE_UPDATE(201, MessageUpdateEvent.class),
    MESSAGE_DELETE(202, MessageDeleteEvent.class),
    MESSAGE_REACTION_ADD(210, MessageReactionAddEvent.class),
    MESSAGE_REACTION_REMOVE(211, MessageReactionRemoveEvent.class),
    MESSAGE_REACTION_REMOVE_ALL(212, MessageReactionRemoveAllEvent.class),
    MESSAGE_REACTION_REMOVE_EMOJI(213, MessageReactionRemoveEmojiEvent.class),
    GUILD_AVAILABLE(300, GuildAvailableEvent.class),
    GUILD_UNAVAILABLE(301, GuildUnavailableEvent.class),
    GUILD_MEMBER_JOIN(310, GuildMemberJoinEvent.class),
    GUILD_MEMBER_UPDATE(311, GuildMemberUpdateEvent.class),
    GUILD_MEMBER_LEAVE(312, GuildMemberRemoveEvent.class),
    GUILD_MEMBER_ROLE_ADD(313, GuildMemberRoleAddEvent.class),
    GUILD_MEMBER_ROLE_REMOVE(314, GuildMemberRoleRemoveEvent.class),
    GUILD_MEMBER_PENDING_CHANGE(320, GuildMemberUpdatePendingEvent.class),
    GUILD_MEMBER_NICKNAME_CHANGE(321, GuildMemberUpdateNicknameEvent.class),
    GUILD_MEMBER_AVATAR_CHANGE(322, GuildMemberUpdateAvatarEvent.class),
    GUILD_MEMBER_TIMEOUT_CHANGE(323, GuildMemberUpdateTimeOutEvent.class),
    GUILD_MEMBER_BOOST_CHANGE(324, GuildMemberUpdateBoostTimeEvent.class),
    GUILD_INVITE_CREATE(330, GuildInviteCreateEvent.class),
    GUILD_INVITE_DELETE(331, GuildInviteDeleteEvent.class),
    GUILD_VOICE_UPDATE(340, GuildVoiceUpdateEvent.class),
    GUILD_VOICE_MUTE(350, GuildVoiceMuteEvent.class),
    GUILD_VOICE_DEAF(351, GuildVoiceDeafenEvent.class),
    GUILD_VOICE_GUILD_MUTE(352, GuildVoiceGuildMuteEvent.class),
    GUILD_VOICE_GUILD_DEAF(353, GuildVoiceGuildDeafenEvent.class),
    GUILD_VOICE_SELF_MUTE(354, GuildVoiceSelfMuteEvent.class),
    GUILD_VOICE_SELF_DEAF(355, GuildVoiceSelfDeafenEvent.class),
    GUILD_VOICE_STREAM(356, GuildVoiceStreamEvent.class),
    GUILD_VOICE_VIDEO(357, GuildVoiceVideoEvent.class),
    GUILD_STAGE_INSTANCE_CREATE(360, StageInstanceCreateEvent.class),
    GUILD_STAGE_INSTANCE_DELETE(361, StageInstanceDeleteEvent.class),
    CHANNEL_CREATE(400, ChannelCreateEvent.class),
    CHANNEL_DELETE(401, ChannelDeleteEvent.class),
    CHANNEL_NAME_CHANGE(410, ChannelUpdateNameEvent.class),
    CHANNEL_TYPE_CHANGE(411, ChannelUpdateTypeEvent.class),
    CHANNEL_USER_LIMIT_CHANGE(412, ChannelUpdateUserLimitEvent.class),
    CHANNEL_NSFW_CHANGE(413, ChannelUpdateNSFWEvent.class),
    CHANNEL_ARCHIVE_STATE_CHANGE(414, ChannelUpdateArchivedEvent.class),
    ROLE_CREATE(500, RoleCreateEvent.class),
    ROLE_DELETE(501, RoleDeleteEvent.class),
    ROLE_NAME_CHANGE(510, RoleUpdateNameEvent.class),
    ROLE_PERMISSION_CHANGE(511, RoleUpdatePermissionsEvent.class),
    ROLE_POSITION_CHANGE(512, RoleUpdatePositionEvent.class),
    ROLE_MENTIONABLE_CHANGE(513, RoleUpdateMentionableEvent.class),
    EMOJI_ADD(600, EmojiAddedEvent.class),
    EMOJI_REMOVE(601, EmojiRemovedEvent.class),
    EMOJI_NAME_CHANGE(610, EmojiUpdateNameEvent.class),
    EMOJI_ROLES_CHANGE(611, EmojiUpdateRolesEvent.class),
    STICKER_ADD(700, GuildStickerAddedEvent.class),
    STICKER_REMOVE(701, GuildStickerRemovedEvent.class),
    STICKER_AVAILABILITY_CHANGE(710, GuildStickerUpdateAvailableEvent.class),
    STICKER_NAME_CHANGE(711, GuildStickerUpdateNameEvent.class),
    STICKER_TAGS_CHANGE(712, GuildStickerUpdateTagsEvent.class);

    private final int id;
    private final Class<? extends GenericEvent> eventClass;

    private static final Map<Integer, BotEventType> ID_MAP = Arrays.stream(BotEventType.values())
            .collect(Collectors.toUnmodifiableMap(BotEventType::getId, type -> type));

    private static final Map<Class<? extends GenericEvent>, BotEventType> CLASS_MAP = Arrays.stream(BotEventType.values())
            .filter(type -> type != OTHER)
            .collect(Collectors.toUnmodifiableMap(BotEventType::getEventClass, type -> type));

    public static Optional<BotEventType> getById(int id) {
        return Optional.ofNullable(ID_MAP.get(id));
    }

    public static Optional<BotEventType> getByClass(Class<? extends GenericEvent> eventClass) {
        return Optional.ofNullable(CLASS_MAP.get(eventClass));
    }
}