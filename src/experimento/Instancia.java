package experimento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFileChooser;

/** @author Jancleiton Nunes */

public class Instancia {
    
    private static float[][] matrizAdjacencia;
    private static int numeroCidades; 
    private static String nomeInstancia;
    private static float melhorSolucaoConhecida;
    
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
                String linha0 = linhaVetor[0].trim();
                
                if(linha0.equalsIgnoreCase("NAME")){
                    nomeInstancia = linhaVetor[1].trim();
                    atualizaMelhorSolucaoConhecida(nomeInstancia);
                    System.out.println("Instancia = " + nomeInstancia);
                    System.out.println("Melhor solução = " + melhorSolucaoConhecida);
                }                                
                
                else if(linha0.equalsIgnoreCase("DIMENSION")){
                    numeroCidades = Integer.parseInt(linhaVetor[1].trim());                    
                    System.out.println("Número de cidades = " + numeroCidades);
                }
                
                else if(linha0.equalsIgnoreCase("EDGE_WEIGHT_TYPE") && (nomeInstancia.equalsIgnoreCase("eil51") || nomeInstancia.equalsIgnoreCase("burma14"))){                                        
                    switch(linhaVetor[1].trim()){
                        case "EUC_2D":
                            ler_EUC_2D(input);
                            break;
                        case "GEO":
                            ler_GEO(input);
                            break;
                    }    
                    break;
                }     
                
