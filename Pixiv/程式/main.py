import time
import re

from core.clipboard import ClipBoard
from core.config import Config
from core.threads import Threads
from core.console import Console
from core.classes.Task import Task
from modules.Pixiv import Pixiv

clipboard: ClipBoard
threads: Threads
console: Console


def main():
    global clipboard, threads, console

    config = Config()
    clipboard = ClipBoard(config)
    threads = Threads(config)
    console = Console(config, threads, clipboard)

    @clipboard.listener()
    def pixiv_downloader(clipboard_content: bytes):
        try:
            clipboard_content = clipboard_content.decode("ascii")
        except UnicodeDecodeError:
            return
        if not re.match("https://www.pixiv.net/artworks/*", clipboard_content) is None:
            def pixiv_task(task: Task):
                pixiv = Pixiv(clipboard_content)
                pixiv.download(config.get_pixiv_save_location(), task)

            threads.add_task("Pixiv Getting Information...", pixiv_task)

    while True:
        time.sleep(0.5)


if __name__ == '__main__':
    print("", end="\rInitializing...")
    main()
    # https://www.pixiv.net/artworks/92463499
