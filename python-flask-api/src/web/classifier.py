import cv2
import numpy as np
from keras.models import load_model
from PIL import Image
from utils import HAARCASCADE_FRONTALFACE_XML_PATH, EMOTION_CLASSIFIER_V2_PATH, EMOTION_CLASSIFIER_PATH, get_emotion
import tensorflow as tf
import json



class Classifier(object):
    def __init__(self):
        self.face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
        self.emotion_classifier = load_model(EMOTION_CLASSIFIER_PATH, compile=False)
        self.emotion_target_size = self.emotion_classifier.input_shape[1:3]
        self.graph = tf.get_default_graph()

    def __del__(self):
        del self.face_detection_model
        del self.emotion_classifier
        del self.emotion_target_size

    def classify_image(self, image):
        output = {}

        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = gray.astype('uint8')

        emotion_text = 'None'

        faces = self.face_detection_model.detectMultiScale(gray, 1.3, 5)
        i = 0
        for (x, y, w, h) in faces:
            current_face_properties={}
            face_coordinates = {}

            face_coordinates['x'] = np.float64(x).item()
            face_coordinates['y'] = np.float64(y).item()
            face_coordinates['height'] = np.float64(h).item()
            face_coordinates['width'] = np.float64(w).item()

            current_face_properties['coordinates'] = face_coordinates

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

            with self.graph.as_default():
                prediction = self.emotion_classifier.predict(face)


            emotion_label_arg = np.argmax(prediction)
            emotion_text = get_emotion(emotion_label_arg)

            print('Prediction: ', prediction)
            print('Emotion :', emotion_text)
            i += 1
            current_face_properties['emotion'] = emotion_text
            output['face_' + str(i)] = current_face_properties


        output['number_of_faces'] = i
        return json.dumps(output)

