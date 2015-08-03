/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconhecimentodenotas;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author u13166
 */
public class ReconhecimentoDeNotas {

    Nota[] notas = {
        new Nota("C1", 30.88, 34.65, 32.70),
        new Nota("C#1", 32.70, 36.71, 34.65),
        new Nota("D1", 34.65, 38.89, 36.71),
        new Nota("D#1", 36.71, 41.20, 38.89),
        new Nota("E1", 38.89, 43.65, 41.20),
        new Nota("F1", 41.20, 46.25, 43.65),
        new Nota("F#1", 43.65, 49.00, 46.25),
        new Nota("G1", 46.25, 51.91, 49.00),
        new Nota("G#1", 49.00, 55.00, 51.91),
        new Nota("A1", 51.91, 58.27, 55.00),
        new Nota("A#1", 55.00, 61.74, 58.27),
        new Nota("B1", 58.27, 65.41, 61.74),
        new Nota("C2", 61.74, 69.30, 65.41),
        new Nota("C#2", 65.41, 73.42, 69.30),
        new Nota("D2", 69.30, 77.78, 73.42),
        new Nota("D#2", 73.42, 82.41, 77.78),
        new Nota("E2", 77.78, 87.31, 82.41),
        new Nota("F2", 82.41, 92.50, 87.31),
        new Nota("F#2", 87.31, 98.00, 92.50),
        new Nota("G2", 92.50, 103.83, 98.00),
        new Nota("G#2", 98.00, 110.00, 103.83),
        new Nota("A2", 103.83, 116.54, 110.00),
        new Nota("A#2", 110.00, 123.47, 116.54),
        new Nota("B2", 116.54, 130.81, 123.47),
        new Nota("C3", 123.47, 138.59, 130.81),
        new Nota("C#3", 130.81, 146.83, 138.59),
        new Nota("D3", 138.59, 155.56, 146.83),
        new Nota("D#3", 146.83, 164.81, 155.56),
        new Nota("E3", 155.56, 174.61, 164.81),
        new Nota("F3", 164.81, 185.00, 174.61),
        new Nota("F#3", 174.61, 196.00, 185.00),
        new Nota("G3", 185.00, 207.65, 196.00),
        new Nota("G#3", 196.00, 220.00, 207.65),
        new Nota("A3", 207.65, 233.08, 220.00),
        new Nota("A#3", 220.00, 246.94, 233.08),
        new Nota("B3", 233.08, 261.63, 246.94),
        new Nota("C4", 246.94, 277.18, 261.63),
        new Nota("C#4", 261.63, 293.66, 277.18),
        new Nota("D4", 277.18, 311.13, 293.66),
        new Nota("D#4", 293.66, 329.63, 311.13),
        new Nota("E4", 311.13, 349.23, 329.63),
        new Nota("F4", 329.63, 369.99, 349.23),
        new Nota("F#4", 349.23, 392.00, 369.99),
        new Nota("G4", 369.99, 415.30, 392.00),
        new Nota("G#4", 392.00, 440.00, 415.30),
        new Nota("A4", 415.30, 466.16, 440.00),
        new Nota("A#4", 440.00, 493.88, 466.16),
        new Nota("B4", 466.16, 523.25, 493.88),
        new Nota("C5", 493.88, 554.37, 523.25),
        new Nota("C#5", 523.25, 587.33, 554.37),
        new Nota("D5", 554.37, 622.25, 587.33),
        new Nota("D#5", 587.33, 629.25, 622.25),
        new Nota("E5", 622.25, 698.46, 629.25),
        new Nota("F5", 629.25, 739.99, 698.46),
        new Nota("F#5", 698.46, 783.99, 739.99),
        new Nota("G5", 739.99, 830.61, 783.99),
        new Nota("G#5", 783.99, 880.00, 830.61),
        new Nota("A5", 830.61, 932.33, 880.00),
        new Nota("A#5", 880.00, 987.77, 932.33),
        new Nota("B5", 932.33, 1046.50, 987.77),};

    TargetDataLine targetDataLine;
    CaptureThread captureThread;
    AudioFormat audioFormat;

    double freqMin = 77.781;
    double freqMax = 87.307;
    double freqOK = 82.406;

    int coordSelectedx = 70;
    int coordSelectedy = 134;
    int xPos = 171;
    int yPos = 98 - 40;

    char notaSelecionada = 'E';

