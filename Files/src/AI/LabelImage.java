package AI;
/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.types.UInt8;

/** Sample use of the TensorFlow Java API to label images using a pre-trained model. */
public class LabelImage {
	 byte[] scoreGraphDef;
	    byte[] lightGraphDef;
	    byte[] balancingElementsGraphDef;
	    byte[] dofGraphDef;
	    byte[] colorHarmonyGraphDef;
	    byte[] contentGraphDef;
	    byte[] motionBlurGraphDef;
	    byte[] ruleOfThirdsGraphDef;
	    byte[] objectGraphDef;
	    byte[] repetitionGraphDef;
	    byte[] vividColorGraphDef;
	    byte[] symmetryGraphDef;
	    List<String> labels;
	    
	    ArrayList<String> adviceArray;
	    ArrayList<String> stringArray;

  public LabelImage() {
	  	this.stringArray = new ArrayList<String>();

	  	this.adviceArray = new ArrayList<String>();
	  	//File scoreFile = new File(System.getProperty("user.dir") + "/Learning_Models");
	  	// /Users/stevenzhao/Desktop/coding/201/FinalProject_Tensorflow/Learning_Models
		File scoreFile = new File("/Users/HrishiHD/eclipse-workspace/RateMySelfie/Learning_Models");
		// Path string
		String scorePath = scoreFile.getAbsolutePath();
		System.out.println("Opening: " + scoreFile.getAbsolutePath());
		
	    this.scoreGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Score/retrained_graph.pb"));
	    this.lightGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Light/retrained_graph.pb"));
	    this.balancingElementsGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/BalancingElements/retrained_graph.pb"));
	    this.dofGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/DoF/retrained_graph.pb"));
	    this.colorHarmonyGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/ColorHarmony/retrained_graph.pb"));
	    this.contentGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Content/retrained_graph.pb"));
	    this.motionBlurGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/MotionBlur/retrained_graph.pb"));
	    this.ruleOfThirdsGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/RuleOfThirds/retrained_graph.pb"));
	    this.objectGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Object/retrained_graph.pb"));
	    this.repetitionGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Repetition/retrained_graph.pb"));
	    this.vividColorGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/VividColor/retrained_graph.pb"));
	    this.symmetryGraphDef = readAllBytesOrExit(Paths.get(scorePath, "/Symmetry/retrained_graph.pb"));
	    this.labels =readAllLinesOrExit(Paths.get(scorePath, "/Score/retrained_labels.txt"));
  }
  public String readInImage(InputStream inputFile) {
	  byte[] imageBytes;
	  String outputString = "";
	  String scoreString = "";
	  // ArrayList<String> stringArray = new ArrayList<String>();
	  stringArray.clear();
	  // We need something to output the messages!
		try {
			imageBytes = IOUtils.toByteArray(inputFile);
			
			try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			      float[] labelProbabilities = executeInceptionGraph(scoreGraphDef, image);
			      int bestLabelIdx = maxIndex(labelProbabilities);
			      // Add this to the ArrayList
			      scoreString = labels.get(bestLabelIdx);
			      stringArray.add(labels.get(bestLabelIdx));
			      // If this is particularly bad, add this to the adviceArray
			      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
			    	  	
			    	  	// 
			    	  	adviceArray.add("Score");
			    	  	
			      }
			      // END
			      System.out.println(
			          String.format("Score: BEST MATCH: %s (%.2f%% likely)",
			              labels.get(bestLabelIdx),
			              labelProbabilities[bestLabelIdx] * 100f));
			    }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(lightGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("Light");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Light: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(balancingElementsGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("BalancingElements");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("BalancingElements: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(dofGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("DoF");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("DoF: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(colorHarmonyGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("ColorHarmony");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("ColorHarmony: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(contentGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("Content");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Content: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(motionBlurGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("MotionBlur");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("MotionBlur: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(ruleOfThirdsGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("RuleOfThirds");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Rule of Thirds: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(objectGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("Object");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Object: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(repetitionGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("Repetition");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Repetition: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(vividColorGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("VividColor");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Vivid Color: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
			        float[] labelProbabilities = executeInceptionGraph(symmetryGraphDef, image);
			        int bestLabelIdx = maxIndex(labelProbabilities);
				      // Add this to the ArrayList
				      stringArray.add(labels.get(bestLabelIdx));
				      // If this is particularly bad, add this to the adviceArray
				      if ((labelProbabilities[bestLabelIdx] * 100f) > 95.0 && labels.get(bestLabelIdx).equals("low")) {
				    	  	// 
				    	  	adviceArray.add("Symmetry");
				    	  	
				      }
				      // END
			        System.out.println(
			            String.format("Symmetry: BEST MATCH: %s (%.2f%% likely)",
			                labels.get(bestLabelIdx),
			                labelProbabilities[bestLabelIdx] * 100f));
			      }
			// BufferedImage image = ImageIO.read(inputFile);
		} catch (IllegalArgumentException iae ) {
			System.out.println("Illegal Argument Exception in ImageLearningModel.readInImage(): " + iae.getMessage());
		} catch (IOException ioe) {
			System.out.println("I/O Exception in ImageLearningModel.readInImage(): " + ioe.getMessage());
		}
	  // byte[] imageBytes = readAllBytesOrExit(Paths.get("/Users/stevenzhao/Desktop/coding/201/FinalProject_Tensorflow/aestheticMountain.jpg"));
		System.out.println(stringArray.toString());
		System.out.println(adviceArray.toString());
		
		
		/* 
		 * Calculate score
		 */
		String finalString = calculateFinalString();
		boolean firstThing = true;
		for (String finalStr : stringArray) {
			if (firstThing == true) {
				firstThing = false;
				outputString += finalString;

			} else {
				outputString += "|";
				if (finalStr.equals("low")) {
					outputString += "0";
				}
				else if (finalStr.equals("high")) {
					outputString += "1";

				}
			}
		}
		System.out.println(outputString);
		String finalAdvice = calculateAdviceString();
		ImageAdvice advisor = new ImageAdvice();
		String adviceOutput = "";
		adviceOutput = advisor.processModelString(finalAdvice);
		System.out.println(adviceOutput);
		
		
		// 
		// Format 
		outputString = finalString + "|" + adviceOutput;
		System.out.println("outputString: " + outputString);
	    return outputString;
  }
  /*
   * calculateAdviceString
   */
  private String calculateAdviceString() {
	  String build = "";
	  boolean isFirst = true;

	  for (String adviceString : adviceArray) {
		  if (isFirst == true) {
			  isFirst = false;
			  build += adviceString;
		  } else {
			  build += "|";
			  build += adviceString;
		  }
		  
	  }
	  return build;
  }
  /*
   * calculateFinalString
   */
  public String calculateFinalString() {
	  Double score = 0.0;
	  // Iterate over stringArray
	  boolean isFirst = true;
	  for (String scoreString : stringArray) {
		  
		  if (scoreString.equals("high")) {
			  if (isFirst == true) {
				  isFirst = false;
				  score += 2;
			  } else {
				  score += 1;
			  }
		  }
	  }
	  score = score / 13;
	  score = score * 10;
	  Double rounded = Math.floor(score*10)/10;
	  
	  
	  // System.out.println(score);
	  // System.out.println(rounded);

	  
	  return rounded.toString();
	  
  }
  // Uses adviceArray[]
  public String getAdvice() {
	  String advice = "";
	  // We now use adviceArray
	  if (adviceArray.isEmpty()) {
		  // We love your photo! Call this
		  
	  } else {
		  for (String attribute:adviceArray) {
			  // call relevant function
			  // concatenate to advice
		  }
	  }
	  // call string
	  return advice;
  }
	
	
  private static void printUsage(PrintStream s) {
    final String url =
        "https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip";
    s.println(
        "Java program that uses a pre-trained Inception model (http://arxiv.org/abs/1512.00567)");
    s.println("to label JPEG images.");
    s.println("TensorFlow version: " + TensorFlow.version());
    s.println();
    s.println("Usage: label_image <model dir> <image file>");
    s.println();
    s.println("Where:");
    s.println("<model dir> is a directory containing the unzipped contents of the inception model");
    s.println("            (from " + url + ")");
    s.println("<image file> is the path to a JPEG image file");
  }

  public static void main(String[] args) {
	  LabelImage testingLabel = new LabelImage();


		try {
			File imageFile = new File("/Users/HrishiHD/eclipse-workspace/RateMySelfie/x.jpg");
			InputStream targetTest = new FileInputStream(imageFile);
			testingLabel.readInImage(targetTest);
		} catch (Exception e) {
			System.out.println("Error! LabelImage has failed.");
		}
		
		
	  // String modelDir = "Learning_Models/inception-2015-12-05.tgz";
	  // String imageFile = "../awfulDoctors.png";


    //    byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));

    
  }

  private static Tensor<Float> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes) {
    try (Graph g = new Graph()) {
      GraphBuilder b = new GraphBuilder(g);
      // Some constants specific to the pre-trained model at:
      // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
      //
      // - The model was trained with images scaled to 224x224 pixels.
      // - The colors, represented as R, G, B in 1-byte each were converted to
      //   float using (value - Mean)/Scale.
      final int H = 299;
      final int W = 299;
      final float mean = 117f;
      final float scale = 1f;

      // Since the graph is being constructed once per execution here, we can use a constant for the
      // input image. If the graph were to be re-used for multiple input images, a placeholder would
      // have been more appropriate.
      final Output<String> input = b.constant("input", imageBytes);
      final Output<Float> output =
          b.div(
              b.sub(
                  b.resizeBilinear(
                      b.expandDims(
                          b.cast(b.decodeJpeg(input, 3), Float.class),
                          b.constant("make_batch", 0)),
                      b.constant("size", new int[] {H, W})),
                  b.constant("mean", mean)),
              b.constant("scale", scale));
      try (Session s = new Session(g)) {
        return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
      }
    }
  }

  private static float[] executeInceptionGraph(byte[] graphDef, Tensor<Float> image) {
    try (Graph g = new Graph()) {
      g.importGraphDef(graphDef);
      try (Session s = new Session(g);
          Tensor<Float> result =
              s.runner().feed("Mul", image).fetch("final_result").run().get(0).expect(Float.class)) {
        final long[] rshape = result.shape();
        if (result.numDimensions() != 2 || rshape[0] != 1) {
          throw new RuntimeException(
              String.format(
                  "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                  Arrays.toString(rshape)));
        }
        int nlabels = (int) rshape[1];
        return result.copyTo(new float[1][nlabels])[0];
      }
    }
  }

  private static int maxIndex(float[] probabilities) {
    int best = 0;
    for (int i = 1; i < probabilities.length; ++i) {
      if (probabilities[i] > probabilities[best]) {
        best = i;
      }
    }
    return best;
  }

  private static byte[] readAllBytesOrExit(Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
      System.exit(1);
    }
    return null;
  }

  private static List<String> readAllLinesOrExit(Path path) {
    try {
      return Files.readAllLines(path, Charset.forName("UTF-8"));
    } catch (IOException e) {
      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  // In the fullness of time, equivalents of the methods of this class should be auto-generated from
  // the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages
  // like Python, C++ and Go.
  static class GraphBuilder {
    GraphBuilder(Graph g) {
      this.g = g;
    }

    Output<Float> div(Output<Float> x, Output<Float> y) {
      return binaryOp("Div", x, y);
    }

    <T> Output<T> sub(Output<T> x, Output<T> y) {
      return binaryOp("Sub", x, y);
    }

    <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
      return binaryOp3("ResizeBilinear", images, size);
    }

    <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
      return binaryOp3("ExpandDims", input, dim);
    }

    <T, U> Output<U> cast(Output<T> value, Class<U> type) {
      DataType dtype = DataType.fromClass(type);
      return g.opBuilder("Cast", "Cast")
          .addInput(value)
          .setAttr("DstT", dtype)
          .build()
          .<U>output(0);
    }

    Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
      return g.opBuilder("DecodeJpeg", "DecodeJpeg")
          .addInput(contents)
          .setAttr("channels", channels)
          .build()
          .<UInt8>output(0);
    }

    <T> Output<T> constant(String name, Object value, Class<T> type) {
      try (Tensor<T> t = Tensor.<T>create(value, type)) {
        return g.opBuilder("Const", name)
            .setAttr("dtype", DataType.fromClass(type))
            .setAttr("value", t)
            .build()
            .<T>output(0);
      }
    }
    Output<String> constant(String name, byte[] value) {
      return this.constant(name, value, String.class);
    }

    Output<Integer> constant(String name, int value) {
      return this.constant(name, value, Integer.class);
    }

    Output<Integer> constant(String name, int[] value) {
      return this.constant(name, value, Integer.class);
    }

    Output<Float> constant(String name, float value) {
      return this.constant(name, value, Float.class);
    }

    private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }

    private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }
    private Graph g;
  }
}


