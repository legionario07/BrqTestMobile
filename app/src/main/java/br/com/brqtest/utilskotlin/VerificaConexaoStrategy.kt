package br.com.brqtest.utilskotlin

import android.content.Context
import android.net.ConnectivityManager


/**
 * Strategy verifica a conexão com a internet
 */
object VerificaConexaoStrategy {

    /**
     * Verifica se há conexão com a internet
     * @param context
     * @return - true se houver conexão
     */
    fun verificarConexao(context: Context): Boolean {
        val conectado: Boolean
        val conectivtyManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (conectivtyManager.activeNetworkInfo != null
            && conectivtyManager.activeNetworkInfo.isAvailable
            && conectivtyManager.activeNetworkInfo.isConnected
        ) {
            conectado = true
        } else {
            conectado = false
        }
        return conectado
    }

}
