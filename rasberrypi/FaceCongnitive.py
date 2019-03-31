## -*- coding: utf-8 -*-  # 한글 주석쓸려면 적기
import cv2
import time
import json
import sys
import threading
import Command

font = cv2.FONT_ITALIC

def Rotate(src, degrees):
    if degrees == 90:
         dst = cv2.transpose(src)
         dst = cv2.flip(dst, 1)
    elif degrees == 180:
         dst = cv2.flip(src, -1)
    elif degrees == 270:
         dst = cv2.transpose(src)
         dst = cv2.flip(dst, 0)
    else:
         dst = null
         
    return dst ################################### 

def faceDetect():
    
    eye_detect = True
    face_cascade = cv2.CascadeClassifier("./haarcascade_frontalface_default.xml")  # 얼굴찾기 haar 파일
    #eye_cascade = cv2.CascadeClassifier("./haarcascade_eye.xml") # 눈찾기 haar 파일
    
    try:
        cam = cv2.VideoCapture(-1)
        

    except:
        print("camera loading error")
        return
 
    while True:
       
        ret, frame = cam.read()
        if not ret:
            break
        Command.InitFrame(frame)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray,1.3, 5)
        faces = face_cascade.detectMultiScale(gray,1.3, 5)
        #카메라 영상 왼쪽위에 위에 셋팅된 info 의 내용 출력
        #cv2.putText(frame, emotion_judge, (5,15), font, 0.5, (255,0, 255),1)
 
        for(x,y, w,h) in faces:
            cv2.rectangle(frame, (x,y), (x+w, y+h), (255,0,0), 2)  #사각형 범위
            cv2.putText(frame, "Detected Face", (x-5, y-5), font, 0.5, (255,255,0),2)  #얼굴찾았다는 메시지
            print((x,y), (x+w, y+h))
        
        
        cv2.imshow("frame",frame)
        
        k=cv2.waitKey(30)
        #print("test")
        #실행 중 키보드 i 를 누르면 눈찾기를 on, off한다.
        if k ==ord("i") :
            cv2.imshow("frame", frame)
        if k == 27:
            break
    cam.release()
    cv2.destroyAllWindows()
 
 
 
face_thread = threading.Thread(target=faceDetect())
face_thread.daemon = True
face_thread.start()


    

