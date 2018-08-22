'''
    This module is based on Inception-V3


'''
import os
import shutil
#import tensorflow as tf
import PIL
from PIL import Image

#Declare dictionary
#This is a dictionary
'''
 Options:
 Score.txt
 BalancingElements.txt
 ColorHarmony.txt
 Content.txt
 DoF.txt
 Light.txt
 MotionBlur.txt
 Object.txt
 Repetition.txt
 RuleOfThirds.txt
 Symmetry.txt
 VividColor.txt
 
'''
# This maps Input file name to Output file name
fileDict = {
"BalancingElements.txt":"BalancingElements",
"ColorHarmony.txt":"ColorHarmony",
"Content.txt":"Content",
"DoF.txt":"DoF",
"Light.txt":"Light",
"MotionBlur.txt":"MotionBlur",
"Object.txt":"Object",
"Repetition.txt":"Repetition",
"RuleOfThirds.txt":"RuleOfThirds",
"Symmetry.txt":"Symmetry",
"VividColor.txt":"VividColor",
"Score.txt":"Score"
}

VeryLowNum, LowNum, MediumNum, HighNum, VeryHighNum, ErrorNum = 0,0,0,0,0,0

def classType(score):
    #score >= -1.0 and score < -0.6
    if ():
        global VeryLowNum
        VeryLowNum += 1
        return 'Very_Low';
    #score >= -0.6 and score < -0.2
    #score >= -1.0 and score < 0.00
    if (score < -0.35):
        global LowNum
        LowNum += 1
        return "Low";
    #score >= -0.2 and score < 0.2
    if (score == 0.00):
        global MediumNum
        MediumNum += 1
        return "Medium"
        '''
        if (MediumNum < 876):
            #return "Medium"
            return "Medium"
        else:
            return "Error"
        '''

    #score >= 0.2 and score < 0.6
    if (score > 0.35 and score <= 1.0):
        global HighNum
        HighNum += 1
        return "High";
    #score >= 0.6 and score < 1.0
    if ():
        global VeryHighNum
        VeryHighNum += 1
        return "Very_High";
    global ErrorNum
    ErrorNum += 1
    return "Error";
def printAll():
    print(VeryLowNum)
    print(LowNum)
    print(MediumNum)
    print(HighNum)
    print(VeryHighNum)
    print(ErrorNum)
# Read in the labels
# Write a new file
# 5 classes :very low, low, medium, high, very high

# For every file in folder, write to other folder
# Problem: class imbalance

for fileIter in os.listdir('/Users/stevenzhao/Desktop/FinalProject_Python/InputFilenames'):
    #these contain the input and output names
    fileIterCorrelate = fileIter.rstrip()
    print(fileIterCorrelate)
    filenameCorrelate = fileDict[fileIterCorrelate]
    #print(filenameCorrelate)

    outputFilename = fileIterCorrelate
    print(outputFilename)

    #output = open('output.txt', 'r+')
    output = open('/Users/stevenzhao/Desktop/FinalProject_Python/OutputFilenames/'+outputFilename, 'r+')
    # with open('imgListTrainRegression_Light.txt') as file:
    # for line in file
    with open('/Users/stevenzhao/Desktop/FinalProject_Python/InputFilenames/'+fileIterCorrelate) as file:
        for line in file:
            #Write to other file
            myLineList = line.split(" ")
            output.write(myLineList[0] + " ")

            classResult = classType(score=float(myLineList[1]))

            output.write(classResult)
            output.write("\n")

printAll()
#output.close() open('output.txt')

# Resize

# iterate over all lists
# compartmentalize into the low high folders for each one
# autotrain?
# please



# parse specific
 #output = open('output.txt', 'r+')
output = open('/Users/stevenzhao/Desktop/FinalProject_Python/OutputFilenames/RuleOfThirds.txt', 'r+')
    # with open('imgListTrainRegression_Light.txt') as file:
    # for line in file
with open('/Users/stevenzhao/Desktop/FinalProject_Python/InputFilenames/RuleOfThirds.txt') as file:
    for line in file:
        #Write to other file
        myLineList = line.split(" ")
        output.write(myLineList[0] + " ")

        classResult = classType(score=float(myLineList[1]))

        output.write(classResult)
        output.write("\n")

printAll()

#generate specific
outputIterFile = open('/Users/stevenzhao/Desktop/FinalProject_Python/OutputFilenames/RuleOfThirds.txt', 'r+')
for processedLine in outputIterFile:
    # move file
    myListTwo = processedLine.split(" ")
    filenameTwo = myListTwo[0]
    fileClassTwo = myListTwo[1].replace("\n", "")
    # fileClassTwo = fileClassTwo.replace("\n", "")

    print("Attempting " + filenameTwo + " " + fileClassTwo)
    #addressTwoName = fileDict[outputIter]
    addressOne = "/Users/stevenzhao/Desktop/FinalProject_Python/datasetImages_warp256/" + filenameTwo
    addressTwo = "/Users/stevenzhao/Desktop/FinalProject_Python/FileClasses/RuleOfThirds/" + fileClassTwo + "/" + filenameTwo
    shutil.copyfile(addressOne, addressTwo)
    im1 = Image.open(addressTwo)
    # im1.resize(226)
    # os.rename(addressOne, addressTwo)
    print("renamed " + addressTwo)

print("Done.")

for fileIterTwo in os.listdir('/Users/stevenzhao/Desktop/FinalProject_Python/OutputFilenames'):
    outputIter = fileIterTwo.rstrip()
    outputIterFile = open('/Users/stevenzhao/Desktop/FinalProject_Python/OutputFilenames/'+outputIter, 'r+')

    # with output as processedFile:
    for processedLine in outputIterFile:
        # move file
        myListTwo = processedLine.split(" ")
        filenameTwo = myListTwo[0]
        fileClassTwo = myListTwo[1].replace("\n", "")
        # fileClassTwo = fileClassTwo.replace("\n", "")

        print("Attempting " + filenameTwo + " " + fileClassTwo)
        addressTwoName = fileDict[outputIter]
        addressOne = "/Users/stevenzhao/Desktop/FinalProject_Python/datasetImages_warp256/" + filenameTwo
        addressTwo = "/Users/stevenzhao/Desktop/FinalProject_Python/FileClasses/"+addressTwoName+"/" + fileClassTwo + "/" + filenameTwo
        shutil.copyfile(addressOne, addressTwo)
        im1 = Image.open(addressTwo)
        # im1.resize(226)
        # os.rename(addressOne, addressTwo)
        print("renamed " + addressTwo)

    print("Done.")



