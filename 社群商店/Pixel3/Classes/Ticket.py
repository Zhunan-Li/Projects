from discord.utils import get as discord_get
from discord import CategoryChannel, Interaction, ui, ButtonStyle, PermissionOverwrite, TextChannel

from discord.ext import commands
from discord.errors import HTTPException
from discord.abc import GuildChannel

from a import MyModal

class CloseTicketButton(ui.View):
    def __init__(self):
        super().__init__()

    @ui.button(label="Close Ticket", style=ButtonStyle.red, emoji="❌")
    async def close_ticket(self, button: ui.Button, interation: Interaction):
        self.stop()


class Ticket:
    def __init__(self, bot: commands.Bot):
        self.bot = bot

    async def create_commission_ticket(self, interaction: Interaction):
        if await self.check_ticket_opened(interaction.user.name,
                                          discord_get(self.bot.settings.guild.categories,
                                                      id=self.bot.settings.global_settings[
                                                          "commission_working_category"])):
            await interaction.response.send_message("You cannot open more than 1 ticket at a time!", ephemeral=True)
        else:
            await self.open_ticket(interaction,
                                   self.bot.settings.global_settings["commission_working_category"])

    async def create_support_ticket(self, interaction: Interaction):
        if await self.check_ticket_opened(interaction.user.name,
                                          discord_get(self.bot.settings.guild.categories,
                                                      id=self.bot.settings.global_settings[
                                                          "support_working_category"])):
            await interaction.response.send_message("You cannot open more than 1 ticket at a time!", ephemeral=True)
        else:
            await self.open_ticket(interaction, self.bot.settings.global_settings["support_working_category"])

    async def check_ticket_opened(self, user_name: str, category: CategoryChannel):
        for channel in category.channels:
            if channel.name.lower().replace("-", " ") == user_name.lower():
                return True
        return False

    async def open_ticket(self, interaction: Interaction, category_id: int):
        await interaction.response.send_modal(MyModal())
        return
        channel = await self.bot.settings.guild.create_text_channel(interaction.user.name,
                                                                    category=discord_get(
                                                                        self.bot.settings.guild.categories,
                                                                        id=category_id))
        await channel.set_permissions(interaction.user, read_messages=True, send_messages=True, attach_files=True,
                                      external_emojis=True, read_message_history=True)
        close_ticket_button = CloseTicketButton()
        for btn in close_ticket_button.children:
            btn.custom_id = self.bot.settings.global_settings["close_ticket_button_id"]
        close_operate = await channel.send(
            interaction.user.mention + "\nPlease provide the order ID of the products you wish to get support for, and details of the problem. We will get back to you when we are available . Thanks for the patient."
                                       "\n \n"
                                       "Order ID can be found in the MCModels purchase email or on mcmodels.net.",
            view=close_ticket_button)
        await close_operate.pin()

    async def close_ticket(self, interaction: Interaction):
        channel = interaction.channel
        for user in channel.members:
            await channel.set_permissions(user, read_messages=False, send_messages=False,
                                          attach_files=False, external_emojis=False,
                                          read_message_history=False)
        original_category: CategoryChannel = channel.category
        new_channel_name = channel.name
        if original_category.id == self.bot.settings.global_settings["commission_working_category"]:
            new_channel_name = "c-" + new_channel_name
        if original_category.id == self.bot.settings.global_settings["support_working_category"]:
            new_channel_name = "s-" + new_channel_name
        completed_categories = []
        for category in interaction.guild.categories:
            if category.name.lower().startswith("completed"):
                completed_categories.append(category)
        for i in range(0, len(completed_categories)):
            try:
                await channel.edit(name=new_channel_name, category=completed_categories[i])
                break
            except HTTPException:
                #
                #   該分類達到最大上限(50)個Channel
                #
                if i == (len(completed_categories) - 1):
                    #
                    #   沒找到有空間的Category
                    #
                    if len(completed_categories) < self.bot.settings.global_settings["max_closed_categories_count"]:
                        #
                        #   創新的
                        #
                        overwrites = {
                            self.bot.settings.guild.default_role: PermissionOverwrite(view_channel=False)
                        }
                        category = await self.bot.settings.guild.create_category(
                            name="Completed Tickets {}".format(len(completed_categories) + 1), overwrites=overwrites,
                            position=(len(interaction.guild.categories) - 1))
                        await channel.edit(name=new_channel_name, category=category)
                    else:
                        #
                        #   到達 completed categories 數量上限
                        #
                        warn_channel = await self.bot.settings.global_settings_channel.fetch_channel(
                            self.bot.settings.global_settings["admin_message_channel"])
                        await warn_channel.send("Completed Tickets 滿啦啦啦啦啦啦啦啦")

    async def on_channel_delete(self, channel: GuildChannel):
        if isinstance(channel, TextChannel):
            channel_category = channel.category
            if channel_category.name.lower().startswith("completed") and len(channel_category.channels) == 0:
                await channel_category.delete()
