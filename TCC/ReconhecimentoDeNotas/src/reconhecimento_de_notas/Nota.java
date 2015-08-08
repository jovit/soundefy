package reconhecimento_de_notas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author u13166
 */
public class Nota {
    
    private final String nome;
    private final double freqMin;
    private final double freqMax;
    private final double freqOk;

    public Nota(String nome, double freqMin, double freqMax, double freqOk) {
        this.nome = nome;
        this.freqMin = freqMin;
        this.freqMax = freqMax;
        this.freqOk = freqOk;
    }

    public String getNome() {
        return nome;
    }

    public double getFreqMin() {
        return freqMin;
    }

    public double getFreqMax() {
        return freqMax;
    }

    public double getFreqOk() {
        return freqOk;
    }
    
}
