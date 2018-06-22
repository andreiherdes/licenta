import pandas as pd
import shutil
from utils import *

def make_dirs():
    if not os.path.exists(PATH_SORTED_DIRS):
        os.makedirs(PATH_SORTED_DIRS)
    for i in range(1,9):
        if not os.path.exists(PATH_SORTED_DIRS + get_emotion(i)):
            os.makedirs(PATH_SORTED_DIRS + get_emotion(i))

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