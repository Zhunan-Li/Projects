import base64
import json

import discord
from discord.ext import commands


class Settings:
    def __init__(self):
        self.bot: commands.Bot = None
        self.json_local_settings = None
        self.bot_token = None
        self.command_prefix = None
        self.guild: discord.Guild = None
        self.global_settings_channel: discord.TextChannel = None
        self.global_settings_message: discord.Message = None
        self.show_global_settings_message: discord.Message = None
        self.global_settings = {}
        self.default_settings = '{' \
                                '"show_settings_message_id": 0,' \
                                '"admin_message_channel": 0,' \
                                '"bot_talk_channel_id": 0,' \
                                '"accept_rules_button_id": "accept_rules",' \
                                '"member_role_id": 0,' \
                                '"commission_open_channel":0,' \
                                '"commission_open_message":0,' \
                                '"commission_open_button_id":"commission_open_button",' \
                                '"commission_working_category":0,' \
                                '"support_open_channel":0,' \
                                '"support_open_message":0,' \
                                '"support_open_button_id":"support_open_button",' \
                                '"support_working_category":0,' \
                                '"close_ticket_button_id":"ticket_close",' \
                                '"max_closed_categories_count":5' \
                                '}'

    def load_local_settings(self, settings_path: str):
        with open(settings_path, "r") as settings_file:
            self.json_local_settings = json.load(settings_file)
            self.bot_token = self.json_local_settings["token"]
            self.command_prefix = self.json_local_settings["command_prefix"]

    async def load_global_settings(self):
        #
        #   Loading Guild
        #
        guild_id = self.json_local_settings["guild_id"]
        if guild_id == 0:
            raise Exception("guild_id is invalid")
        elif self.bot.get_guild(guild_id) is None:
            raise Exception("Guild of id " + str(guild_id) + " is not found.")
        else:
            self.guild = self.bot.get_guild(guild_id)
            print("Guild set to " + self.guild.name)
            #
            # Loading Settings Channel and Settings Message
            #
            global_settings_channel_id = self.json_local_settings["settings_channel_id"]
            global_settings_message_id = self.json_local_settings["settings_message_id"]
            if global_settings_channel_id == 0:
                raise Exception("settings_channel_id is invalid.")
            elif not isinstance(self.bot.get_channel(global_settings_channel_id), discord.TextChannel):
                raise Exception("Settings channel of channel id " + str(global_settings_channel_id) + " is not found.")
            else:
                self.global_settings_channel = self.bot.get_channel(global_settings_channel_id)
                print("Settings channel set to " + self.global_settings_channel.name)
                try:
                    self.global_settings_message = await self.global_settings_channel.fetch_message(
                        global_settings_message_id)
                    self.global_settings = json.loads(
                        base64.b64decode(self.global_settings_message.content.encode("ascii")).decode("ascii").replace(
                            "'", '"'))
                except discord.NotFound:
                    encoded_settings = base64.b64encode(self.default_settings.encode("ascii"))
                    self.global_settings_message = await self.global_settings_channel.send(
                        encoded_settings.decode("ascii"))
                    self.global_settings = json.loads(self.default_settings)
                    await self.global_settings_channel.send(
                        "settings message ID " + str(
                            self.global_settings_message.id) + " \n")
                try:
                    self.show_global_settings_message = await self.global_settings_channel.fetch_message(
                        self.global_settings["show_settings_message_id"])
                except discord.NotFound:
                    self.show_global_settings_message = await self.global_settings_channel.send("a")
                    await self.set_global_settings("show_settings_message_id", self.show_global_settings_message.id)
                await self.update_new_settings()

    async def update_new_settings(self):
        j = json.loads(self.default_settings)
        for i in j:
            if i not in self.global_settings:
                self.global_settings[i] = j[i]
                await self.update_global_settings()
                await self.update_settings_display_string()

    async def set_global_settings(self, option: str, value):
        if option in self.global_settings:
            self.global_settings[option] = value
            await self.update_global_settings()
            await self.update_settings_display_string()
        else:
            raise Exception("Settings not found.")

    async def update_global_settings(self):
        await self.global_settings_message.edit(
            content=base64.b64encode(str(self.global_settings).encode("ascii")).decode("ascii"))

    async def update_settings_display_string(self):
        await self.show_global_settings_message.edit(
            content="=====Settings===== \n" + json.dumps(self.global_settings, sort_keys=True, indent=4))
