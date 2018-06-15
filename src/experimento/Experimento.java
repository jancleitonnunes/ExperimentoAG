package experimento;

import java.io.FileNotFoundException;
import java.io.IOException;

/** @author Jancleiton Nunes */

public class Experimento {
  
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Instancia.lerInstancia();        
        System.out.println("\nINICIALIZANDO ...");
        double t = System.currentTimeMillis();
        float distanciaCadaExecucao[] = new float[10];//DISTANCIA DE CADA EXECUÇÃO PARA CALCULAR O MÍNIMO, MÁXIMO E MÉDIA
        AG ag = new AG();
        int exec = 0;
        Cromossomo resultado;        
        float cruzamento, mutacao, distanciaMedia;
        for(cruzamento=51; cruzamento<=100; cruzamento++){
            for(mutacao=0.5f; mutacao<=10; mutacao=mutacao+0.5f){
                exec++;
                distanciaMedia = 0f;
                for(int i=0; i<10; i++){
                    ag = new AG();
                    resultado = new Cromossomo();
                    System.out.println((i+1)+"ª execução");
                    double tempoExecucao = System.currentTimeMillis();
                    resultado = ag.executar(cruzamento, mutacao);
                    
                    if (resultado.getDistancia() < Instancia.getMelhorSolucaoConhecida()){
                        System.out.println("IGUAL OU ABAIXO DA MELHOR SOLUÇÃO CONHECIDA: "+resultado);
                    }
                    
                    tempoExecucao = System.currentTimeMillis() - tempoExecucao;
                    tempoExecucao = tempoExecucao/1000;//para ficar em segundos                    
                    System.out.println("Tempo de execução: "+tempoExecucao+" segundos");
                    
                    distanciaCadaExecucao[i] = resultado.getDistancia();                                        
                    System.out.println("Distancia da Execução: "+resultado.getDistancia());
                }    
                //SOMA AS DISTANCIAS, CALCULA A MEDIA, SELECIONA A MAIOR E MENOR 
                float maior = distanciaCadaExecucao[0];
                float menor = distanciaCadaExecucao[0];
                for(int i=0; i<10; i++){
                    if(maior < distanciaCadaExecucao[i]){
                        maior = distanciaCadaExecucao[i];
                    }
                    if(menor > distanciaCadaExecucao[i]){
                        menor = distanciaCadaExecucao[i];
                    }
                    distanciaMedia += distanciaCadaExecucao[i];
                }
                
                distanciaMedia = distanciaMedia/10; //CALCULA MÉDIA DAS DISTANCIAS
                System.out.println("seq "+exec+" Cruzamento: "+cruzamento+" Mutação: "+mutacao+" Distância média: "+distanciaMedia+" Maior distancia: "+maior+" Menor distancia: "+menor);
                
                //ESCREVE RESULTADO EM ARQUIVO PARA GERAÇÃO DE GRAFICOS
                //grafico 3d com taxas de cruzamento, mutacao e distancia media
                Instancia.escreveMedia(cruzamento, mutacao, distanciaMedia);  
                //grafico de tolerancia para as 1.000 execucoes                
                Instancia.escreveVarianciaMedia(exec, distanciaMedia, menor, maior);                
            }
        }
        t = System.currentTimeMillis() - t; 
        t = t/1000;//para ficar em segundos
        System.out.println("Tempo de execução do experimento: "+t+" segundos");
        
        
        //TESTE AVALIACAO
        /*Instancia.lerInstancia();
        Cromossomo c = new Cromossomo();        
        ArrayList<Integer> array = new ArrayList<>();
        array.add(7);
        array.add(25);
        array.add(19);
        array.add(4);
        array.add(15);
        array.add(11);
        array.add(22);
        array.add(14);
        array.add(17);
        array.add(18);
        array.add(10);
        array.add(20);
        array.add(21);
        array.add(2);
        array.add(13);
        array.add(16);
        array.add(23);
        array.add(27);
        array.add(8);
        array.add(24);
        array.add(1);
        array.add(28);
        array.add(6);
        array.add(12);
        array.add(9);
        array.add(5);
        array.add(26);
        array.add(29);
        array.add(3);
        c.setCidades(array);
        c.recalcularAvaliacao();
        System.out.println("\n\nDistancia = "+c.getDistancia());*/



        //TESTE RANKING
        /*Cromossomo x1 = new Cromossomo(1);
        x1.setAvaliacao(2000);
        Cromossomo x2 = new Cromossomo(1);
        x2.setAvaliacao(10);
        Cromossomo x3 = new Cromossomo(1);
        x3.setAvaliacao(15);
        Cromossomo x4 = new Cromossomo(1);
        x4.setAvaliacao(1000);
        Cromossomo x5 = new Cromossomo(1);
        x5.setAvaliacao(1500);
        Cromossomo x6 = new Cromossomo(1);
        x6.setAvaliacao(30000);
        
        ArrayList<Cromossomo> geracao = new ArrayList<>();
        
        geracao.add(x1);
        geracao.add(x2);
        geracao.add(x3);
        geracao.add(x4);
        geracao.add(x5);
        geracao.add(x6);        
        
        double t = System.currentTimeMillis();
        
        AG ag = new AG();
        ag.setGeracao(geracao);       
        
        for(int i = 0; i<ag.getGeracao().size(); i++){
            System.out.println("x"+(i+1)+": "+ ag.getGeracao().get(i).getAvaliacao());
        }
        
        ag.gerarRanking();
        
        System.out.println("Montagem de ranking:");
        
        for(int i = 0; i<ag.getGeracao().size(); i++){
            System.out.println((i+1)+"º: "+ ag.getGeracao().get(i).getAvaliacaoRanking());
        }
                
        t = System.currentTimeMillis() - t; 
        t = t/1000;//para ficar em segundos
        System.out.println("tempo de execução: "+t+" segundos");
        */
    }    
}
