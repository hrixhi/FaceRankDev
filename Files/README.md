# CSCI201 Group Project: FaceRank (Image Aesthetics)
Our project is a web app that uses machine learning to determine the aesthetic quality of photos. It also gives you tips on how to improve your picture taking!

Run login.jsp to start using our app.

#Basic Information
For complete information and setup instructions, please see our Project document.

Upon starting the program, the login.jsp is executed. The login form then communicates with the SQL server hosting the database (with authenticate.jsp).

Once authentication is successful, the user is redirected to the homepage.jsp file. This is where most of the client sided functionality is. Here, you can upload images, and have them processed. The redirect from homepage to homepage1 allows you to view other peoples' profiles.

The enormous "Learning Models" file contains our tensorflow model. 

ImageProcessing.py aids in pre-processing training data. Actual training was done through the terminal - over several computers.

# Known issues
We currently have bugs with the CSS, and some data processing elements.

We are fixing these bugs before the presentation! Apologies if anything breaks.