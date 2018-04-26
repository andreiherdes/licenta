import numpy as np
import cv2
import tensorflow as tf
from pathlib import Path
import os
import datetime

from keras.models import load_model

now = datetime.datetime.now()

HAARCASCADE_FRONTALFACE_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_frontalface_default.xml'
HAARCASCADE_FRONTALFACE_ALT_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_frontalface_alt.xml'
HAARCASCADE_FRONTALFACE_ALT2_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_frontalface_alt2.xml'
HAARCASCADE_FRONTALFACE_ALT_TREE_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_frontalface_alt_tree.xml'

HAARCASCADE_EYE_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_eye.xml'

IMAGE_FOLDER_PATH = 'E:\\Facultate\\licenta\\frames\\current-frames\\'
PATH_OUTPUT = 'E:\\Facultate\\licenta\\detected_faces\\' + str(now.day) + '-' + str(now.month) + '-' + str(now.year)

pathlist = Path(IMAGE_FOLDER_PATH).glob('**/*.jpg')
count = 0

if not os.path.exists(PATH_OUTPUT):
    os.makedirs(PATH_OUTPUT)

for path in pathlist:

    # because path is object not string
    path_in_str = str(path)
    print(path_in_str)

    face_cascade = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)

    #eye_cascade = cv2.CascadeClassifier(HAARCASCADE_EYE_XML_PATH)

    img = cv2.imread(path_in_str)

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    for (x,y,w,h) in faces:
        gray = gray[y:y+h, x:x+w]

        try:
            output = cv2.resize(gray, (350,350))
            cv2.imwrite(PATH_OUTPUT + "\\face%d.jpg" % count, output)
        except:
            print("Error at: ", path_in_str)
            pass

        count += 1

        # Used for eye detection - not needed
        #roi_color = img[y:y+h, x:x+w]
        #eyes = eye_cascade.detectMultiScale(roi_gray)
        #for (ex,ey,ew,eh) in eyes:
        #   cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)

    #cv2.imshow('img',img)
