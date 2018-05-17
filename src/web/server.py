#
# Date: 16/05/2018
# Description:
# Modified to support face detection in the video stream using opencv and detect
# emotions using a model trained with keras.
# Video/Webcam stream credits go to Miguel Grinberg and ckieric. Thanks!
# Credits: http://blog.miguelgrinberg.com/post/video-streaming-with-flask
#          https://github.com/log0/video_streaming_with_flask_example
from application import app

if __name__ == '__main__':
    app.run('localhost', 8080, debug=True)