import unicodedata
import re


# def slugify(value, allow_unicode=False) -> str:
#     value = str(value)
#     if allow_unicode:
#         value = unicodedata.normalize('NFKC', value)
#     else:
#         value = unicodedata.normalize('NFKD', value).encode('ascii', 'ignore').decode('ascii')
#     value = re.sub(r'[^\w\s-]', '', value.lower())
#     return re.sub(r'[-\s]+', '-', value).strip('-_')
def slugify(value) -> str:
    char = "“>、<、:、/:/”"
    for c in char:
        value = value.replace(c, "")
    return value
