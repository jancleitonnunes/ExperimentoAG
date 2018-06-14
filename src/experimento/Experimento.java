package experimento;

import java.io.FileNotFoundException;
import java.io.IOException;

/** @author Jancleiton Nunes */

public class Experimento {
  
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Instancia.lerInstancia();        
        System.out.println("\nINICIALIZANDO ...");
        double t = System.currentTimeMillis();
        AG ag = new AG();
        Cromossomo resultado;        
        float cruzamento, mutacao, distanciaMedia;
        for(cruzamento=51; cruzamento<=100; cruzamento++){
            for(mutacao=0.5f; mutacao<=10; mutacao=mutacao+0.5f){
                distanciaMedia = 0f;
                for(int i=0; i<10; i++){
                    ag = new AG();
                    resultado = new Cromossomo();
                    System.out.println((i+1)+"ª execução");
                    double tempogeracao = System.currentTimeMillis();
                    resultado = ag.executar(cruzamento, mutacao);
                    /*if (resultado.getDistancia()<(float)699.0){
                        System.out.println("ABAIXO DE 699: "+resultado);
                    }*/
                    
                    tempogeracao = System.currentTimeMillis() - tempogeracao;
                    tempogeracao = tempogeracao/1000;//para ficar em segundos                    
                    System.out.println("tempo de execução: "+tempogeracao+" segundos");
                    
                    distanciaMedia = distanciaMedia + resultado.getDistancia(); 
                    System.out.println("Distancia: "+resultado.getDistancia());
                }               
                distanciaMedia = distanciaMedia/10;
                System.out.println("Cruzamento: "+cruzamento+" Mutação: "+mutacao+" Distância média: "+distanciaMedia);
                //ESCREVE RESULTADO EM ARQUIVO PARA GERAÇÃO DE GRAFICOS
                Instancia.escreveMedia(cruzamento, mutacao, distanciaMedia);                
            }
        }
        t = System.currentTimeMillis() - t; 
        t = t/1000;//para ficar em segundos
        System.out.println("tempo de execução: "+t+" segundos");
        
        
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
