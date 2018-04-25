package Testes;

import model.Cliente;
import model.Endereco;
import model.Livro;
import model.ProblemaTroca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import service.ClienteService;
import service.EnderecoService;
import service.LivroService;
import service.ProblemaTrocaService;
import service.SolicitacaoService;
import service.TrocaService;

public class Teste {
	
	public static void main(String[] args) {
		
		DAOCliente cli = new DAOCliente();
		DAOLivro daoliv = new DAOLivro();
		ClienteService cliServe = new ClienteService();
		EnderecoService endServe = new EnderecoService();
		LivroService livroService = new LivroService();
		SolicitacaoService soliService = new SolicitacaoService();
		TrocaService trocaService = new TrocaService();
		ProblemaTrocaService problemaService = new ProblemaTrocaService();
		
		Cliente cliente = new Cliente();
		cliente.setLogin("Patricia_Silva");
		cliente.setNome("Janeide Estandeslau da Silva");
		cliente.setSenha("50jeipb08d");
		cliente.setId((long) 3);
		cliente.setPonto(1);
		
//		Cliente cliente2 = new Cliente();
//		cliente2.setLogin("Patricia_Cabral");
//		cliente2.setNome("Patricia SIlva Cabral");
//		cliente2.setSenha("50jeipb08d");
//		cliente2.setId((long) 3);
//		cliente2.setPonto(1);
		
		
//		Livro livro = new Livro();
//		livro.setId((long) 1);
//		livro.setConservacao("òtimo estado");
//		livro.setEditora("SAraiva");
//		livro.setIsbn("9788561559342");
//		livro.setPublicacao(2000);
//		livro.setSinopse("Júlio César (Dan Stulbach), um psicólogo decepcionado.");
//		livro.setTitulo("O vendedor de sonhos");
		
		
//		Livro livro1 = new Livro();
//		livro1.setConservacao("òtimo estado");
//		livro1.setEditora("SAraiva");
//		livro1.setIsbn("455410456");
//		livro1.setPublicacao(2000);
//		livro1.setSinopse("sefhhjbdg");
//		livro1.setTitulo("A mulher do Viajante no tempo");
		
		
//		Endereco end = new Endereco();
//		end.setId((long) 1);
//		end.setBairro("Centro");
//		end.setCep("58500-000");
//		end.setCidade("Santana dos Garrotes");
//		end.setEstado("Paraíba");
//		end.setNumero("S/N");
//		end.setRua("Destrito de Pitombeira de Dentro");
		
		
//		ProblemaTroca problema = new ProblemaTroca();
//		problema.setDescricaoProblema("lllllllllllllkkkkkkkkkkkk");
	
		
		try {
			
//			problemaService.registrarProblemaTroca((long) 1, (long) 2, problema);
//			problemaService.problemaResolvido((long) 4);
			
//			livroService.salvarLivro(livro);
//			livroService.salvarLivro(livro1);
			
//			cliServe.adicionaLivroPossuintes((long) 1, (long) 1);
			
			
//			soliService.solicitarLivro((long) 2, (long) 1);
//			soliService.aceitarSolicitacao((long) 1);
			
			cliServe.salvarUsuario(cliente);
//			cliServe.salvarUsuario(cliente2);
			
//			endServe.editarEndereco(end);
//			livroService.modificarLivro(livro);
//			cliServe.modificarUsuario(cliente);
//			cliServe.removerUsuario((long) 1);
//			cliServe.removerLivroPossuintes((long) 1, (long) 1);
//			cliServe.removerLivroListaDesejos((long) 1, (long) 1);
//			endServe.cadastrarEndereco(end, (long) 1);
//			endServe.editarEndereco((long) 1);
//			cliServe.adicionarLivroListaDesejos((long) 1, (long) 1);
//			cli.save(cliente);
//			daoliv.save(livro1);
//			cliServe.cancelarSolicitacao((long) 1);
//			trocaService.cancelarTroca((long) 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cli.close();
		}
	}

}
