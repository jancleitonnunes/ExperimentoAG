package experimento;

import java.util.ArrayList;
import java.util.Random;

/** @author Jancleiton Nunes */

public class Cromossomo implements Comparable<Cromossomo>{

    private ArrayList<Integer> cidades;   
    private float distancia;
    private float avaliacao;
    private float avaliacaoRanking;  
    
    public Cromossomo(){
        cidades = new ArrayList<Integer>();
        avaliacao = 0.0f;
        avaliacaoRanking = 0.0f;
        distancia = 0.0f;
    }
    
    public Cromossomo(int numeroCidades){
        cidades = new ArrayList<Integer>();
        avaliacao = 0.0f;
        avaliacaoRanking = 0.0f;
        gerarSolucao(numeroCidades);
        avaliacao = calcularAvaliacao();
    }
    
    public void recalcularAvaliacao(){
        avaliacao = calcularAvaliacao();
    }
    
    public void gerarSolucao(int numeroCidades){
        //sorteia numeros sem repeticao
        ArrayList<Integer> numeros = new ArrayList<>();
        for(int i=0; i<numeroCidades; i++){
            numeros.add(i+1);
        }
        //RETIRANDO ALEATORIAMENTE DE NUMEROS E ADD EM CIDADES
        while(numeros.size() > 0){
            Random gerador = new Random();
            int indice = gerador.nextInt(numeros.size())+1;            
            this.cidades.add(numeros.get(indice-1));                                   
            numeros.remove(indice-1);                                               
        }        
    }           
    
    public float calcularAvaliacao(){        
        distancia = 0.0f;
        for(int i=0; i<=this.cidades.size()-1; i++){
            int cidade1, cidade2;
            if(i==this.cidades.size()-1){
                cidade1 = this.cidades.get(0);
                cidade2 = this.cidades.get(i);                
            }
            else{
                cidade1 = this.cidades.get(i);
                cidade2 = this.cidades.get(i+1);                                                 
            }
            distancia = distancia + Instancia.getDistancia(cidade1, cidade2);            
            
        }  
        //PROBLEMA DE MINIMIZAÇÃO
        avaliacao = 1/distancia;        
        return avaliacao;        
    }

    public ArrayList<Integer> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<Integer> cidades) {
        this.cidades = cidades;
    }       

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public float getAvaliacaoRanking() {
        return avaliacaoRanking;
    }

    public void setAvaliacaoRanking(float avaliacaoRanking) {
        this.avaliacaoRanking = avaliacaoRanking;
    }    
    
    public int compareTo(Cromossomo outroCromossomo) {
        //PARA ORDENAÇÃO
        if (this.avaliacao < outroCromossomo.getAvaliacao()) {
            return -1;
        }
        if (this.avaliacao > outroCromossomo.getAvaliacao()) {
            return 1;
        }
        return 0;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }        

    @Override
    public String toString() {
        return "Cromossomo{" + "cidades=" + cidades + '}' + " Distancia: " + distancia + " Avaliacao: " + avaliacao;
    }        
}
