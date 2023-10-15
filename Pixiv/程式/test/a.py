def calc(i: int) -> int:
    if i <= 1:
        return i
    else:
        return calc(i - 1) + calc(i - 2)


print(calc(int(input())))
