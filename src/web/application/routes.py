from application import app
from flask import render_template, Response
from camera import VideoCamera

emotion = 'None'

@app.route('/index')
def index():
    return render_template('index.html')

def gen(camera):
    global emotion
    while True:
        frame, emotion_text = camera.get_frame
        emotion = emotion_text
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')

@app.route('/emotion_text')
def get_emotion():
    return Response(emotion, mimetype='text/xml')

@app.route('/video_feed')
def video_feed():
    return Response(gen(VideoCamera()),
                    mimetype='multipart/x-mixed-replace; boundary=frame')