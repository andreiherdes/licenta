import datetime
import os

now = datetime.datetime.now()

my_path = os.path.dirname(__file__)

IMAGE_FOLDER_PATH = os.path.join(my_path, '..\\frames\\' + str(now.day) + '-' + str(now.month) + '-' + str(now.year))
PATH_OUTPUT = os.path.join(my_path, '..\\detected_faces\\' + str(now.day) + '-' + str(now.month) + '-' + str(now.year))

# google cloud stuff
# HAARCASCADE_FRONTALFACE_XML_PATH, EMOTION_CLASSIFIER_PATH
HAARCASCADE_FRONTALFACE_XML_PATH = os.path.join(my_path, "..\\xml_cascades\\haarcascade_frontalface_default.xml")
EMOTION_CLASSIFIER_PATH = os.path.join(my_path, "..\\trained-models\\v4\\v4_fewer_classes.h5")

#keras_floyd_nn.py constants
PATH_SORTED_DIRS_FLOYD = '/dataset/'

#keras_first_network.py constants
PATH_SORTED_DIRS_LOCAL = os.path.join(my_path, '..\\dataset\\sorted-small\\')

#frame_capture.py constants
PATH_OUTPUT_FRAME_CAPTURE = os.path.join(my_path, '..\\frames\\' + str(now.day) + '-' + str(now.month) + '-' + str(now.year))

#sort_dataset.py constants
DATASET_PATH = os.path.join(my_path, '..\\dataset\\')
IMAGES_PATH = DATASET_PATH + 'images\\'

OLD_IMAGES_PATH = 'e:\\Facultate\\licenta-db-faces\\facial_expressions\\images\\'
OLD_LEGEND_PATH = 'e:\\Facultate\\licenta-db-faces\\facial_expressions\\data\\'

PATH_SORTED_DIRS = 'E:\\Facultate\\licenta\\dataset\\sorted\\'

#switch-cases
def get_label(x):
    return {
        'anger': 0,
        'happiness': 1,
        'neutral': 2,
        'sadness': 3,
        'surprise': 4
    }[x]

def get_emotion(x):
    return {
        0: 'anger',
        1: 'happiness',
        2: 'neutral',
        3: 'sadness',
        4: 'surprise'
    }[x]

def old_get_label(x):
    return {
        'ANGER': 1,
        'SURPRISE': 2,
        'DISGUST': 3,
        'FEAR': 4,
        'NEUTRAL': 5,
        'HAPPINESS': 6,
        'SADNESS': 7,
        'CONTEMPT': 8
    }[x]

def old_get_emotion(x):
    return {
        1: 'ANGER',
        2: 'SURPRISE',
        3: 'DISGUST',
        4: 'FEAR',
        5: 'NEUTRAL',
        6: 'HAPPINESS',
        7: 'SADNESS',
        8: 'CONTEMPT'
    }[x]

def get_person(x):
    return {
        1: 'Andrei Herdes',
        2: 'Stranger'
    }[x]


#functions
def make_dir(PATH_OUTPUT):
    if not os.path.exists(PATH_OUTPUT):
        os.makedirs(PATH_OUTPUT)


def preprocess_input(x, v2=True):
    x = x.astype('float32')
    x = x / 255.0
    if v2:
        x = x - 0.5
        x = x * 2.0
    return x