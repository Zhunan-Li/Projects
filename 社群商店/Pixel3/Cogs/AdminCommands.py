import discord
from discord.ext import commands
from discord import ui, ButtonStyle, Embed
from Classes.Ticket import CloseTicketButton


class AddButton(ui.View):
    def __init__(self):
        super().__init__()


class SettingsCommands(commands.Cog):
    def __init__(self, bot: commands.Bot):
        self.bot = bot
        self.embed_default_color = 0x13bebe
        self.embed_default_title = "PixelBarrel's Model Workshop"

    @commands.command()
    @commands.has_permissions(administrator=True)
    async def embed(self, ctx: commands.Context, channel_id: int = None):
        target_channel: discord.TextChannel = self.bot.get_channel(channel_id)
        if channel_id is None or target_channel is None:
            await ctx.send("{}embed channel_id".format(self.bot.settings.command_prefix))
        else:
            await ctx.send("輸入 \n "
                           "ok 確定 \n "
                           "cancel 取消 \n"
                           "title author_name author_icon image field_add")
            embed = discord.Embed(title=self.embed_default_title, colour=self.embed_default_color)
            if ctx.message.guild.icon is None:
                embed.set_author(name=(str(ctx.message.created_at))[:-7])
            else:
                embed.set_author(name=str(ctx.message.created_at)[:-7], icon_url=ctx.message.guild.icon.url)
            embed_message: discord.Message = await ctx.send(content="目標頻道 <#{}>".format(channel_id), embed=embed)

            def check(message: discord.Message):
                return message.author.id == ctx.author.id and message.channel.id == ctx.channel.id

            while True:
                input_message: discord.Message = await self.bot.wait_for('message', check=check)
                operate = input_message.content.split(" ")[0]
                content = input_message.content.replace(operate + " ", "")
                await input_message.delete()
                if operate == "ok":
                    await target_channel.send(embed=embed)
                    await ctx.send("送出")
                    break
                elif operate == "cancel":
                    await ctx.send("取消")
                    break
                else:
                    if operate == "title":
                        embed.title = content
                    elif operate == "author_name":
                        embed.set_author(name=content)
                    elif operate == "author_icon":
                        embed.set_author(name=embed.author.name, icon_url=content)
                    elif operate == "image":
                        embed.set_image(url=content)
                    elif operate == "field_add":
                        embed.add_field(name=content.split("\n")[0], value=content.split("\n")[1], inline=False)
                    await embed_message.edit(embed=embed)

    @commands.command()
    @commands.has_permissions(administrator=True)
    async def set_settings(self, ctx: commands.Context, option: str, value):
        try:
            value = int(value)
        except Exception:
            pass
        if option in self.bot.settings.global_settings:
            await self.bot.settings.set_global_settings(option, value)
        else:
            await ctx.send("Settings not found.")

    @commands.command()
    @commands.has_permissions(administrator=True)
    async def set_button(self, ctx: commands.Context, channel_id: int, message_id: int,
                         button_color: str, custom_id: str, emoji: str, *label: str):
        try:
            channel: discord.TextChannel = await self.bot.fetch_channel(channel_id)
            message: discord.Message = await channel.fetch_message(message_id)
            color = ButtonStyle.green
            if button_color == "blue":
                color = ButtonStyle.primary
            elif button_color == "red":
                color = ButtonStyle.danger
            elif button_color == "gray":
                color = ButtonStyle.secondary
            view = AddButton()
            label = " ".join(label)
            button = ui.Button(custom_id=custom_id, style=color, label=label, emoji=emoji)
            view.add_item(button)
            await message.edit(view=view)
        except discord.NotFound:
            await ctx.send("not found")

    @commands.command()
    @commands.has_permissions(administrator=True)
    async def clear_button(self, ctx: commands.Context, channel_id: int, message_id: int):
        try:
            channel: discord.TextChannel = await self.bot.fetch_channel(channel_id)
            message: discord.Message = await channel.fetch_message(message_id)
            await message.edit(view=None)
        except discord.NotFound:
            await ctx.send("not found")

    @commands.command()
    @commands.has_permissions(administrator=True)
    async def close_ticket(self, ctx: commands.Context):
        await ctx.message.delete()
        embed = Embed(title="If you wish to close the ticket, click on the button below.", color=0xff131a)
        button = CloseTicketButton()
        for btn in button.children:
            btn.custom_id = self.bot.settings.global_settings["close_ticket_button_id"]
        await ctx.send(embed=embed, view=button)


def setup(bot: commands.Bot):
    bot.add_cog(SettingsCommands(bot))