                else if(linha0.equalsIgnoreCase("EDGE_WEIGHT_FORMAT")){                                          
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
    
    public static void atualizaMelhorSolucaoConhecida(String nomeInstancia){
        switch(nomeInstancia){
            case "burma14":
                melhorSolucaoConhecida = (float) 3323;
                break;
            case "bays29":
                melhorSolucaoConhecida = (float) 2020;
                break;
            case "dantzig42":
                melhorSolucaoConhecida = (float) 699;
                break;
            case "eil51":
                melhorSolucaoConhecida = (float) 426;
                break;
            default:
                melhorSolucaoConhecida = (float) 0;
                System.out.println("Não foi possível atualizar a melhor solução conhecida!");
        }
    }
    
    public static void escreveMedia(float cruzamento, float mutacao, float media) throws IOException{
        DecimalFormat df = new DecimalFormat("0.0");        
        File arquivo = new File("graficos/"+nomeInstancia+"_grafico3d.txt");        
        if (!arquivo.exists()) {            
            arquivo.createNewFile();//cria um arquivo (vazio)  
        }
        FileWriter fw = new FileWriter(arquivo, true); 
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(cruzamento+" "+mutacao+" "+df.format(media));
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public static void escreveVarianciaMedia(int exec, float media, float min, float max) throws IOException{
        DecimalFormat df = new DecimalFormat("0.0");        
        File arquivo = new File("graficos/"+nomeInstancia+"_graficoVarianciaMedia.txt");
        if (!arquivo.exists()) {            
            arquivo.createNewFile();//cria um arquivo (vazio)
        }
        FileWriter fw = new FileWriter(arquivo, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(exec+" "+df.format(media)+" "+df.format(min)+" "+df.format(max));
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public static void escreveMelhorSolucaoConhecida(Cromossomo solucao, float cruzamento, float mutacao) throws IOException{                
        File arquivo = new File("graficos/"+nomeInstancia+"_melhorSolucaoConhecida.txt");        
        if (!arquivo.exists()) {            
            arquivo.createNewFile();//cria um arquivo (vazio)  
        }
        FileWriter fw = new FileWriter(arquivo, true); 
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Cruzamento: "+cruzamento+" Mutação: "+mutacao);
        bw.write("Melhor Solução: "+solucao);
        bw.newLine();
        bw.close();
        fw.close();
    }        
    
    public static void posicionaParaLeitura(Scanner input, String secaoDados){
        while(true){
            String linha = input.nextLine();
            String[] linhaVetor = linha.split(":");                        
            if (linhaVetor[0].trim().compareTo(secaoDados) == 0){                
                break;
            }
        }
    }
    
    public static void ler_FULL_MATRIZ(Scanner input){          
        String secao = "EDGE_WEIGHT_SECTION";
        posicionaParaLeitura(input, secao);//POSICIONA PARA LEITURA DOS DADOS        
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
        for(int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                matrizAdjacencia[i][j] = (float) input.nextInt();
            }
        }        
        imprimeMatriz();
    }        
    
    public static void ler_LOWER_DIAG_ROW(Scanner input){
        posicionaParaLeitura(input, "EDGE_WEIGHT_SECTION");//POSICIONA PARA LEITURA DOS DADOS        
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
        posicionaParaLeitura(input, "NODE_COORD_SECTION");//POSICIONA PARA LEITURA DOS DADOS        
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
        ArrayList<Ponto> cidades = new ArrayList<>();
        Ponto p;
        for(int i=0;i<numeroCidades;i++){
            p = new Ponto();
            p.setCidade(input.nextInt());
            p.setX(input.nextInt());
            p.setY(input.nextInt());
            cidades.add(p);
        }
        
        /*System.out.println("CIDADES E SUAS COORDENADAS: ");
        for(Ponto ponto:cidades){
            System.out.println(ponto);
        }*/        
                        
        for(int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                if(i!=j){
                    float xd = cidades.get(i).getX() - cidades.get(j).getX();
                    float yd = cidades.get(i).getY() - cidades.get(j).getY();
                    int dist = (int) Math.sqrt(xd*xd + yd*yd);
                    matrizAdjacencia[i][j] = dist;//RESTRINGIR PARA 2 CASAS DECIMAIS
                }                
            }
        }
        imprimeMatriz();
    }
    
    public static void ler_GEO(Scanner input){
        input.useLocale(Locale.US);
        posicionaParaLeitura(input, "NODE_COORD_SECTION");//POSICIONA PARA LEITURA DOS DADOS
        matrizAdjacencia = new float[numeroCidades][numeroCidades];
        ArrayList<Ponto> cidades = new ArrayList<>();
        Ponto p;
        for(int i=0;i<numeroCidades;i++){
            p = new Ponto();
            p.setCidade(input.nextInt());
            p.setX(input.nextFloat());
            p.setY(input.nextFloat());
            cidades.add(p);
        }
        
        for(int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                if(i!=j){
                    //CALCULO DA LATITUDE E LONGITUDE PARA CIDADE DE INDICE i
                    int deg = (int) cidades.get(i).getX();
                    float min = cidades.get(i).getX() - deg;
                    float latitude_i = (float) ((float) Math.PI * (deg + 5.0 * min / 3.0) / 180.0);                    
                    deg = (int) cidades.get(i).getY();
                    min = cidades.get(i).getY() - deg;
                    float longitude_i = (float) ((float) Math.PI * (deg + 5.0 * min / 3.0) / 180.0);
                    //CALCULO DA LATITUDE E LONGITUDE PARA CIDADE DE INDICE j
                    deg = (int) cidades.get(j).getX();
                    min = cidades.get(j).getX() - deg;
                    float latitude_j = (float) ((float) Math.PI * (deg + 5.0 * min / 3.0) / 180.0);                    
                    deg = (int) cidades.get(j).getY();
                    min = cidades.get(j).getY() - deg;
                    float longitude_j = (float) ((float) Math.PI * (deg + 5.0 * min / 3.0) / 180.0);
                    
                    float RRR = (float) 6378.388;                    
                    float q1 = (float) Math.cos(longitude_i - longitude_j);
                    float q2 = (float) Math.cos(latitude_i - latitude_j);
                    float q3 = (float) Math.cos(latitude_i + latitude_j);
                    
                    //CALCULA DISTANCIA ENTRE CIDADE DE INDICE i E CIDADE DE INDICE j
                    int dist = (int) (RRR * Math.acos(0.5*((1.0+q1)*q2 - (1.0-q1)*q3)) + 1.0);
                    
                    matrizAdjacencia[i][j] = dist;//RESTRINGIR PARA 2 CASAS DECIMAIS
                }                
            }
        }
        imprimeMatriz();
    }
    
    public static void imprimeMatriz(){
        System.out.println("Matriz de adjacência:");
        DecimalFormat df = new DecimalFormat("0.0");
        for(int i = 0; i < numeroCidades; i++) {
            System.out.print("\n");
            for (int j = 0; j < numeroCidades; j++) {                    
                System.out.print(df.format(matrizAdjacencia[i][j]) + " ");
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

    public static String getNomeInstancia() {
        return nomeInstancia;
    }

    public static void setNomeInstancia(String nomeInstancia) {
        Instancia.nomeInstancia = nomeInstancia;
    }

    public static float getMelhorSolucaoConhecida() {
        return melhorSolucaoConhecida;
    }

    public static void setMelhorSolucaoConhecida(float melhorSolucaoConhecida) {
        Instancia.melhorSolucaoConhecida = melhorSolucaoConhecida;
    }        
}
