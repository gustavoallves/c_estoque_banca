/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Fornecedores;
import br.com.sistema.model.Funcionarios;
import br.com.sistema.view.AreaTrabalho;
import br.com.sistema.view.FormularioLogin;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class FornecedoresDAO {
    private Connection conn;
    
    public FornecedoresDAO(){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    
    public void Salvar(Fornecedores obj){
        try {
            // criar o sql
            String sql = "insert into tb_fornecedores(nome, cnpj, email, telefone, celular, cep, endereco, numero, complemento, bairro, cidade, estado)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            
            // preparar conexao sql
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,obj.getNome());
            stmt.setString(2,obj.getCnpj());
            stmt.setString(3,obj.getEmail());
            stmt.setString(4,obj.getTelefone());
            stmt.setString(5,obj.getCelular());
            stmt.setString(6,obj.getCep());
            stmt.setString(7,obj.getEndereco());
            stmt.setInt(8,obj.getNumero());
            stmt.setString(9,obj.getComplemento());
            stmt.setString(10,obj.getBairro());
            stmt.setString(11,obj.getCidade());
            stmt.setString(12,obj.getEstado());
            
            // executar sql
            stmt.execute();
            
            // Fechar conexao
            stmt.close();
            JOptionPane.showMessageDialog(null, "Fornecedor salvo com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o fornecedor: " + erro);
        }
    }
    
    public void Editar(Fornecedores obj){
        try {
            // criar o sql
            String sql = "update tb_fornecedores set nome=?,cnpj=?,email=?,telefone=?,"
                    + "celular=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,estado=? where id=?";
            
            // preparar conexao sql
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,obj.getNome());
            stmt.setString(2,obj.getCnpj());
            stmt.setString(3,obj.getEmail());
            stmt.setString(4,obj.getTelefone());
            stmt.setString(5,obj.getCelular());
            stmt.setString(6,obj.getCep());
            stmt.setString(7,obj.getEndereco());
            stmt.setInt(8,obj.getNumero());
            stmt.setString(9,obj.getComplemento());
            stmt.setString(10,obj.getBairro());
            stmt.setString(11,obj.getCidade());
            stmt.setString(12,obj.getEstado());
            stmt.setInt(13,obj.getId());
            
            // executar sql
            stmt.execute();
            
            // Fechar conexao
            stmt.close();
            JOptionPane.showMessageDialog(null, "Fornecedor editado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o fornecedor: " + erro);
        }
    }
    
    public void Excluir(Fornecedores obj){
        try {
            String sql = "delete from tb_fornecedores where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Fornecedor excluido com sucesso!");

        } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir o fornecedor: " + erro);
        }
    }
    
    public Fornecedores BuscarFornecedor(String nome){
        try {
            String sql = "select * from tb_fornecedores where nome =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,nome);
            ResultSet rs = stmt.executeQuery();
            Fornecedores obj = new Fornecedores();
            if(rs.next()){
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));  
            }
            return obj;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar fornecedor: " + erro);
        }
        return null;
    }
    
    public List<Fornecedores>Listar(){
        List<Fornecedores> lista = new ArrayList<>();
        try {
            String sql = "select  * from tb_fornecedores";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Fornecedores obj = new Fornecedores();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao criar lista: " + erro);
        }
        return null;
    }
    
    public List<Fornecedores>Filtrar(String nome){
        List<Fornecedores> lista = new ArrayList<>();
        try {
            String sql = "select  * from tb_fornecedores where nome like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,nome);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Fornecedores obj = new Fornecedores();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao criar lista: " + erro);
        }
        return null;
    }
    
    public void efetuarLogin(String email, String senha){
        try {
            String sql = "select * from tb_funcionarios where email=? and senha=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Seja bem vindo ao sistema!");
                AreaTrabalho at = new AreaTrabalho();
                at.setVisible(true);
            }else{
                FormularioLogin login = new FormularioLogin();
                JOptionPane.showMessageDialog(null, "Dados inválidos!");
                login.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro:!" + e);

        }
    }
}
