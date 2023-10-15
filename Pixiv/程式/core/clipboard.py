import threading
import time
import pyclip

from typing import Callable
from core.config import Config


class ClipBoard(threading.Thread):
    def __init__(self, config: Config):
        super(ClipBoard, self).__init__()
        self._config = config
        self._eventFuncs = []
        self._stopping = False
        self.start()

    def listener(self) -> Callable:
        def decorator(func: Callable) -> Callable:
            self._eventFuncs.append(func)
            return func

        return decorator

    def run(self):
        recent_value = pyclip.paste()
        while not self._stopping:
            temp_value = pyclip.paste()
            if temp_value != recent_value:
                recent_value = temp_value
                for func in self._eventFuncs:
                    func(recent_value)
            time.sleep(self._config.get_clipboard_check_interval())

    def stop(self):
        self._stopping = True
