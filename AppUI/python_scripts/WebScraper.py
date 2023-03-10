from bs4 import BeautifulSoup
import requests
import re

from WikiContent import WikiContent

# "https://en.wikipedia.org/wiki/Cabernet_Sauvignon"
# "https://en.wikipedia.org/wiki/2018_FIFA_World_Cup_final"
# "https://en.wikipedia.org/wiki/Standing_Up_in_the_Milky_Way"

def get_wiki_content(url):
    r = requests.get(url)
    html = r.content
    bs = BeautifulSoup(html, 'html.parser')

    # Get Title
    title = bs.find("h1", class_="firstHeading mw-first-heading").get_text()

    # Get main body of text
    p_tags = bs.find_all("p")

    # String cleaning
    text = [t.get_text().replace("\n", " ") for t in p_tags]
    text = [re.sub(r'\[[0-9]+\]', '', i) for i in text]
    text_string = "".join(text)

    wiki_content = WikiContent(title, text_string)

    return wiki_content