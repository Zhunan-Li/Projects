import sys
import requests

link = "https://i.pximg.net/img-original/img/2021/09/03/00/41/22/92463499_p0.png"
file_name = "download.png"
with open(file_name, "wb") as f:
    print("Downloading %s" % file_name)
    response = requests.get(link, stream=True, headers={"referer": "https://www.pixiv.net/"})
    total_length = response.headers.get('content-length')

    if total_length is None:  # no content length header
        f.write(response.content)
    else:
        dl = 0
        total_length = int(total_length)
        for data in response.iter_content(chunk_size=4096):
            dl += len(data)
            f.write(data)
            done = int(50 * dl / total_length)
            print("", end="\r{}%".format(str(int((dl / total_length) * 100))))  # 百分比
            # sys.stdout.write("\r[%s%s]" % ('=' * done, ' ' * (50 - done))) # 進度條
            sys.stdout.flush()
