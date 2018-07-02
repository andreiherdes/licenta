"""
The flask application package.
"""

from flask import Flask

app = Flask(__name__)

from application import routes