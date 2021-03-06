import cv2
import numpy as np
from keras.models import load_model
from PIL import Image
from utils import HAARCASCADE_FRONTALFACE_XML_PATH, EMOTION_CLASSIFIER_V2_PATH, EMOTION_CLASSIFIER_PATH, get_emotion
import tensorflow as tf


class VideoCameraLocal(object):
    def __init__(self):
        # Using OpenCV to capture from device 0. If you have trouble capturing
        # from a webcam, comment the line below out and use a video file
        # instead.
        self.video = cv2.VideoCapture(0)
        self.face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
        self.emotion_classifier = load_model(EMOTION_CLASSIFIER_PATH, compile=False)
        self.emotion_target_size = self.emotion_classifier.input_shape[1:3]
        self.graph = tf.get_default_graph()

    def __del__(self):
        self.video.release()

    @property
    def get_frame(self):
        success, image = self.video.read()

        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = gray.astype('uint8')

        emotion_text = 'None'

        faces = self.face_detection_model.detectMultiScale(gray, 1.3, 5)
        for (x, y, w, h) in faces:
            face = gray[y:y + h, x:x + w]
            cv2.rectangle(image, (x, y), (x + w, y + h), (255, 0, 0), 2)

            try:
                face = np.expand_dims(face, axis=0)
                face = Image.fromarray(face.astype('uint8'), 'RGB')
                face = np.array(face)
                face = cv2.resize(face, (self.emotion_target_size))
                face = np.reshape(face, [1, 100, 100, 3])

            except Exception as e:
                print(e)
                continue

            with self.graph.as_default():
                prediction = self.emotion_classifier.predict(face)

            emotion_label_arg = np.argmax(prediction)
            emotion_text = get_emotion(emotion_label_arg)

            fontScale = 1
            fontColor = (255, 255, 255)
            lineType = 2
            cv2.putText(image, emotion_text, (x + 40, y + 40),
                        cv2.FONT_HERSHEY_SIMPLEX,
                        int(fontScale), fontColor, int(lineType), cv2.LINE_AA)

        # Encoding the image as JPG
        ret, jpeg = cv2.imencode('.jpg', image)
        return jpeg.tobytes()
