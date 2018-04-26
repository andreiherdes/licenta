import os
import numpy as np
import pandas as pd
import shutil

DATASET_PATH = 'e:\\Facultate\\licenta\\dataset\\'
IMAGES_PATH = DATASET_PATH + 'images\\'

OLD_IMAGES_PATH = 'e:\\Facultate\\licenta-db-faces\\facial_expressions\\images\\'
OLD_LEGEND_PATH = 'e:\\Facultate\\licenta-db-faces\\facial_expressions\\data\\'

PATH_SORTED_DIRS = 'E:\\Facultate\\licenta\\dataset\\sorted\\'

def get_label(x):
    return {
        'anger': 1,
        'surprise': 2,
        'disgust': 3,
        'fear': 4,
        'neutral': 5,
        'happiness': 6,
        'sadness': 7,
        'contempt': 8
    }[x]

def get_emotion(x):
    return {
        1: 'anger',
        2: 'surprise',
        3: 'disgust',
        4: 'fear',
        5: 'neutral',
        6: 'happiness',
        7: 'sadness',
        8: 'contempt'
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


def make_dirs():
    if not os.path.exists(PATH_SORTED_DIRS):
        os.makedirs(PATH_SORTED_DIRS)
    for i in range(1,9):
        if not os.path.exists(PATH_SORTED_DIRS + get_emotion(i)):
            os.makedirs(PATH_SORTED_DIRS + get_emotion(i))

make_dirs()

if __name__ == '__main__':
    make_dirs()

    image_list = os.listdir(IMAGES_PATH)
    df = pd.read_csv(DATASET_PATH + 'legend.csv')
    label_from_legend = dict()

    for index, row in df.iterrows():
        # image_to_label[index] = row['emotion']
        #if row['emotion'] in ('ANGER', 'SURPRISE', 'DISGUST', 'FEAR', 'NEUTRAL', 'HAPPINESS', 'SADNESS', 'CONTEMPT'):
            label_from_legend[row['image']] = get_label(row['emotion'])

    for img in image_list:
        if img in label_from_legend:
            shutil.copy2(IMAGES_PATH + img, PATH_SORTED_DIRS + get_emotion(label_from_legend.get(img)))

    print('Finished job.')
'''
image_list = os.listdir(IMAGES_PATH)

label_from_legend= dict()

df = pd.read_csv(DATASET_PATH + 'legend.csv')

print('Read the csv')

#image_to_label = {}
for index, row in df.iterrows():
    #print(row['image'] + ' ' + row['emotion'])
    #image_to_label[index] = row['emotion']
    label_from_legend[row['image']] = get_label(row['emotion'])

for img in image_list:
    if img in label_from_legend:

    else:
        print (img)'''