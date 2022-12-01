import model.Noticia;

public class Main {
    public static void main(String[] args) throws Exception {

        // Quantidade de Threads que iram realizar o papel de consumidor e produtor
        int quantitadeConsumidores = 10;
        int quantitadeProdutores = 1;


        // Recurso em comum
        Noticia noticias = new Noticia();


        // Instancialização e inicialização das threads produtoras 
        for (int i = 0; i < quantitadeProdutores; i++) {
            Produtor produtor = new Produtor(noticias);
            produtor.start();
        }

        // Instancialização e inicialização das threads consumidoras
        for (int i = 0; i < quantitadeConsumidores; i++) {
            Consumidor consumidor = new Consumidor(noticias);
            consumidor.start();
        }

    }
}
