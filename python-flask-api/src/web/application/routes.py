from application import app
from flask import render_template, Response, request
from camera import VideoCamera
from classifier import Classifier
import jsonpickle
import numpy as np
import cv2
import io
import keras.backend as K

myClassifier = Classifier()

@app.route('/index')
def index():
    return render_template('index.html')


def generate_stream(camera):
    while True:
        frame = camera.get_frame
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')


@app.route('/video_feed')
def video_feed():
    return Response(generate_stream(VideoCamera()),
                    mimetype='multipart/x-mixed-replace; boundary=frame')


# route http posts to this method
@app.route('/predict_image', methods=['POST'])
def predictImage():
    global myClassifier
    r = request

    photo = r.files['photo']
    in_memory_file = io.BytesIO()
    photo.save(in_memory_file)

    # do some fancy processing here....
    nparray = np.fromstring(in_memory_file.getvalue(), dtype=np.uint8)
    img = cv2.imdecode(nparray, cv2.IMREAD_COLOR)

    # encode response using jsonpickle
    response = myClassifier.classify_image(image=img)

    return Response(response=response, status=200, mimetype="application/json")