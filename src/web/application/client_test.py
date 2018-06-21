import requests
import json
import cv2

addr = 'http://127.0.0.1:8080'
test_url = addr + '/predict_image'

# prepare headers for http request
content_type = 'image/jpeg'
headers = {'content-type': content_type}

img = cv2.imread('frame15.jpg')
# encode image as jpeg
_, img_encoded = cv2.imencode('.jpg', img)
# send http request with image and receive response
response = requests.post(test_url, data=img_encoded.tostring(), headers=headers)
# decode response
print (json.loads(response.text))
