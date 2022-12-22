class WikiContent:
    def __init__(self, title, content):
        self.title = title
        self.content = content

    def build_full_text(self):
        return self.title + "\n\n" + self.content

    def get_converted_title_to_filename(self):
        return self.title.strip().replace(" ", "_")
