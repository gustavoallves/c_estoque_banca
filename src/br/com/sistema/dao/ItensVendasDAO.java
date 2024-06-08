/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.ItensVendas;
import br.com.sistema.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author crist
 */
public class ItensVendasDAO {
    private Connection conn;
    
    public ItensVendasDAO(){
        this.conn = new ConexaoBanco().getConexao();
    }
    
    public void salvar(ItensVendas obj){
        try {
            String sql = "insert into tb_itensvendas (venda_id,produto_id,qtd,subtotal) values(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,obj.getVendas().getId());
            stmt.setInt(2,obj.getProdutos().getId());
            stmt.setInt(3,obj.getQtd());
            stmt.setDouble(4,obj.getSubtotal());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar os itens da minha venda!");
        }
    }
    
    public List<ItensVendas>listaItens(int venda_id){
        try {
            List<ItensVendas>lista = new ArrayList<>();
            String sql = "select p.id,p.descricao,i.qtd,p.preco,i.subtotal from tb_itensvendas as i inner join tb_produtos as p on(i.produto_id=p.id) where i.venda_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, venda_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ItensVendas item = new ItensVendas();
                Produtos p = new Produtos();
                p.setId(rs.getInt("p.id"));
                item.setProdutos(p);
                p.setDescricao(rs.getString("p.descricao"));
                item.setProdutos(p);
                item.setQtd(rs.getInt("i.qtd"));
                p.setPreco(rs.getDouble("p.preco"));
                item.setProdutos(p);
                item.setSubtotal(rs.getInt("i.Subtotal"));
                lista.add(item);
            }
            return lista;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar a lista de itens! " +e);
        }
    }
}
