import requests
import json
from bs4 import BeautifulSoup

link = "https://www.pixiv.net/artworks/92463499"
req = requests.get(link, headers={"referer": "https://www.pixiv.net/"})
soup = BeautifulSoup(req.content, 'lxml')
jdata = json.loads(soup.findAll('meta')[-1].get("content"))
illustration_title = jdata[""]
illustration_id = link.split("/")[-1]
original_image_url = jdata["illust"]["{}".format(illustration_id)]["urls"]["original"]
print(original_image_url)
