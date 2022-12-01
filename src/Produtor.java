import model.Noticia;

public class Produtor extends Thread{
    Noticia noticias;

    public Produtor(Noticia noticias){
        this.noticias = noticias;
    }

    @Override
    public void run(){
        while(true) {
            noticias.produzir(this.getName());
            try {
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
