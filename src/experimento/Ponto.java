package experimento;

/** @author Jancleiton Nunes */

//CLASSE PARA REPRESENTAR OS PONTOS PARA A INSTANCIA TIPO EUC_2D E GEO
public class Ponto {
    private int cidade;
    private float x;
    private float y;
    
    public Ponto(){        
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getCidade() {
        return cidade;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }    

    @Override
    public String toString() {
        return "Ponto{" + "cidade=" + cidade + ", x=" + x + ", y=" + y + '}';
    }        
    
}
