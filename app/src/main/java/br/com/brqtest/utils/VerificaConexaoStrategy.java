package br.com.brqtest.utils;

import android.content.Context;
import android.net.ConnectivityManager;


/**
 * Strategy verifica a conexão com a internet
 */
public class VerificaConexaoStrategy {

    /**
     * Verifica se há conexão com a internet
     * @param context
     * @return - true se houver conexão
     */
    public static boolean verificarConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
