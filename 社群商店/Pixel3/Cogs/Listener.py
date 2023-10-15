from discord import Interaction
from discord.abc import GuildChannel
from discord.ext import commands
from discord.errors import NotFound

from Classes.Role import Role
from Classes.Ticket import Ticket


class Listener(commands.Cog):
    def __init__(self, bot: commands.Bot):
        self.bot = bot
        self.ticket_system = Ticket(bot)
        self.role_system = Role(bot)

    @commands.Cog.listener()
    async def on_interaction(self, interaction: Interaction):
        #
        #   Button Interaction
        #
        if interaction.data["component_type"] == 2:
            custom_id = interaction.data["custom_id"]
            if custom_id == self.bot.settings.global_settings["commission_open_button_id"]:
                await self.ticket_system.create_commission_ticket(interaction)
            elif custom_id == self.bot.settings.global_settings["support_open_button_id"]:
                await self.ticket_system.create_support_ticket(interaction)
            elif custom_id == self.bot.settings.global_settings[
                "close_ticket_button_id"] and not interaction.channel.category.name.lower().startswith("completed"):
                await self.ticket_system.close_ticket(interaction)
            elif custom_id == self.bot.settings.global_settings["accept_rules_button_id"]:
                await self.role_system.add_role(interaction.user, interaction.guild)
        if not interaction.response.is_done():
            try:
                await interaction.response.defer()
            except NotFound:
                pass

    @commands.Cog.listener()
    async def on_guild_channel_delete(self, channel: GuildChannel):
        await self.ticket_system.on_channel_delete(channel)


def setup(bot: commands.Bot):
    bot.add_cog(Listener(bot))
