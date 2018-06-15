package experimento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/** @author Jancleiton Nunes */

public class Instancia {
    
    private static float[][] matrizAdjacencia;
    private static int numeroCidades; 
    private static String nomeInstancia;
    
    public static void lerInstancia() throws FileNotFoundException{
        File arquivo  = null;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Escolha a instancia");
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setApproveButtonText("OK");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(true);
        int resultado = fc.showOpenDialog(fc);
        if (resultado == JFileChooser.CANCEL_OPTION){
            System.exit(1);
        }
        arquivo = fc.getSelectedFile();
           
        Scanner input = new Scanner(arquivo);
        String linha = new String();
        String[] linhaVetor = new String[2];
                
         while (true) {
                linha = input.nextLine();
                linhaVetor = linha.split(":");
                
                if(linhaVetor[0].trim().compareTo("NAME")==0){
                    nomeInstancia = linhaVetor[1].trim();
                    System.out.println("Instancia = " + nomeInstancia);
                }
                
                if(linhaVetor[0].trim().compareTo("DIMENSION")==0){
                    numeroCidades = Integer.parseInt(linhaVetor[1].trim());                    
                    System.out.println("Número de cidades = " + numeroCidades);
                }
                
                else if(linhaVetor[0].trim().compareTo("EDGE_WEIGHT_TYPE")==0){
                    switch(linhaVetor[1].trim()){
                        case "EUC_2D":
                            ler_EUC_2D(input);
                            break;
                        case "GEO":
                            ler_GEO(input);
                            break;
                    }                       
                } 
                else if (linhaVetor[0].trim().compareTo("EDGE_WEIGHT_FORMAT")==0){                    
                    switch(linhaVetor[1].trim()){
                        case "FULL_MATRIX":                              
                            ler_FULL_MATRIZ(input);
                            break;
                        case "LOWER_DIAG_ROW":
                            ler_LOWER_DIAG_ROW(input);
                            break;
                    } 
                    break;
                }                                    
            }             
         input.close();        
    }
    
    public static void escreveMedia(float cruzamento, float mutacao, float media) throws IOException{
        File arquivo = new File("graficos/"+nomeInstancia+"_grafico3d.txt");        
        if (!arquivo.exists()) {
            //cria um arquivo (vazio)
            arquivo.createNewFile();  
        }
        FileWriter fw = new FileWriter(arquivo, true); 
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(cruzamento+" "+mutacao+" "+media);
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public static void escreveVarianciaMedia(int exec, float media, float min, float max) throws IOException{
        File arquivo = new File("graficos/"+nomeInstancia+"_graficoVarianciaMedia.txt");
        if (!arquivo.exists()) {
            //cria um arquivo (vazio)
            arquivo.createNewFile();
        }
        FileWriter fw = new FileWriter(arquivo, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(exec+" "+media+" "+min+" "+max );
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public static void edge_weight_section(Scanner input){
        while(true){
            String linha = input.nextLine();
            String[] linhaVetor = linha.split(":");            
            if (linhaVetor[0].trim().compareTo("EDGE_WEIGHT_SECTION")==0){
                break;
            }
        }
    }
    
    public static void ler_FULL_MATRIZ(Scanner input){        
        edge_weight_section(input);//POSICIONA PARA LEITURA DOS DADOS        
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
        for(int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                matrizAdjacencia[i][j] = (float) input.nextInt();
            }
        }        
        imprimeMatriz();
    }        
    
    public static void ler_LOWER_DIAG_ROW(Scanner input){
        edge_weight_section(input);//POSICIONA PARA LEITURA DOS DADOS
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
        for(int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                if(i>=j){
                    matrizAdjacencia[i][j] = (float) input.nextInt();
                }                
            }
        }
        imprimeMatriz();
    }
 
    public static void ler_EUC_2D(Scanner input){
        edge_weight_section(input);//POSICIONA PARA LEITURA DOS DADOS
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
    }
    
    public static void ler_GEO(Scanner input){
        edge_weight_section(input);//POSICIONA PARA LEITURA DOS DADOS
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
    }
    
    public static void imprimeMatriz(){
        System.out.println("Matriz de adjacência:");
        for(int i = 0; i < numeroCidades; i++) {
            System.out.print("\n");
            for (int j = 0; j < numeroCidades; j++) {                    
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
        }        
    }
    
    public static float getDistancia(int cidade1, int cidade2){ 
        cidade1--;
        cidade2--;
        int aux;
        if(cidade1<cidade2){
            aux = cidade1;
            cidade1 = cidade2;
            cidade2 = aux;
        }
        float distancia = (float) matrizAdjacencia[cidade1][cidade2];        
        return distancia;   
    }

    public static float[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public static void setMatrizAdjacencia(float[][] matrizAdjacencia) {
        Instancia.matrizAdjacencia = matrizAdjacencia;
    }

    public static int getNumeroCidades() {
        return numeroCidades;
    }

    public static void setNumeroCidades(int numeroCidades) {
        Instancia.numeroCidades = numeroCidades;
    }
    
}
