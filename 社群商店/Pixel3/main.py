import os

from discord import Intents
from discord.ext import commands

from Settings import Settings

settings = Settings()


def main(command_prefix: str, token: str):
    bot = commands.Bot(command_prefix=command_prefix, intents=Intents.all(), help_command=None)
    settings.bot = bot
    bot.settings = settings

    @bot.event
    async def on_ready():
        print("\n===== loading settings... =====")
        await settings.load_global_settings()

        for cog in os.listdir("./Cogs"):
            if cog.endswith(".py"):
                bot.load_extension("Cogs." + cog[:-3])
                print("Cog " + cog + " loaded successfully.")
        # bot.load_extension("test")

    bot.run(token)


if __name__ == "__main__":
    # settings.load_local_settings("./settings.json")
    settings.load_local_settings("./test_settings.json")
    main(settings.command_prefix, settings.bot_token)
