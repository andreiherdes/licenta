import requests
import json
import cv2

addr = 'http://localhost:8080'
test_url = addr + '/emoApi/1ef3c747-4db7-42c6-a526-3f073d4194d9'

# prepare headers for http request
content_type = 'image/jpeg'
headers = {'content-type': content_type}

img = cv2.imread('frame10.jpg')
# encode image as jpeg
_, img_encoded = cv2.imencode('.jpg', img)
# send http request with image and receive response
response = requests.post(test_url, files={'file' : img_encoded.tostring()})
# decode response
print (json.loads(response.text))
