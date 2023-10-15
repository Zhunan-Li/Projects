from discord import Member, Guild
from discord.ext import commands
from discord.utils import get as nextcord_get


class Role:
    def __init__(self, bot: commands.Bot):
        self.bot = bot

    async def add_role(self, member: Member, guild: Guild):
        await member.add_roles(nextcord_get(guild.roles, id=self.bot.settings.global_settings["member_role_id"]))
