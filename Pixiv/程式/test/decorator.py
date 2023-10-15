# from clipboard import ClipBoard
#
# clipboard = ClipBoard(interval=0.5)
# clipboard.start()
#
from typing import Callable
from typing import TypeVar

CFT = TypeVar('CFT', bound='CoroFunc')
funcList = []


# def listener() -> Callable[[CFT], CFT]:
#     def wrapper(func: CFT) -> CFT:
#         funcList.append(func)
#         return func
#
#     return wrapper
def listener():
    def wrapper(func):
        funcList.append(func)
        return func

    return wrapper


@listener()
def main():
    print("Hello World")


@listener()
def abc():
    print("Hello abc")


# trigger event
for f in funcList:
    f()
