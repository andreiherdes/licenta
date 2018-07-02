from application import app
from flask import Response, request
from classifier import Classifier
import numpy as np
import cv2
import io

myClassifier = Classifier()

# route http posts to this method
@app.route('/predict_image', methods=['POST'])
def predictImage():
    global myClassifier
    r = request

    photo = r.files['photo']
    in_memory_file = io.BytesIO()
    photo.save(in_memory_file)

    # image processing
    nparray = np.fromstring(in_memory_file.getvalue(), dtype=np.uint8)
    img = cv2.imdecode(nparray, cv2.IMREAD_COLOR)

    # classify image
    response = myClassifier.classify_image(image=img)

    return Response(response=response, status=200, mimetype="application/json")

@app.route('/predict_image', methods=['GET'])
def predictImage():
    response = "{response: this uri accepts multipart image requests}"
    return Response(response=response, status=200, mimetype="application/json")