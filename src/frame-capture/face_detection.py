import datetime
import os
from pathlib import Path
import cv2
import numpy as np
from model import create_model
from keras.models import load_model
from keras.preprocessing.image import img_to_array, load_img

from utils import *

if __name__ == '__main__':
    pathlist = Path(IMAGE_FOLDER_PATH).glob('**/*.jpg')
    count = 0

    for path in pathlist:
        # because path is object not string
        path_in_str = str(path)

        face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
        emotion_classifier = load_model(EMOTION_CLASSIFIER_PATH, compile=False)

        emotion_target_size = emotion_classifier.input_shape[1:3]

        img = cv2.imread(path_in_str)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        gray = np.squeeze(gray)
        gray = gray.astype('uint8')

        faces = face_detection_model.detectMultiScale(gray, 1.3, 5)
        for (x,y,w,h) in faces:
            output = gray[y:y+h, x:x+w]
            image_output_path = PATH_OUTPUT + "\\face%d.jpg" % count
            try:
                output = cv2.resize(gray, (emotion_target_size))
                cv2.imwrite(image_output_path, output)
            except:
                print("Error at: ", path_in_str)
                pass
            img = load_img(image_output_path, False)
            x = img_to_array(img)
            x = np.expand_dims(x, axis=0)
            emotion_label_arg = np.argmax(emotion_classifier.predict(x))
            emotion_text = get_emotion(emotion_label_arg)
            print('Image:', image_output_path)
            print('Emotion:', emotion_text)
            #output = preprocess_input(output, False)
            #output = np.expand_dims(output, 0)
            #output = np.expand_dims(output, -1)
            #output = output[None, ...]
            #emotion_label_arg = np.argmax(emotion_classifier.predict(output))
            #emotion_text = get_emotion(emotion_label_arg)

            count += 1

            #print("Image path: ", path)
            #print("Emotion: ", emotion_text)

            # Used for eye detection - not needed
            #roi_color = img[y:y+h, x:x+w]
            #eyes = eye_cascade.detectMultiScale(roi_gray)
            #for (ex,ey,ew,eh) in eyes:
            #   cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)

        #cv2.imshow('img',img)
