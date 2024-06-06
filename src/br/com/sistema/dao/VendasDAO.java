/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class VendasDAO {
    private Connection conn;
    
    public VendasDAO(){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void salvar(Vendas obj){
        try {
            String sql = "insert into tb_vendas (cliente_id,data_venda,total_venda,observacoes) values(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getClientes().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObservacoes());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a venda: "+e);
        }
    }
    
    public int retornaUltimoIdVenda(){
        try {
            int ultimoId = 0;
            String sql = "select max(id) id from tb_vendas";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vendas v = new Vendas();
                v.setId(rs.getInt("id"));
                ultimoId = v.getId();
            }
            return ultimoId;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao retornar o último ID da venda!");
        }
    }
    
    public List<Vendas>historicoVendas(LocalDate data_inicio, LocalDate data_fim){
        try {
            List<Vendas>lista = new ArrayList<>();
            String sql = "select v.id,c.nome,date_format(v.data_venda, '%d/%m/%Y') as data_formatada,v.total_venda,v.observacoes from tb_vendas as v inner join tb_clientes as c on(v.cliente_id=c.id) where v.data_venda between ? and ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vendas v = new Vendas();
                Clientes c = new Clientes();
                v.setId(rs.getInt("v.id"));
                c.setNome(rs.getString("c.nome"));
                v.setClientes(c);
                v.setData_venda(rs.getString("data_formatada"));
                v.setTotal_venda(rs.getDouble("v.total_venda"));
                v.setObservacoes(rs.getString("v.observacoes"));
                lista.add(v);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar o histórico de vendas!" +e);
        }
    }
}
