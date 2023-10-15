import requests
import json
import os

from bs4 import BeautifulSoup
from core.classes.Task import Task
from modules.File import slugify


class IllustrationInformation:
    def __init__(self, raw_data, illust_id: int, title: str, description: str, create_date: str,
                 upload_date: str, urls, tags, alt: str, width: int, height: int):
        self.rawData = raw_data
        self.ID = illust_id
        self.title = title
        self.description = description
        self.createDate = create_date
        self.uploadDate = upload_date
        self.urls = urls
        self.tags = tags
        self.alt = alt
        self.width = width
        self.height = height


class AuthorInformation:
    def __init__(self, raw_data, user_id: int, name: str, avatar_url_small: str, avatar_url_big: str):
        self.rawData = raw_data
        self.ID = user_id
        self.name = name
        self.avatarURLSmall = avatar_url_small
        self.avatarURLBig = avatar_url_big


class Pixiv:
    def __init__(self, link: str):
        self.link = link
        self.author_information: AuthorInformation = None
        self.illustration_information: IllustrationInformation = None
        self._init()

    def _init(self):
        req = requests.get(self.link, headers={"referer": "https://www.pixiv.net/"})
        soup = BeautifulSoup(req.content, 'lxml')
        jdata = json.loads(soup.findAll('meta')[-1].get("content"))

        image_id = self.link.split("/")[-1]
        illustration_data = jdata["illust"][image_id]
        title = illustration_data["illustTitle"]
        description = illustration_data["description"]
        create_date = illustration_data["createDate"]
        upload_date = illustration_data["uploadDate"]
        urls = illustration_data["urls"]
        tags = illustration_data["tags"]
        alt = illustration_data["alt"]
        width = illustration_data["width"]
        height = illustration_data["height"]
        ill_info = IllustrationInformation(illustration_data, int(image_id), title, description, create_date,
                                           upload_date,
                                           urls,
                                           tags, alt, width, height)

        user_id = illustration_data["userId"]
        author_data = jdata["user"][user_id]
        name = author_data["name"]
        avatar_url_small = author_data["image"]
        avatar_url_big = author_data["imageBig"]
        author_info = AuthorInformation(author_data, user_id, name, avatar_url_small, avatar_url_big)

        self.author_information = author_info
        self.illustration_information = ill_info

    def download(self, path: str, task: Task = None):
        image_url = self.illustration_information.urls["original"]
        directory_name = "{}({})/".format(slugify(self.author_information.name), self.author_information.ID)
        author_directory = os.path.join(path, directory_name)
        try:
            os.mkdir(author_directory)
        except FileExistsError:
            pass
        illust_name = "{}({}).{}".format(slugify(self.illustration_information.title), self.illustration_information.ID,
                                         image_url.split(".")[-1])
        with open(author_directory + illust_name, "wb") as f:
            response = requests.get(image_url, stream=True, headers={"referer": "https://www.pixiv.net/"})
            total_length = response.headers.get('content-length')
            if total_length is None:
                f.write(response.content)
            else:
                dl = 0
                total_length = int(total_length)
                for data in response.iter_content(chunk_size=4096):
                    dl += len(data)
                    f.write(data)
                    if task is not None:
                        done = str(int((dl / total_length) * 100))
                        task.name = "Pixiv Downloading " + slugify(
                            self.illustration_information.title) + " {}%".format(done)
