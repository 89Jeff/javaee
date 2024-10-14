package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Dao.
 */
public class Dao {
	
	/** Modulo de conexao*. */
	
	//Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user =  "root";
	
	/** The password. */
	private String password = "Senha@2020";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Metodo de conexÃ£o
	private Connection conectar() {
		Connection con = null;
			
		try {
			//VAI LER O DRIVER DO BANCO DE DADOS
			Class.forName(driver);
			con = DriverManager.getConnection(url, user , password);
			return con;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	/**
	 * Teste conexao.
	 */
	//TESTE DE CONEXAO
	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 *   CRUD CREATE *.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(javaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values(?,?,?)";
		try {
			//abrir a conexão
			Connection con = conectar();
			// Preparar a query para execução no banco de dados
			
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parâmetros (?) pelo conteúdo das variáveis javaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			//Executar a query
			pst.executeUpdate();
			
			//Encerrar a conexão com o banco
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
				
	}
	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	// CRUD READ fazer a listagem de todos usuários
	public ArrayList<javaBeans> listarContatos() {
		
		//Criando objeto para acessar a classe javaBeans
		ArrayList<javaBeans> contatos = new ArrayList<>();
		
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			// esse comando é usado para fazer o retorno de um banco de dados temporariamente.
			//ou seja a seleção de todos os contatos do banco de dados é armazenado temporario nessa variavel
			ResultSet rs = pst.executeQuery();
			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				//variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				//populando o ArrayList
				contatos.add(new javaBeans(idcon,nome,fone,email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 *  CRUD UPDATE*.
	 *
	 * @param contato the contato
	 */
	//SELECIONAR O CONTATO
	public void selecionarContato(javaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con =  conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				//setar as variaveis javaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	//editar o contato
	public void alterarContato(javaBeans contato) {
		String create = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	/* CRUD DELETE*/
	public void deletarContato(javaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println("qual é o id" + e);
		}
	}
	
}

