import threading
import sys

from core.config import Config
from core.threads import Threads
from core.clipboard import ClipBoard
from time import sleep
from asciimatics.screen import ManagedScreen
from asciimatics.screen import Screen


class Console:
    def __init__(self, config: Config, threads: Threads, clipboard: ClipBoard):
        self._config = config
        self._threads = threads
        self._clipboard = clipboard
        self._stop = False
        self.value = 0
        self.console_thread = threading.Thread(target=self.run)
        self.console_thread.start()

    def stop(self):
        self._clipboard.stop()
        self._threads.stop()
        self._stop = True
        sys.exit(1)

    def run(self):
        with ManagedScreen() as screen:
            blank_space = " " * screen.width
            while not self._stop:
                index = 0
                for task in self._threads.get_working_task_list():
                    screen.print_at("[Worker #{}] ".format(index + 1) + task.name + blank_space, 0,
                                    index, Screen.COLOUR_GREEN)
                    index += 1
                for _i in range(self._config.get_max_thread_count() - index):
                    screen.print_at("[Worker #{}] Idling ".format(index + 1) + blank_space, 0, index,
                                    Screen.COLOUR_YELLOW)
                    index += 1
                screen.print_at("In queue {}".format(len(self._threads._work_waiting_list)) + blank_space, 0, index + 1,
                                Screen.COLOUR_CYAN)
                screen.refresh()
                sleep(self._config.get_console_update_interval())
