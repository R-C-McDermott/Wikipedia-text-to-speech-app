from gtts import gTTS
import pyttsx3
import os
import glob

class TextToSpeechConvertor:
    FORMAT = ".wav"
    OUTPUT_FILEPATH = "./AppUI/temp/"
    FILES = glob.glob(f"{OUTPUT_FILEPATH}*{FORMAT}")

    def __init__(self, wiki_content):
        self.wiki_content = wiki_content
        self.gtts_config = {"lang": "en", "slow": False}
        self.pyttsx3_config = {"rate": 150, "voice_index": 0}

    def clear_directory(self):
        for f in self.FILES:
            os.remove(f)

    def save_using_gtts(self):
        gtts_object = gTTS(text=self.wiki_content.build_full_text(),
                           lang=self.gtts_config["lang"],
                           slow=self.gtts_config["slow"])

        gtts_object.save(f"{self.OUTPUT_FILEPATH}{self.wiki_content.get_converted_title_to_filename()}{self.FORMAT}")

    def save_using_pyttsx3(self):
        engine = pyttsx3.init()
        # config properties
        engine.setProperty('rate', self.pyttsx3_config["rate"])
        voices = engine.getProperty('voices')
        engine.setProperty('voice', voices[self.pyttsx3_config["voice_index"]].id)
        engine.save_to_file(self.wiki_content.build_full_text(),
                            f"{self.OUTPUT_FILEPATH}{self.wiki_content.get_converted_title_to_filename()}{self.FORMAT}")
        engine.runAndWait()

    def print_output_path(self):
        print(f"Saved file to: {self.OUTPUT_FILEPATH}{self.wiki_content.get_converted_title_to_filename()}{self.FORMAT}")
