from pathlib import Path
import cv2
import numpy as np
import random

from utils import *

ANDREI_IMAGE_FOLDER = 'e:\\Facultate\\licenta\\dataset\\people\\raw\\andrei.herdes\\'
ANDREI_PATH_OUTPUT = 'e:\\Facultate\\licenta\\dataset\\people\\post-process\\andrei.herdes\\'

def save_face(faces, image_name, count):
    for (x, y, w, h) in faces:
        output = gray[y:y + h, x:x + w]
        image_output_path = ANDREI_PATH_OUTPUT + "\\" + image_name + "%d.jpg" % random.randint(0,2000)
        try:
            print(image_output_path)
            output = cv2.resize(output, (100, 100))
            cv2.imwrite(image_output_path, output)
        except:
            print("Error at: ", path_in_str)
            pass
        break

if __name__ == '__main__':
    pathlist = Path(ANDREI_IMAGE_FOLDER).glob('**/*.jpg')

    make_dir(ANDREI_PATH_OUTPUT)

    face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
    face_dection_model_alt1 = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_ALT_XML_PATH)
    face_dection_model_alt2 = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_ALT2_XML_PATH)
    face_dection_model_alt_tree = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_ALT_TREE_XML_PATH)

    for path in pathlist:
        # because path is object not string
        path_in_str = str(path)

        count = 1

        img = cv2.imread(path_in_str)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        gray = np.squeeze(gray)
        gray = gray.astype('uint8')

        print(path_in_str)

        faces = face_detection_model.detectMultiScale(gray, 1.3, 5)
        faces_alt = face_dection_model_alt1.detectMultiScale(gray, 1.3, 5)
        #faces_alt2 = face_dection_model_alt2.detectMultiScale(gray, 1.3, 5)
        #faces_tree = face_dection_model_alt_tree.detectMultiScale(gray, 1.3, 5)

        save_face(faces, 'default', count)
        save_face(faces_alt, 'alt', count)
        #save_face(faces_alt2, 'alt2', count)
        #save_face(faces_tree, 'tree', count)
        count = count + 1