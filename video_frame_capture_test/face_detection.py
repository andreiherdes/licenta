import numpy as np
import cv2
import tensorflow as tf
from pathlib import Path

from keras.models import load_model

HAARCASCADE_FRONTALFACE_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_frontalface_default.xml'
HAARCASCADE_EYE_XML_PATH = 'E:\\Facultate\\licenta\\xml_cascades\\haarcascade_eye.xml'
IMAGE_FOLDER_PATH = 'E:\\Facultate\\licenta\\frames\\current-frames\\'
PATH_OUTPUT = 'E:\\Facultate\\licenta\\detected_faces\\11-03-2018\\'

pathlist = Path(IMAGE_FOLDER_PATH).glob('**/*.jpg')
count = 0
for path in pathlist:

    # because path is object not string
    path_in_str = str(path)
    print(path_in_str)

    face_cascade = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
    #eye_cascade = cv2.CascadeClassifier(HAARCASCADE_EYE_XML_PATH)

    img = cv2.imread(path_in_str)
    cv2.imshow('gray', img)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    for (x,y,w,h) in faces:
        cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = img[y:y+h, x:x+w]
        #eyes = eye_cascade.detectMultiScale(roi_gray)
        #for (ex,ey,ew,eh) in eyes:
        #    cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)

    cv2.imshow('img',img)
    cv2.imwrite(PATH_OUTPUT + "\\faceDetected%d.jpg" % count, img)
    count += 1

# load csv content
csv_path = tf.train.string_input_producer(['list1.csv', 'list2.csv'])
textReader = tf.TextLineReader()
_, csv_content = textReader.read(csv_path)
im_name, label = tf.decode_csv(csv_content, record_defaults=[[""], [1]])

# load images
im_content = tf.read_file(IMAGE_FOLDER_PATH+im_name)
image = tf.image.decode_jpeg(im_content, channels=3)
image = tf.cast(image, tf.float32) / 255.
image = tf.image.resize_images(image, 100, 100)

# make batches
images, labels = sess.run([im_batch, lb_batch])