import model.Noticia;

public class Consumidor extends Thread{
    Noticia noticias;

    public Consumidor(Noticia noticias){
        this.noticias = noticias;
    }

    @Override
    public void run(){
        while(true) {
            this.noticias.consumir(this.getName());
            try {
                sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
