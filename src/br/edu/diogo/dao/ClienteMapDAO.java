package br.edu.diogo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.edu.diogo.domain.Cliente;

public class ClienteMapDAO implements IClienteDAO {

    private Map<Long, Cliente> listaClientes;

    public ClienteMapDAO() {
        this.listaClientes = new HashMap<>();
    }

    public Boolean cadastrarCliente(Cliente cliente) {
        Long cpf = cliente.getCpf();

        if (this.listaClientes.containsKey(cpf)) {
            return false;
        }
        ;

        this.listaClientes.put(cpf, cliente);
        return true;
    }

    public void excluir(Long cpf) {
        Cliente clienteCadastrado = this.listaClientes.get(cpf);

        if (clienteCadastrado != null) {
            this.listaClientes.remove(cpf, clienteCadastrado);
        }
    }

    public void alterar(Cliente cliente) {
        Long cpf = cliente.getCpf();

        Cliente clienteCadastrado = this.listaClientes.get(cpf);

        if (clienteCadastrado != null) {
            clienteCadastrado.setData(cliente);
        }
    }

    public Cliente consultar(Long cpf) {
        return this.listaClientes.get(cpf);
    }

    public Collection<Cliente> buscarTodosClientes() {
        return listaClientes.values();
    }

}
