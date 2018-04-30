import cv2
import numpy as np
import time
import datetime
import os
import random
import string
from utils import *

#SUBFOLDER = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(8))

if not os.path.exists(PATH_OUTPUT_FRAME_CAPTURE):
    os.makedirs(PATH_OUTPUT_FRAME_CAPTURE)

# Read until video is completed
def video_capture(cap):
    count = 0
    while (cap.isOpened()):
        time.sleep(1)
        # Capture frame-by-frame
        ret, frame = cap.read()
        if ret == True:
            print('Read a new frame: ', ret)
            print('Frame number: ', count)
            # Display the resulting frame
            cv2.imshow('Frame', frame)
            cv2.imwrite(PATH_OUTPUT_FRAME_CAPTURE + "\\frame%d.jpg" % count, frame)
            count += 1

            #print(count)
            # Press Q on keyboard to  exit
            if cv2.waitKey(25) & 0xFF == ord('q'):
                break

        # Break the loop
        else:
            break

if __name__ == '__main__':
    # Create a VideoCapture object and read from input file
    # If the input is the camera, pass 0 instead of the video file name
    cap = cv2.VideoCapture(0)
    # Check if camera opened successfully
    if (cap.isOpened() == False):
        print("Error opening video stream or file")

    video_capture(cap)

    # When everything done, release the video capture object
    cap.release()

    # Closes all the frames
    cv2.destroyAllWindows()