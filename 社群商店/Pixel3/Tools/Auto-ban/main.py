import aiohttp
import asyncio
import requests

headers = {
    "Authorization": "Bot TOKEN",
    "User-Agent": "DiscordBot "
}


# 429 被擋 太快了 XD
# async def task(user_id: str):
#     # https://discord.com/api/v9/guilds/851084024033705995/bans/ user id
#     async with aiohttp.request("put", "https://discord.com/api/v9/guilds/851084024033705995/bans/{}".format(user_id),
#                                headers=headers, json={"delete_message_days": "0"}) as response:
#         print("{} {} Banned".format(response.status, user_id))
# def main():
#     with open("./users.txt", "r") as f:
#         tasks = [task(x.split(":")[1][:-1]) for x in f.readlines()]
#     asyncio.run(asyncio.wait(tasks))

def main():
    success = 0
    fail = 0
    fail_list = []
    with open("./users.txt", "r") as f:
        for line in f.readlines():
            user_id = str(line.split(":")[1]).replace("\n", "")
            response = requests.put("https://discord.com/api/v9/guilds/851084024033705995/bans/{}".format(user_id),
                                    headers=headers, json={"delete_message_days": "0"})
            print("{} {} Banned".format(response.status_code, user_id))
            if response.status_code == 204:
                success += 1
            else:
                fail += 1
                fail_list.append(user_id)
    print("success: {}".format(success))
    print("fail: {}".format(fail))
    print("fail list:")
    print(fail_list)


if __name__ == "__main__":
    main()
