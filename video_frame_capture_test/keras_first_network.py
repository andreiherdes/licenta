from keras.models import Sequential
from keras.layers import Dense
from keras.preprocessing.image import ImageDataGenerator, img_to_array, load_img
import os
import numpy
# fix random seed for reproducibility

numpy.random.seed(7)

def data_augmentation(imageDir, previewDir, augsize=10):
    datagen = ImageDataGenerator(
            featurewise_std_normalization=False,
            rotation_range = 20,
            width_shift_range = 0.10,
            height_shift_range = 0.10,
            shear_range = 0.1,
            zoom_range = 0.1,
            horizontal_flip = True)

    imageList = os.listdir(imageDir)
    if not os.path.exists(previewDir):
        os.makedirs(previewDir)

    for i in range(0, len(imageList)):
        img = load_img(os.path.join(imageDir, imageList[i]))
        x = img_to_array(img)
        x = x.reshape((1,) + x.shape)
        j = 0
        for batch in datagen.flow(x, batch_size=1, save_to_dir=imageDir,
                                  save_prefix=imageList[i].split('.')[0]):
            j += 1
            if j >= augsize:
                break