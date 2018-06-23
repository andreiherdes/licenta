import cv2
import numpy as np
from keras.models import load_model
from PIL import Image
from utils import HAARCASCADE_FRONTALFACE_XML_PATH, EMOTION_CLASSIFIER_V2_PATH, EMOTION_CLASSIFIER_PATH, get_emotion
import jsonpickle


class MyImage(object):
    def __init__(self):
        #self.face_coordinates = []
        self.emotion = 'None'


class Classifier(object):
    def __init__(self):
        self.face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
        self.emotion_classifier = load_model(EMOTION_CLASSIFIER_PATH, compile=False)
        self.emotion_target_size = self.emotion_classifier.input_shape[1:3]

    def __del__(self):
        del self.face_detection_model
        del self.emotion_classifier
        del self.emotion_target_size

    def classify_image(self, image):
        my_image_object_list = []

        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = gray.astype('uint8')

        emotion_text = 'None'

        faces = self.face_detection_model.detectMultiScale(gray, 1.3, 5)
        for (x, y, w, h) in faces:
            current_face = MyImage()

            face = gray[y:y + h, x:x + w]
            cv2.rectangle(image, (x, y), (x + w, y + h), (255, 0, 0), 2)

            #current_face.face_coordinates = ((x, y), (x + w, y + h))

            try:
                face = np.expand_dims(face, axis=0)
                face = Image.fromarray(face.astype('uint8'), 'RGB')
                face = np.array(face)
                face = cv2.resize(face, self.emotion_target_size)
                face = np.reshape(face, [1, 100, 100, 3])

            except Exception as e:
                print(e)
                continue

            prediction = self.emotion_classifier.predict(face)

            print('Prediction: ', prediction)
            emotion_label_arg = np.argmax(prediction)
            emotion_text = get_emotion(emotion_label_arg)

            current_face.emotion = emotion_text
            print(emotion_text)

            my_image_object_list.append(current_face)

        return my_image_object_list