    int dispError = 0;
    Panel infoPanel = new Panel();
    Button closeButton = new Button("Close");
    boolean bStopTuner = false;

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    public ReconhecimentoDeNotas() {
        audioFormat = new AudioFormat(8000.0F, 16, 1, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);

            captureThread = new CaptureThread();
            captureThread.start();

        } catch (Exception e2) {
            dispError = 1;
            System.out.println("Error : Unable to start acqusition -> " + e2);
        }
    }

    public class CaptureThread extends Thread {

        @Override
        public void run() {
            try {

                int cnt2 = 0;
                int spectreSize = 2048 * 2 * 2 * 2 * 2;
                int sampleSize = 2048 * 2 * 2;
                double divi = 4 * 2 * (4096 / 4000);
                byte data[] = new byte[spectreSize];
                int valtemp = 0;
                targetDataLine.start();
                int nbValues = 0;
                double tempValue = 0;
                int nbMesures = 1;
                double[] ar = new double[spectreSize];
                double[] ai = new double[spectreSize];

                while (((cnt2 = targetDataLine.read(data, 0, sampleSize)) > 0)) {

                    try {
                        for (int i = 0; i < sampleSize; i++) {
                            ar[i] = (double) data[i];
                            ai[i] = 0.0;
                        }
                        for (int i = sampleSize; i < spectreSize; i++) {
                            ar[i] = 0.0;
                            ai[i] = 0.0;
                        }
                        computeFFT(1, spectreSize, ar, ai);
                        double menorAngulo = Double.MAX_VALUE;
                        int menorIndice = -1;
                        for(int k=0; k<notas.length; k++){
                            double maxFreq = 0;
                            double maxAmpl = 0;
                            double maxIndex = 0;
                            double erreur = 0;

                            for (int i = (int) (notas[k].getFreqMin() * divi); i < (notas[k].getFreqMax() * divi); i++) {
                                if (Math.abs(ai[i]) > maxAmpl) {
                                    maxFreq = ar[i];
                                    maxAmpl = Math.abs(ai[i]);
                                    maxIndex = i;

                                }

                            }

                            if (maxAmpl > 0.01) {
                                erreur = ((maxIndex / divi - notas[k].getFreqOk()) / (notas[k].getFreqOk() - notas[k].getFreqMin()));

                                tempValue += erreur;

                                nbValues += 1;
                                if (nbValues > (nbMesures - 1)) {

                                    double angle = ((tempValue / nbMesures)) - 0.25;
                                    if(angle < menorAngulo){
                                        menorAngulo = angle;
                                        menorIndice = k;
                                    }
                                    nbValues = 0;
                                    tempValue = 0;
                                }
                            }
                        }
                        System.out.println(notas[menorIndice].getNome());

                    } catch (Exception e2) {
                        System.out.println(e2);

                    }

                    targetDataLine.flush();
                }

            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
        }

    }

    public static void computeFFT(int sign, int n, double[] ar, double[] ai) {
        double scale = 2.0 / (double) n;
        int i, j;
        for (i = j = 0; i < n; ++i) {
            if (j >= i) {
                double tempr = ar[j] * scale;
                double tempi = ai[j] * scale;
                ar[j] = ar[i] * scale;
                ai[j] = ai[i] * scale;
                ar[i] = tempr;
                ai[i] = tempi;
            }
            int m = n / 2;
            while ((m >= 1) && (j >= m)) {
                j -= m;
                m /= 2;
            }
            j += m;
        }
        int mmax, istep;
        for (mmax = 1, istep = 2 * mmax; mmax < n; mmax = istep, istep = 2 * mmax) {
            double delta = sign * Math.PI / (double) mmax;
            for (int m = 0; m < mmax; ++m) {
                double w = m * delta;
                double wr = Math.cos(w);
                double wi = Math.sin(w);
                for (i = m; i < n; i += istep) {
                    j = i + mmax;
                    double tr = wr * ar[j] - wi * ai[j];
                    double ti = wr * ai[j] + wi * ar[j];
                    ar[j] = ar[i] - tr;
                    ai[j] = ai[i] - ti;
                    ar[i] += tr;
                    ai[i] += ti;
                }
            }
            mmax = istep;
        }
    }

    public void calcularNota() {
        if (notaSelecionada == 'E') {
            freqMin = 77.781;
            freqMax = 87.307;
            freqOK = 82.406;

        }
        /* if (x > 110 && y > 137 && x < 128 && y < 154) {
         freqMin = 103.826;
         freqMax = 116.540;
         freqOK = 110.0;

         imgSelected = imgA;
         coordSelectedx = 106;
         }
         if (x > 144 && y > 137 && x < 163 && y < 154) {
         freqMin = 138.591;
         freqMax = 155.563;
         freqOK = 146.832;
         imgSelected = imgD;
         coordSelectedx = 141;
         }
         if (x > 180 && y > 137 && x < 199 && y < 154) {
         freqMin = 184.997;
         freqMax = 207.652;
         freqOK = 195.997;
         imgSelected = imgG;
         coordSelectedx = 176;
         }
         if (x > 214 && y > 137 && x < 236 && y < 154) {
         freqMin = 233.081;
         freqMax = 261.625;
         freqOK = 246.941;
         imgSelected = imgB;
         coordSelectedx = 212;
         }
         if (x > 248 && y > 137 && x < 268 && y < 154) {
         freqMin = 311.126;
         freqMax = 349.228;
         freqOK = 329.627;
         imgSelected = imgE;
         coordSelectedx = 247;
         }
         if (x > 24 && y > 179 && x < 313 && y < 198) {

         infoPanel.setVisible(true);
         }
   
         */
    }

}
