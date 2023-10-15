from asciimatics.screen import ManagedScreen
from time import sleep
#
#
# with ManagedScreen() as screen:
#     screen.print_at("Hello World", 0, 1)
#     screen.refresh()
#     sleep(1)
#     screen.print_at("Hello Kitsune", 0 ,1)
#     screen.refresh()
#     sleep(10)
with ManagedScreen() as screen:
    while True:
        screen.print_at("Hello World", 0, 0)

        screen.refresh()
        sleep(1)
