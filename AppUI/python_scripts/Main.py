from WebScraper import get_wiki_content
from TextToSpeechConvertor import TextToSpeechConvertor


def main():
    wiki_content = get_wiki_content()
    text_to_speech = TextToSpeechConvertor(wiki_content)
    try:
        print("Creating audiofile...")
        text_to_speech.save_using_gtts()
        print("Audiofile created")
    except:
        print("gtts unable to parse data")
        print("Trying pyttsx3...")
        text_to_speech.save_using_pyttsx3()
        print("Audiofile created")

if __name__ == '__main__':
    main()
