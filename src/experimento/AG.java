package experimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/** @author Jancleiton Nunes */

public class AG {
    
    private ArrayList<Cromossomo> geracao;
    private ArrayList<Cromossomo> elite;   
    private Cromossomo melhorSolucao;
    
    public AG(){
        this.geracao = new ArrayList<>();
        this.elite = new ArrayList<>();
        this.melhorSolucao = new Cromossomo();
    }
    
    public Cromossomo executar(float probCruzamento, float probMutacao){        
        //INICIALIZA POPULAÇÃO COM 100 INDIVIDUOS ALEATORIOS
        Cromossomo c;
        for(int i=0; i<100; i++){
            c = new Cromossomo(Instancia.getNumeroCidades());
            this.geracao.add(c);
        }                       
        atualizaMelhorSolucao();                          
        
        double tempoExecucao = 0.0;
        int numeroGeracoes = 0;        
        while(tempoExecucao<10000){
            double tempoInicio = System.currentTimeMillis();
            ArrayList<Cromossomo> novaGeracao = null;
            novaGeracao = new ArrayList<Cromossomo>();
            for(int j=0; j<100; j++){                
                c = selecione();                
                if(verificaProbabilidade(probCruzamento)){                    
                    c = cruzamento(c, selecione());                                 
                }
                if(verificaProbabilidade(probMutacao)){                    
                    c = mutacao(c);                    
                }                                                               
                novaGeracao.add(c);                
                
            }             
            //passar de nova geração para this.geracao
            this.geracao = (ArrayList<Cromossomo>) novaGeracao.clone();
            this.geracao.set(piorSolucao(this.geracao), melhorSolucao);
            atualizaMelhorSolucao();            
            
            double tempoParcial = System.currentTimeMillis() - tempoInicio;            
            tempoExecucao = tempoExecucao + tempoParcial;
            numeroGeracoes++;            
        }                        
        System.out.println("Nº DE GERAÇÕES: "+numeroGeracoes);
        return this.melhorSolucao;
    }
    
    public int piorSolucao(ArrayList<Cromossomo> geracao){
        Cromossomo piorSolucao = melhorSolucao;
        int indicePiorSolucao = 0;
        for(int i=0; i<geracao.size(); i++){
            if(geracao.get(i).getAvaliacao()<piorSolucao.getAvaliacao()){
                indicePiorSolucao = i;
            }
        }
        return indicePiorSolucao;
    }
    
    public boolean verificaProbabilidade(float probCruzamento){
        Random sorteio = new Random();
        Float numeroSorteado = sorteio.nextFloat();
        if(numeroSorteado<=probCruzamento){
            return true;
        }
        return false;
    }
        
    public Cromossomo selecione(){        
        gerarRanking();
        int indice = roletaViciada(); //COM BASE NO RANKING
        if(indice>this.geracao.size() || indice<0){
            System.out.println("erro indice: "+indice);
        }
        return this.geracao.get(indice);
    }
    
    public void atualizaMelhorSolucao(){
        for(Cromossomo c:this.geracao){
            if(this.melhorSolucao.getAvaliacao()<c.getAvaliacao()){
                this.melhorSolucao = c;
            }
        }                    
    }
    
    public Cromossomo mutacao(Cromossomo cromossomo){
        //INVERSÃO DE SUB-LISTA
        int inicio, fim;
        inicio = new Random().nextInt(cromossomo.getCidades().size());
        if (inicio == cromossomo.getCidades().size()){
            inicio--;
        }
        fim = inicio + new Random().nextInt(cromossomo.getCidades().size()-inicio);
        if (fim == cromossomo.getCidades().size()){
            fim--;
        }
        int aux[] = new int[fim-inicio+1];
        for(int i=inicio; i<=fim; i++){
            aux[i-inicio] = cromossomo.getCidades().get(fim-i+inicio);
        }       
        for(int i=inicio; i<=fim; i++){
            cromossomo.getCidades().set(i, aux[i-inicio]);                       
        }        
        cromossomo.recalcularAvaliacao();
        return cromossomo;
    }
    
    public Cromossomo cruzamento(Cromossomo pai1, Cromossomo pai2){
        Cromossomo filho = new Cromossomo(pai1.getCidades().size());
        ArrayList<Integer> aux = new ArrayList<>();
        Random sorteio = new Random();
        
        for(int i=0; i<pai1.getCidades().size(); i++){
            if(sorteio.nextBoolean() == true){
                filho.getCidades().set(i, pai1.getCidades().get(i));
            }
            else{
                aux.add(pai1.getCidades().get(i));
                filho.getCidades().set(i, -1);                                                        
            }
        }
        int j=0;        
        for(int i=0; i<pai1.getCidades().size(); i++){
            while((j<pai1.getCidades().size())&&(filho.getCidades().get(j)!=-1)){
                j++;
            }
            if(aux.indexOf(pai2.getCidades().get(i))>=0){
                filho.getCidades().set(j, pai2.getCidades().get(i));                                           
                j++;
            }
        }        
        filho.recalcularAvaliacao();
        return filho;
    }      
    
    public int roletaViciada(){
        //ROLETA VICIADA COM BASE NO RANKING                
        float somaAvaliacoes = somaAvaliacaoRanking(this.geracao); 
        float aux = (float) 0.0;
        int i;
        float limite = new Random().nextFloat()*somaAvaliacoes;                
        for (i=0; ((i<this.geracao.size())&&(aux<limite)); i++){
            aux += this.geracao.get(i).getAvaliacaoRanking();
        }
        if(i!=0){
            i--;
        }                    
        return i;
    }         
    
    public float somaAvaliacaoRanking (ArrayList<Cromossomo> populacao){
        float soma = 0.0f;
        for(Cromossomo c: populacao){
            soma = soma + c.getAvaliacaoRanking();
        }
        return soma;
    }
    
    public void gerarRanking(){        
        Collections.sort(this.geracao);          
        float min = 0.9f;
        float max = 1.1f;               
        float tamanhoGeracao = this.geracao.size();        
        float avaliacaoRanking;        
        for(int i=0; i<this.geracao.size();i++){            
            avaliacaoRanking = min+((max-min)*((i+1-1)/(tamanhoGeracao-1)));            
            this.geracao.get(i).setAvaliacaoRanking(avaliacaoRanking);            
        }        
    }

    public ArrayList<Cromossomo> getGeracao() {
        return geracao;
    }

    public void setGeracao(ArrayList<Cromossomo> geracao) {
        this.geracao = geracao;
    }

    public ArrayList<Cromossomo> getElite() {
        return elite;
    }

    public void setElite(ArrayList<Cromossomo> elite) {
        this.elite = elite;
    }      

    public Cromossomo getMelhorSolucao() {
        return melhorSolucao;
    }

    public void setMelhorSolucao(Cromossomo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }     
}
