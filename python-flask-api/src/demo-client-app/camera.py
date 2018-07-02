import cv2
import requests
import json
import time


class VideoCamera(object):
    def __init__(self):
        self.video = cv2.VideoCapture(0)
        self.addr = 'http://spring-boot-webapp.appspot.com'
        self.apiKey = '/emoApi/aaa8fb99-544a-4eba-a9c3-bbc7f6a09e8e'


    def __del__(self):
        self.video.release()

    @property
    def get_frame(self):
        time.sleep(0.2)
        success, image = self.video.read()
        _, img_encoded = cv2.imencode('.jpg', image)

        url = self.addr + self.apiKey
        response = requests.post(url, files={'file': img_encoded})

        data = json.loads(response.text)
        for key in data:
            if key.startswith('face'):
                x = int(data[key]['coordinates']['x'])
                y = int(data[key]['coordinates']['y'])
                w = int(data[key]['coordinates']['width'])
                h = int(data[key]['coordinates']['height'])
                emotion = data[key]['emotion']
                fontScale = 1
                fontColor = (255, 255, 255)
                lineType = 2

                cv2.putText(image, emotion, (x + 40, y + 40),
                            cv2.FONT_HERSHEY_SIMPLEX,
                            int(fontScale), fontColor, int(lineType), cv2.LINE_AA)
                cv2.rectangle(image, (x, y), (x + w, y + h), (255, 0, 0), 2)
        # Encoding the image as JPG
        ret, jpeg = cv2.imencode('.jpg', image)
        return jpeg.tobytes()
