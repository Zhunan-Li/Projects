import discord
from discord.ext import commands


class BotTalk(commands.Cog):
    def __init__(self, bot: commands.Bot):
        self.bot = bot

    @commands.Cog.listener()
    async def on_message(self, message: discord.Message):
        if message.channel.id == self.bot.settings.global_settings["bot_talk_channel_id"]:
            colorCode = 0x13bebe
            if len(message.content.split("\n")) != 3:
                colorLine = message.content.split("\n")[3]
                colorCode = discord.Color.from_rgb(int(colorLine.split(",")[0]), int(colorLine.split(",")[1]),
                                                    int(colorLine.split(",")[2]))
            embed = discord.Embed(title="PixelBarrel's Model Workshop", color=colorCode)
            embed.set_author(name=str(message.created_at)[:-7], icon_url=message.guild.icon.url)
            embed.add_field(name=message.content.split("\n")[1],
                            value=message.content.split("\n")[2].replace("%n%", "\n"),
                            inline=False)
            if message.attachments:
                embed.set_image(url=message.attachments[0].url)
            await self.bot.get_channel(int(message.content.split("\n")[0])).send(embed=embed)


def setup(bot: commands.Bot):
    bot.add_cog(BotTalk(bot))
