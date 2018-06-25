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
                    
                    if (resultado.getDistancia() <= Instancia.getMelhorSolucaoConhecida()){
                        System.out.println("IGUAL OU ABAIXO DA MELHOR SOLUÇÃO CONHECIDA: "+resultado);
                        Instancia.escreveMelhorSolucaoConhecida(resultado, cruzamento, mutacao);
                    }
                    
                    tempoExecucao = System.currentTimeMillis() - tempoExecucao;
                    tempoExecucao = tempoExecucao/1000;//para ficar em segundos                    
                    System.out.println("Tempo de execução: "+tempoExecucao+" segundos");
                    
                    distanciaCadaExecucao[i] = resultado.getDistancia();                                        
                    System.out.println("Melhor distância da Execução: "+resultado.getDistancia());
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
        
    }    
}
