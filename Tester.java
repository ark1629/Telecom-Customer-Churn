
/**
 * Copyright 2023 Heinz Silberbauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;






/**
 * Neural network: Backpropagation tester 
 * 
 * Note: a training data set is defined at the end of this file.
 */
public class Tester {

    double[][] trainingData;
    
    /**
     * Runs the test.
     * 
     * @param args        the arguments
     */
    public static void main(String[] args) {

        new Tester();
    }
    
    
    /**
     * Construct the tester, model, test data, run some training and 
     * display the output of each training.
     */
    public Tester() {
        
     /*   
     * This default model has one hidden layer with about 10% neurons than the input layer,
     * the default learning rate and weights/biases initialized using random numbers<br>
     * in the ranges [0.1 .. 0.5]. 
     */
    
        int anzahlEingabeNeuronen=2;
        int anzahlHiddenNeuronen=2;
        int anzahlAusgabeNeuronen=1;
        float learningRate = 0.9f;

        //Daten einlesen
        double[][] trainingData=this.datenEinlesen();
        
        // create the network
        BackpropNeuralNetwork neuralNetwork = new BackpropNeuralNetwork(anzahlEingabeNeuronen, anzahlHiddenNeuronen, anzahlAusgabeNeuronen, learningRate);
        //neuralNetwork.setLearningRate(BackpropNeuralNetwork.DEFAULT_LEARNING_RATE);        // note: DEFAULT_LEARNING_RATE is already set
        neuralNetwork.createInOutVectors(trainingData);
        // train the network using the truth table of a XOR gate
        //neuralNetwork.createInOutVectors(trainingData);
        // four data sets (input vectors, so each data set will be trained 10 times)
        int trainigStepsPerSet = 10;
        int trainings = 800;
        // display the result of the training
        neuralNetwork.trainRandom(trainings, trainigStepsPerSet);                  
        System.out.println("\n***** Training (default learning rate): " + trainings + " trainings (each " 
            + trainigStepsPerSet + " steps) *****");
        displayAfterTraining(neuralNetwork);
        // do it again using more training
        /*trainings = 20000;
        neuralNetwork = new BackpropNeuralNetwork(2, 2, 1);
        neuralNetwork.createInOutVectors(trainingData);
        neuralNetwork.trainRandom(trainings, trainigStepsPerSet);            
        System.out.println("\n***** Training (default learning rate): " + trainings + " trainings (each " 
        + trainigStepsPerSet + " steps) *****");
        displayAfterTraining(neuralNetwork);
        // do it again using a higher learning rate and less training
        trainings = 2000;
        neuralNetwork = new BackpropNeuralNetwork(2, 2, 1);
        double learningRate = 0.3;            // high due to XOR behavior and few inputs
        neuralNetwork.setLearningRate(learningRate);        
        neuralNetwork.createInOutVectors(trainingData);
        neuralNetwork.trainRandom(trainings, trainigStepsPerSet);            
        System.out.println("\n***** Training (learning rate " + learningRate 
        + "): " + trainings + " trainings (each " 
        + trainigStepsPerSet + " steps) *****");
        displayAfterTraining(neuralNetwork);
         */
    }

    /**
     * Displays the results after a training.
     * 
     * @param neuralNetwork                the network
     */
    private void displayAfterTraining(BackpropNeuralNetwork neuralNetwork) {

        for (int i = 0; i < testData.length; i += 2) {
            double[] inputs = testData[i];
            System.out.println("\nInput: " + Arrays.toString(inputs));
            double[] outputs = neuralNetwork.forwardPass(inputs);
            System.out.println("Output: " + Arrays.toString(outputs));
            System.out.println("Desired output: " + Arrays.toString(testData[i + 1]));
        }
    }

    

    /** data set for training: {input vector: two inputs}, {output vector: one output} */
    //Daten einlesen
    public double[][] datenEinlesen(){
        String line = "";
        String path ="punkte.txt";
        int anzahlPunkte;
        double[][] punkte;
        int lineNum = 1;   
        try { 
            BufferedReader br1 = new BufferedReader(new FileReader(path));
            anzahlPunkte=0;
            //Zeilen bzw. Punkte zählen
            while((line = br1.readLine()) != null) {
                anzahlPunkte++;
            }
            System.out.println(anzahlPunkte);
            punkte = new double[20][2];

            //Zeilen bzw. Punkte einlesen
            BufferedReader br = new BufferedReader(new FileReader(path));
            String[] values;
            while((line = br.readLine()) != null) {

                values = line.split(","); //zerteilt eine Zeile in Werte in einem Feld
                if(lineNum >= 1 && lineNum < anzahlPunkte + 1) {
                    //Verteilt die drei Werte je Zeile auf zwei Vektoren
                    punkte[lineNum*2 - 2][0] = Double.valueOf(values[0]);
                    punkte[lineNum*2 - 2][1] = Double.valueOf(values[1]);
                    punkte[lineNum*2 - 1][0] = Double.valueOf(values[2]);

                }
                lineNum += 1;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden.");
            punkte = new double[0][0];
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("i");
            punkte = new double[0][0];
            e.printStackTrace();
        }
        return punkte;
    }

    
    
    //public static final double[][] trainingData=datenEinlesen();
    
    public static final double[][] testData = {
            {0.9, 0.3}, {0},
            {0.5, 0.2}, {0},
            {0.1, 0.3}, {1},
            {0.4, 0.3}, {1},
            {0.9, 0.5}, {0},
            {0.5, 0.3}, {0},
            {0.3, 0.6}, {1},
            {0.4, 0.6}, {1},
        };
}
