import cv2
import time
import random
import string
from utils import *

#SUBFOLDER = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(8))

# Read until video is completed
def video_capture_webcam(cap):
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

def video_capture_file(cap):
    while not cap.isOpened():
        cap = cv2.VideoCapture("out.mp4")
        cv2.waitKey(1000)
        print ('Wait for the header')

    pos_frame = cap.get(cv2.cv.CV_CAP_PROP_POS_FRAMES)
    while True:
        flag, frame = cap.read()
        if flag:
            # The frame is ready and already captured
            cv2.imshow('video', frame)
            pos_frame = cap.get(cv2.cv.CV_CAP_PROP_POS_FRAMES)
            print (str(pos_frame)+" frames")
        else:
            # The next frame is not ready, so we try to read it again
            cap.set(cv2.cv.CV_CAP_PROP_POS_FRAMES, pos_frame-1)
            print ("frame is not ready")
            # It is better to wait for a while for the next frame to be ready
            cv2.waitKey(1000)

        if cv2.waitKey(10) == 27:
            break
        if cap.get(cv2.cv.CV_CAP_PROP_POS_FRAMES) == cap.get(cv2.cv.CV_CAP_PROP_FRAME_COUNT):
            # If the number of captured frames is equal to the total number of frames,
            # we stop
            break

if __name__ == '__main__':

    make_dir(PATH_OUTPUT_FRAME_CAPTURE)
    # Create a VideoCapture object and read from input file
    # If the input is the camera, pass 0 instead of the video file name
    cap_webcam = cv2.VideoCapture(0)
    cap_video = cv2.VideoCapture("out.mp4")
    # Check if camera opened successfully
    if (cap_webcam.isOpened() == False):
        print("Error opening video stream or file")

    #video_capture_file(cap_video)
    video_capture_webcam(cap_webcam)
    # When everything done, release the video capture object
    cap_webcam.release()

    # Closes all the frames
    cv2.destroyAllWindows()