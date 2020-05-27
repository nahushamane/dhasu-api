from flask import Flask, request
import os
#import run as r

app = Flask(__name__)

@app.route('/uploadfile',methods=['PUT'])
def uploadfile():
    f = request.files['file']
    absolute_path = os.path.dirname(os.path.abspath(__file__))
    filePath = absolute_path+"/test.wav"
    f.save(filePath)
    return "success"

@app.route('/getresults',methods=['GET'])
def getresults():
    #results = r.classify()
    #print(results)
    #return results
    os.system('python3 run.py')
    absolute_path = os.path.dirname(os.path.abspath(__file__))
    file1 = open(absolute_path+"/MyFile.txt","r+") 
    string = file1.readline(10)
    #print (string)
    return string

#app.run(port=5100, debug=True)
