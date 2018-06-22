from pathlib import Path
import cv2
import numpy as np
from keras.models import load_model
from keras.preprocessing.image import img_to_array, load_img
from PIL import Image
from keras import backend as K


from utils import *

if __name__ == '__main__':
    #IMAGE_FOLDER_PATH = 'e:\\Facultate\\licenta\\frames\\30-4-2018'
    #PATH_OUTPUT = 'e:\\Facultate\\licenta\\detected_faces\\30-4-2018'
    pathlist = Path(IMAGE_FOLDER_PATH).glob('**/*.jpg')
    count = 0

    make_dir(PATH_OUTPUT)

    for path in pathlist:
        # because path is object not string
        path_in_str = str(path)

        face_detection_model = cv2.CascadeClassifier(HAARCASCADE_FRONTALFACE_XML_PATH)
        emotion_classifier = load_model(EMOTION_CLASSIFIER_PATH, compile=False)
        emotion_classifier.compile(loss='categorical_crossentropy',
                                   optimizer='adam',
                                   metrics=['accuracy'])

        emotion_target_size = emotion_classifier.input_shape[1:3]

        img = cv2.imread(path_in_str)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        gray = np.squeeze(gray)
        gray = gray.astype('uint8')

        faces = face_detection_model.detectMultiScale(gray, 1.3, 5)
        for (x,y,w,h) in faces:
            face = gray[y:y+h, x:x+w]
            image_output_path = PATH_OUTPUT + "\\face%d.jpg" % count
            try:
                face = cv2.resize(face, emotion_target_size)
                #cv2.imwrite(image_output_path, face)
                '''pil_im = Image.fromarray(face)
                pil_im.thumbnail(emotion_target_size)
                #pil_im.show()
                x = img_to_array(pil_im)
                x = np.expand_dims(x, axis=0)

                print (x.shape)

                emotion_label_arg = np.argmax(emotion_classifier.predict(x))
                emotion_text = get_emotion(emotion_label_arg)
                '''
            except Exception as e:
                print(e)
                pass

            img = load_img(image_output_path, False)
            y = img_to_array(img)
            y = np.expand_dims(y, axis=0)

            emotion_label_arg = np.argmax(emotion_classifier.predict(y))
            emotion_text = get_emotion(emotion_label_arg)

            print(emotion_classifier.predict(y))

            print('Image:', image_output_path)
            print('Emotion:', emotion_text)
            count += 1
