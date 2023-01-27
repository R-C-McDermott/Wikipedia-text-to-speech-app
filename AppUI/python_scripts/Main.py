from WebScraper import get_wiki_content
from TextToSpeechConvertor import TextToSpeechConvertor
import sys


def main():
    wiki_url = str(sys.argv[1])
    # audio_type = str(sys.argv[2])
    audio_type = "pyttsx3"
    wiki_content = get_wiki_content(wiki_url)
    text_to_speech = TextToSpeechConvertor(wiki_content)

    text_to_speech.clear_directory()
    if audio_type == "gtts":
        try:
            print("Creating audiofile...")
            text_to_speech.save_using_gtts()
            text_to_speech.print_output_path()
        except:
            print("gtts unable to parse data")
            print("Trying pyttsx3...")
            text_to_speech.save_using_pyttsx3()
            text_to_speech.print_output_path()
    elif audio_type == "pyttsx3":
        print("Using pyttsx3...")
        text_to_speech.save_using_pyttsx3()
        text_to_speech.print_output_path()


if __name__ == '__main__':
    main()
