package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Dao;
import model.javaBeans;

// TODO: Auto-generated Javadoc
//as requisicoes sao recebidas através de urls 
//configuradas aqui,as urls são caminhos qeu agente define 
/**
 * The Class Controller.
 */
//formularios , botoes...
@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update","/delete","/report"})
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	Dao dao = new Dao();
	
	/** The contato. */
	javaBeans contato = new javaBeans();
	
	
  
    /**
     * Instantiates a new controller.
     */
    public Controller() {
        super();
        
    }

	//metodo principal , o que vou fazer e redirecional as requisicoes que foi configuradas
    /**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//na linha 14 para metodos expecificos .
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//TESTE DE CONEXAO
		//dao.testeConexao();
		//aqui nos temos um teste do Servlet
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//essa variavel guarda o caminho da requisição
		String action = request.getServletPath();
		System.out.println(action);
		if(action.equals("/main")) {
			contatos(request, response);
		}else if (action.equals("/insert")) {
			adicionarContato(request , response);
		}else if (action.equals("/select")) {
			listarContato(request , response);
		}else if (action.equals("/update")) {
			editarContato(request , response);
		}else if (action.equals("/delete")) {
			removerContato(request , response);
		}else if (action.equals("/report")) {
			gerarRelatorio(request , response);
		}else {
			response.sendRedirect("index.html");
		}
		
	}
	
	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Criando um objeto que irá receber os dados javaBeans
		ArrayList<javaBeans> lista = dao.listarContatos();
		
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		// RequestDispatcher é uma classe modelo que trabalha requisições e respostas no ServLet
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		//aqui encaminha o objeto lista ao documento agenda.jsp 
		rd.forward(request, response);
		
		/*Teste de recebimento da lista
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}*/
	}
	
	/**
	 * Adicionar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Novo contato
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response) 
		  throws ServletException, IOException {
		
		/*TESTE DO RECEBIMENTO DOS DADOS DO FORMULÁRIO
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));*/
		
		//setar as variáveis javaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		//invocar o método inserirContato passando o objeto contato
		dao.inserirContato(contato);
		
		//redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}
	
	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response) 
			  throws ServletException, IOException {
		//Recebimento do id do contato que será  editado
		String idcon = request.getParameter("idcon");
		
		// setar a variável JavaBeans
		contato.setIdcon(idcon);
		
		// Executar o método selecionarContato (DAO)
		dao.selecionarContato(contato);
		
		/* teste de recebimento
		System.out.println(contato.getIdcon());
		System.out.println(contato.getNome());
		System.out.println(contato.getFone());
		System.out.println(contato.getEmail());*/
		
		//Setar os atributos do formulário com o conteúdo javaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		//Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) 
			  throws ServletException, IOException {
		/*Teste  de recebimento
		System.out.println(request.getParameter("idcon"));
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));*/
		
		//setar as variáveis javaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		//executar o método alterarContato
		dao.alterarContato(contato);
		// redirecionar para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}
	
	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Remover contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response) 
			  throws ServletException, IOException {
		//RECEBIMENTO DO ID DO CONTATO A SER EXCLUÍDO (VALIDADOR.JS)
		String idcon = request.getParameter("idcon");
		//TESTANDO NO CONSOLE PARA VER SE ESTÁ CHEGANDO O ID
		System.out.println("ESTÁ CHEGANDO O ID" + idcon);
		//SETAR A VARIÁVEL IDCON javaBEANS
		contato.setIdcon(idcon);
		// executar o método deletarContato (DAO) passando o objeto contato
		dao.deletarContato(contato);
		// redirecionar para o documento agenda.jsp (atualizando as alterações)
				response.sendRedirect("main");
		}
	
	/**
	 * Gerar relatorio.
	 *
	 * @param solicitacao the solicitacao
	 * @param resposta the resposta
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//gerar relatorio
	public void gerarRelatorio(HttpServletRequest solicitacao, HttpServletResponse resposta) throws ServletException, IOException {
	    Document documento = new Document();
	    PdfWriter writer = null;
	    try {
	        // Definir o tipo de conteúdo
	        resposta.setContentType("application/pdf");
	        // Definir o nome do documento
	        resposta.addHeader("Content-Disposition", "inline; filename=contatos.pdf");

	        // Criar o escritor do PDF
	        writer = PdfWriter.getInstance(documento, resposta.getOutputStream());

	        // Abrir o documento e gerar o conteúdo
	        documento.open();
	        documento.add(new Paragraph("Lista de contatos:"));
	        // isso quebra uma linha no documento pdf
	        documento.add(new Paragraph(" "));
	        //criar uma tabela -  aqui cria três colunas PdfPTable(3);
	        PdfPTable tabela = new PdfPTable(3);
	        //cabeçalho
	        PdfPCell col1 = new PdfPCell(new Paragraph("nome"));
	        PdfPCell col2 = new PdfPCell(new Paragraph("fone"));
	        PdfPCell col3 = new PdfPCell(new Paragraph("email"));
	        tabela.addCell(col1);
	        tabela.addCell(col2);
	        tabela.addCell(col3);
	        //popular a tabela com os contatos
	        ArrayList<javaBeans> lista = dao.listarContatos();
	        for(int i = 0 ; i < lista.size(); i++) {
	        	tabela.addCell(lista.get(i).getNome());
	        	tabela.addCell(lista.get(i).getFone());
	        	tabela.addCell(lista.get(i).getEmail());
	        }
	        documento.add(tabela);
	        documento.close();
	    } catch (Exception e) {
	        System.out.println("Erro ao gerar o PDF: " + e.getMessage());
	    } finally {
	        // Certifique-se de fechar o documento e o OutputStream
	        if (documento.isOpen()) {
	            documento.close();
	        }
	        if (writer != null) {
	            writer.flush();
	            writer.close();
	        }
	    }
	}
}

