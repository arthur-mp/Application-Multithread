package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Noticia {
    // Array de Dados
    private StringBuffer[] noticias;
    // Limite do array
    private int limite = 10;
    // Quantidade de dados presente no array
    private int quantidadeNoticias = 0;
    // Indice da proxima posição a ser consumida
    private int proximaNoticiaConsumir = 0;
    // Indice da proxima posição a ser produzida
    private int proximaNoticiaProduzir = 0;

    public Noticia() {
        this.noticias = new StringBuffer[limite];
    }

    public synchronized void consumir(String idThread) {

        while (this.quantidadeNoticias == 0) {
            try {
                // Coloca as demais threads consumidoras a dormir
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Consome a noticia
        System.out.println("Consumidor \t" + idThread + "\t consumiu a noticia de número: \t" + proximaNoticiaConsumir);
        this.noticias[proximaNoticiaConsumir] = null;
        quantidadeNoticias--;

        // Atualiza o indice da proxima notica a ser consumida
        if (proximaNoticiaConsumir + 1 == limite)
            proximaNoticiaConsumir = 0;
        else
            proximaNoticiaConsumir++;

        // Notifica as demais threas 
        notifyAll();
    }

    public synchronized void produzir(String idThread) {
        while (quantidadeNoticias == limite) {
            try {
                // Coloca as threads produtoras para dormir
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Gera uma nova noticia
        StringBuffer novaNoticia = gerarNovaNoticia();

        noticias[proximaNoticiaProduzir] = novaNoticia;

        System.out.println("Produtor \t" + idThread + "\t produziu a noticia de número: \t" + proximaNoticiaProduzir);

        // Atualiza o indice que implica na proxima posição 
        // a ser utilizada para armazenar a nova noticia
        if (proximaNoticiaProduzir + 1 == limite)
            proximaNoticiaProduzir = 0;
        else
            proximaNoticiaProduzir++;

        quantidadeNoticias++;


        // Notifica as demais threads 
        notifyAll();

    }

    // Realiza a leitura de um arquivo TXT
    // Gera um StringBuffer
    private StringBuffer gerarNovaNoticia() {
        File file = new File("src/util/Texto.txt");
        StringBuffer texto = new StringBuffer();
        try {
            file.createNewFile();

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (bufferedReader.ready()) {
                texto.append(bufferedReader.readLine() + "\n");
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException erro) {
            System.out.printf("Erro: %s", erro.getMessage());
        }
        return texto;
    }
}
