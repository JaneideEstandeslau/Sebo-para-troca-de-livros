package Testes;

import model.Cliente;
import model.Endereco;
import model.Livro;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import service.ClienteService;
import service.EnderecoService;
import service.LivroService;

public class Teste {
	
	public static void main(String[] args) {
		
		DAOCliente cli = new DAOCliente();
		DAOLivro daoliv = new DAOLivro();
		ClienteService cliServe = new ClienteService();
		EnderecoService endServe = new EnderecoService();
		LivroService livroService = new LivroService();
		
		Cliente cliente = new Cliente();
		cliente.setLogin("Janeide Estandeslau");
		cliente.setNome("Janeide");
		cliente.setSenha("wjfkjjreg");
		cliente.setPonto(1);
		
		
//		Livro livro = new Livro();
//		livro.setConservacao("òtimo estado");
//		livro.setEditora("SAraiva");
//		livro.setIsbn("4554100");
//		livro.setPublicacao(2000);
//		livro.setSinopse("sefhhjbdg");
//		livro.setTitulo("O vendedor de sonhos");
		
		
		Livro livro1 = new Livro();
		livro1.setConservacao("òtimo estado");
		livro1.setEditora("SAraiva");
		livro1.setIsbn("455410456");
		livro1.setPublicacao(2000);
		livro1.setSinopse("sefhhjbdg");
		livro1.setTitulo("O");
		
		
//		Endereco end = new Endereco();
//		end.setBairro("Centro");
//		end.setCep("58500-000");
//		end.setCidade("Monteiro");
//		end.setEstado("Paraíba");
//		end.setNumero("122");
//		end.setRua("Severino Ramos Residencial Dona Socorro");
		try {

//			cliServe.removerUsuario((long) 1);
//			cliServe.removerLivroPossuintes((long) 1, (long) 1);
//			cliServe.removerLivroListaDesejos((long) 1, (long) 1);
//			cliServe.adicionaLivroPossuintes((long) 1, (long) 1);
//			endServe.cadastrarEndereco(end, (long) 1);
//			endServe.editarEndereco((long) 1);
//			livroService.salvarLivro(livro1);
//			cliServe.adicionarLivroListaDesejos((long) 1, (long) 1);
//			cliServe.salvarUsuario(cliente);
//			cli.save(cliente);
//			daoliv.save(livro1);
//			cliServe.solicitarLivro((long) 3, (long) 1);
//			cliServe.aceitarSolicitacao((long) 3);
//			cliServe.cancelarSolicitacao((long) 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cli.close();
		}
	}

}
