/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.jdbc;

import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class TestarConexao {
    
    public static void main(String[] args) {
        try {
            new ConexaoBanco().pegarConexao();
            JOptionPane.showMessageDialog(null, "conectado com sucesso ao banco de dados");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "erro ao se conectar ao banco de dados:" + erro.getMessage());
        }
    }
    
}
