import cv2
import numpy as np
from keras.models import load_model
from keras.preprocessing.image import img_to_array, load_img

from utils import HAARCASCADE_FRONTALFACE_XML_PATH, FACE_CLASSIFIER_PATH, get_person

DEMO_PICTURE_PATH = 'e:\\Facultate\\licenta\\detected_faces\\IMG_20180513_132833.jpg'
DEMO_PICTURE_OUTPUT_PATH = 'e:\\Facultate\\licenta\\detected_faces\\Face2.jpg'

TRAIN_PICTURE = 'e:\\Facultate\\licenta\\dataset\\people\\post-process\\train\\andrei.herdes\\alt1092.jpg'

def predict_person(image_path):
    face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
    face_classifier = load_model(FACE_CLASSIFIER_PATH, compile=False)
    face_classifier.compile(loss='binary_crossentropy',
                            optimizer='rmsprop',
                            metrics=['accuracy'])

    face_target_size = face_classifier.input_shape[1:3]

    img = cv2.imread(image_path)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    gray = np.squeeze(gray)
    gray = gray.astype('uint8')

    faces = face_detection_model.detectMultiScale(gray, 1.3, 5)
    for (x, y, w, h) in faces:
        face = gray[y:y + h, x:x + w]
        try:
            face = cv2.resize(face, face_target_size)
            cv2.imwrite(DEMO_PICTURE_OUTPUT_PATH, face)

            img = load_img(DEMO_PICTURE_OUTPUT_PATH, False)
            x = img_to_array(img)
            x = np.expand_dims(x, axis=0)

            prediction = face_classifier.predict(x)
            print(prediction)
            face_label_arg = np.argmax(prediction)
            print(get_person(face_label_arg))
        except Exception as e:
            print(e)
            pass

predict_person(TRAIN_PICTURE)


