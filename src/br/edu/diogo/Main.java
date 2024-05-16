package br.edu.diogo;

import javax.swing.JOptionPane;

import br.edu.diogo.dao.ClienteMapDAO;
import br.edu.diogo.dao.IClienteDAO;
import br.edu.diogo.domain.Cliente;

import java.util.*;

@FunctionalInterface
interface Acao {
    void executar(String entrada);
}

public class Main {
    private static IClienteDAO iClienteDAO;

    static List<String> opcoesValidas = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
    static Map<String, String> opcoes = new HashMap<>() {
        {
            put("1", "cadastro");
            put("2", "consulta");
            put("3", "excluir");
            put("4", "alterar");
            put("5", "sair");
        }
    };

    static Map<String, Acao> acoes = new HashMap<>();

    public static void main(String[] args) throws Exception {
        iClienteDAO = new ClienteMapDAO();

        // Inicializando o mapa de ações
        acoes.put("1", entrada -> cadastrarAcao());
        acoes.put("2", entrada -> consultarAcao());
        acoes.put("3", entrada -> excluirAcao());
        acoes.put("4", entrada -> alterarAcao());
        acoes.put("5", entrada -> sair());

        String opcao = JOptionPane.showInputDialog(null,
                "Digite 1 para cadastro, 2 para consultar, 3 para exclusão, 4 para alteração ou 5 para sair",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        while (opcao != null) {
            System.out.println(opcao);

            if (isOpcaoValida(opcao)) {
                Acao acao = acoes.get(opcao);
                acao.executar(opcao);
            }

            else if (!isOpcaoValida(opcao)) {
                JOptionPane.showMessageDialog(null, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            opcao = JOptionPane.showInputDialog(null,
                    "Digite 1 para cadastro, 2 para consulta, 3 para exclusão, 4 para alteração ou 5 para sair",
                    "Green dinner", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static boolean isOpcaoValida(String opcao) {
        return opcoesValidas.contains(opcao);
    }

    private static void cadastrarAcao() {
        String dados = JOptionPane.showInputDialog(null,
                        "Digite os dados do cliente separados por vígula, conforme exemplo: Nome, CPF, Telefone, Endereço, Número, Cidade e Estado",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        cadastrar(dados);
    }

    private static void consultarAcao() {
        String cpf = JOptionPane.showInputDialog(null,
                "Digite o cpf",
                "Consultar", JOptionPane.INFORMATION_MESSAGE);
        consultar(cpf);
    }

    private static void excluirAcao() {
        String cpf = JOptionPane.showInputDialog(null,
                "Digite o CPF do cliente para excluir",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        excluir(cpf);
    }

    private static void alterarAcao() {
        String dados = JOptionPane.showInputDialog(null,
                        "Digite os dados do cliente separados por vígula, conforme exemplo: Nome, CPF, Telefone, Endereço, Número, Cidade e Estado",
                        "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        alterar(dados);
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Saindo do sistema...", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static void cadastrar(String dados) {
        String[] dadosSeparados = dados.split(",");

        Cliente cliente = new Cliente(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
        
        Boolean isCadastrado = iClienteDAO.cadastrarCliente(cliente);

        if (isCadastrado) {
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso ", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já se encontra cadastrado", "Erro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void alterar(String dados) {

        try {
            String[] dadosSeparados = dados.split(",");
            Cliente cliente = new Cliente(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
            iClienteDAO.alterar(cliente);
            
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso ", "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cliente nao cadastrado", "Erro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        
            
        
    }

    private static void consultar(String cpf) {
        Cliente cliente = iClienteDAO.consultar(Long.parseLong(cpf));
        if (cliente != null) {
            JOptionPane.showMessageDialog(null, "Cliente encontrado: " + cliente.toString(), "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado: ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void excluir(String cpf) {
        try {
            iClienteDAO.excluir(Long.parseLong(cpf));
            JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Cliente não encontrado: ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}