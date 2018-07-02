from application import app
from flask import render_template, Response
from camera import VideoCamera

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