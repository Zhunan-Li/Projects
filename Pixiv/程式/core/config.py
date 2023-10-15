import json
import shutil


class Config:
    def __init__(self):
        self._init()
        var = self._configs

    def _init(self):
        try:
            with open("config.json", "r") as config_file:
                self._configs = json.load(config_file)
        except FileNotFoundError:
            self._create_config()
        except Exception:
            raise Exception("An error occur while reading config file!")

    def _create_config(self):
        shutil.copy("./core/config.json", "./")
        self._init()

    def get_max_thread_count(self) -> int:
        return self._configs["Core"]["Max_Thread_Count"]

    def get_console_update_interval(self) -> int:
        return self._configs["Core"]["Console_Update_Interval"]

    def get_clipboard_check_interval(self) -> int:
        return self._configs["Core"]["Clipboard_Check_Interval"]

    def get_pixiv_save_location(self) -> str:
        return self._configs["Pixiv"]["Save_Location"]
