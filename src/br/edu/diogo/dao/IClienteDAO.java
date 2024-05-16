package br.edu.diogo.dao;

import java.util.Collection;

import br.edu.diogo.domain.Cliente;

public interface IClienteDAO {
    public Boolean cadastrarCliente(Cliente cliente);

    public void excluir(Long cpf);

    public void alterar (Cliente cliente);

    public Cliente consultar(Long cpf);

    public Collection<Cliente> buscarTodosClientes();
    
} 
