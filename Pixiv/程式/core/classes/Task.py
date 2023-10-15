from typing import Callable
from threading import Thread


class Task:
    def __init__(self, name: str, func: Callable):
        self.name = name
        self._func = func
        self._thread: Thread = Thread(target=func, args=(self, ))

    def start(self):
        self._thread.start()

    def get_func(self) -> Callable:
        return self._func

    def get_thread(self) -> Thread:
        return self._thread
