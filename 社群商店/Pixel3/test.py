def calc(i, c):
    if i < 2:
        return i
    else:
        return calc(i - c, c + 1)


print(calc(int(input()), 0))


# 5
#