package com.akjostudios.acsp.bot.util.command;

import com.akjostudios.acsp.bot.config.bot.command.BotConfigCommand;
import com.akjostudios.acsp.bot.config.bot.message.BotConfigMessage;
import com.akjostudios.acsp.bot.constants.AcspDiscordChannels;
import com.akjostudios.acsp.bot.services.DiscordMessageService;
import com.akjostudios.acsp.bot.services.ErrorMessageService;
import com.akjostudios.acsp.bot.services.bot.BotConfigService;
import com.akjostudios.acsp.bot.services.bot.BotStringsService;
import com.akjostudios.acsp.bot.services.command.BotCommandArgumentParserService;
import com.akjostudios.acsp.bot.services.command.BotCommandArgumentParsingReportService;
import com.akjostudios.acsp.bot.services.command.CommandHelperService;
import com.akjostudios.acsp.bot.util.command.argument.BotCommandArgumentParser;
import com.akjostudios.acsp.bot.util.command.argument.BotCommandArguments;
import com.akjostudios.acsp.bot.util.command.permission.BotCommandPermissionParser;
import com.akjostudios.acsp.bot.util.command.permission.BotCommandPermissionValidation;
import com.akjostudios.acsp.bot.util.command.permission.BotCommandPermissionValidator;
import com.akjostudios.acsp.bot.util.exception.AcspBotConfigException;
import io.github.akjo03.lib.logging.Logger;
import io.github.akjo03.lib.logging.LoggerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

public abstract class BotCommand {
	private final Logger LOGGER;

	protected final String name;
	private BotConfigCommand definition;

	private BotConfigService botConfigService;
	private DiscordMessageService discordMessageService;
	private ErrorMessageService errorMessageService;
	private BotCommandArgumentParserService botCommandArgumentParserService;
	private BotCommandArgumentParsingReportService botCommandArgumentParsingReportService;
	private CommandHelperService commandHelperService;
	private BotStringsService botStringsService;

	protected BotCommand(String name) {
		LOGGER = LoggerManager.getLogger(getClass());

		this.name = name;
	}

	public void setupServices(
			BotConfigService botConfigService,
			DiscordMessageService discordMessageService,
			ErrorMessageService errorMessageService,
			BotCommandArgumentParserService botCommandArgumentParserService,
			BotCommandArgumentParsingReportService botCommandArgumentParsingReportService,
			CommandHelperService commandHelperService,
			BotStringsService botStringsService
	) {
		this.botConfigService = botConfigService;
		this.discordMessageService = discordMessageService;
		this.errorMessageService = errorMessageService;
		this.botCommandArgumentParserService = botCommandArgumentParserService;
		this.botCommandArgumentParsingReportService = botCommandArgumentParsingReportService;
		this.commandHelperService = commandHelperService;
		this.botStringsService = botStringsService;
	}

	public abstract void initialize(@NotNull BotCommandInitializer initializer);
	public abstract void execute(@NotNull MessageReceivedEvent event, BotCommandArguments arguments);

	public void initializeInternal(@NotNull ApplicationContext applicationContext, @NotNull JDA jdaInstance) {
		// Get the definition of the command from the bot config
		BotConfigCommand definition = botConfigService.getCommandDefinition(name, Optional.empty());
		if (definition == null) {
			new AcspBotConfigException(
					"Command definition for " + name + " not found!",
					null,
					discordMessageService, errorMessageService, LOGGER
			).sendMessage(jdaInstance, AcspDiscordChannels.ADMIN_CHANNEL);
			return;
		}
		this.definition = definition;

		// Call the initialize method of the command
		initialize(BotCommandInitializer.of(applicationContext, jdaInstance));

		LOGGER.success("Initialized command with name \"" + name + "\"!");
	}

	public void executeInternal(MessageReceivedEvent event, String commandArgsStr) {
		if (definition == null) {
			return;
		}

		// Check if command is available, if not, send error message
		if (!definition.isAvailable()) {
			LOGGER.info("User " + event.getAuthor().getAsTag() + " tried to use unavailable command \"" + name + "\"!");

			event.getChannel().sendMessage(discordMessageService.createMessage(
					errorMessageService.getErrorMessage(
							"errors.command_unavailable.title",
							"errors.command_unavailable.description",
							List.of(),
							List.of(
									name
							),
							Optional.empty()
					)
			)).queue();
			return;
		}

		// Parse and validate permissions for command
		BotCommandPermissionParser permissionParser = new BotCommandPermissionParser(name, definition.getPermissions());
		BotCommandPermissionValidator permissionValidator = permissionParser.parse();
		BotCommandPermissionValidation permissionValidation = permissionValidator.getValidation(event.getGuildChannel(), event.getMember());

		if (!permissionValidation.isAllowed()) {
			LOGGER.info("User " + event.getAuthor().getAsTag() + " tried to use command \"" + name + "\" but was denied!");

			BotConfigMessage errorMessage = errorMessageService.getErrorMessage(
					"errors.command_missing_permissions.title",
					"errors.command_missing_permissions.description",
					List.of(),
					List.of(
							name
					),
					Optional.empty()
			);

			errorMessage.getEmbeds().get(0).setDescription(
					errorMessage.getEmbeds().get(0).getDescription() + " " + permissionValidation.getReason(botStringsService)
			);

			event.getChannel().sendMessage(discordMessageService.createMessage(errorMessage)).queue();

			return;
		}

		// Get the argument parser for the command
		BotCommandArgumentParser argumentParser = botCommandArgumentParserService.getArgumentParser(name, definition, commandArgsStr, event);
		if (argumentParser == null) {
			LOGGER.warn("User " + event.getAuthor().getAsTag() + " tried to use command \"" + name + "\" but getting argument parser failed!");
			return;
		}
		argumentParser.setupServices(
				discordMessageService,
				errorMessageService,
				commandHelperService,
				botConfigService,
				botCommandArgumentParsingReportService,
				botStringsService
		);

		// Parse the arguments
		BotCommandArguments arguments = argumentParser.parse();
		if (arguments == null) {
			LOGGER.warn("User " + event.getAuthor().getAsTag() + " tried to use command \"" + name + "\" but parsing arguments failed!");
			return;
		}

		execute(event, arguments);
	}

	// -------------- Getters --------------

	public String getName() {
		return name;
	}

	public BotConfigCommand getDefinition() {
		return definition;
	}

	protected BotConfigService getBotConfigService() {
		return botConfigService;
	}

	protected DiscordMessageService getDiscordMessageService() {
		return discordMessageService;
	}

	protected ErrorMessageService getErrorMessageService() {
		return errorMessageService;
	}
}