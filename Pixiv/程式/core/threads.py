import threading
import time
from typing import Callable
from core.classes.Task import Task

from core.config import Config


class Threads(threading.Thread):
    def __init__(self, config: Config):
        super().__init__()
        self._config = config
        self._working_tasks = []
        self._work_waiting_list = []
        self._stop = False
        self.checker_thread = threading.Thread(target=self._working_status_checker)
        self.checker_thread.start()

    def get_working_task_list(self) -> list:
        return self._working_tasks

    def _working_status_checker(self):
        while not self._stop:
            for task in self._working_tasks:
                if not task.get_thread().is_alive():
                    self._working_tasks.remove(task)
            if len(self._work_waiting_list) > 0:
                for _i in range(self._config.get_max_thread_count() - len(self._working_tasks)):
                    task: Task = self._work_waiting_list[0]
                    self._work_waiting_list.remove(task)
                    self._working_tasks.append(task)
                    task.start()
            time.sleep(self._config.get_console_update_interval())

    def add_task(self, task_name: str, func: Callable):
        task = Task(task_name, func)
        if len(self.get_working_task_list()) >= self._config.get_max_thread_count():
            self._work_waiting_list.append(task)
        else:
            self._working_tasks.append(task)
            task.start()

    def stop(self):
        self._stop = True
