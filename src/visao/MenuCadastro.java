package visao;

/**
 * Objetivo: Cadastrar uma nova pessoa
 * Entradas: Nome, CPF, Data Nascimento, Sexo, Quantidade de vacinas tomadas. 
 */

import javax.swing.*;

import servicos.Validacao;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import dados.ColecaoPessoas;
import dados.Maior_de_idade;
import dados.Menor_de_idade;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MenuCadastro {
	private static JFrame  frameCadastro;
	private static JTextField textField_Nome;
	private static JTextField textField_CPF;
	private static JTextField textField_Data;
	
	/*Metodo Construtor da classe*/
	public MenuCadastro() {
		menuCadastro();
	}
	
	/*Inicializacao dos conteudos da frame*/
	
	public static void menuCadastro(){
		/*set da janela de cadastro*/
		frameCadastro = new JFrame();
		frameCadastro.setBounds(300, 200, 600, 300);
		frameCadastro.setTitle("Cadastro de um novo registro de vacinacao");
		frameCadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameCadastro.getContentPane().setLayout(null);
		/*Label inicial*/
		JLabel tituloCadastroPessoa = DefaultComponentFactory.getInstance().createTitle("Preencha os campos abaixo com os dados solicitados.");
		tituloCadastroPessoa.setBounds(15, 0, 400, 30);
		frameCadastro.getContentPane().add(tituloCadastroPessoa);
		
		/*Set dos fields para realizar cadastro*/
		/*Field nome*/
		JLabel labelNome = DefaultComponentFactory.getInstance().createLabel("NOME: ");
		labelNome.setVerticalAlignment(SwingConstants.TOP);
		labelNome.setHorizontalAlignment(SwingConstants.RIGHT);
		labelNome.setToolTipText("Nome da pessoa a ser cadastrada");
		labelNome.setBounds(15, 30, 50, 15);
		frameCadastro.getContentPane().add(labelNome);
		textField_Nome = new JTextField();
		labelNome.setLabelFor(textField_Nome);
		textField_Nome.setBounds(15, 50, 300, 30);
		frameCadastro.getContentPane().add(textField_Nome);
		textField_Nome.setColumns(10);

		/*Field CPF*/
		JLabel labelCPF = DefaultComponentFactory.getInstance().createLabel("CPF: ");
		labelCPF.setBounds(15, 90, 130, 15);
		frameCadastro.getContentPane().add(labelCPF);
		textField_CPF = new JTextField();
		textField_CPF.setText("###.###.###-##");
		textField_CPF.setBounds(15, 110, 150, 30);
		frameCadastro.getContentPane().add(textField_CPF);
		textField_CPF.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				textField_CPF.setText("");

			}
		});
		textField_CPF.setColumns(10);

		/*Field data nascimento*/
		JLabel labelData = DefaultComponentFactory.getInstance().createLabel("DATA DE NASCIMENTO: ");
		labelData.setBounds(15, 150, 170, 15);
		frameCadastro.getContentPane().add(labelData);
		textField_Data = new JTextField();

		textField_Data.setText("dd/mm/aaaa");
		textField_Data.setColumns(10);
		textField_Data.setBounds(15, 170, 150, 30);
		textField_Data.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				textField_Data.setText("");
			}
		});
		frameCadastro.getContentPane().add(textField_Data);

		/*Field sexo*/
		JLabel labelSexo = DefaultComponentFactory.getInstance().createLabel("SEXO:");
		labelSexo.setBounds(15, 210, 130, 15);
		frameCadastro.getContentPane().add(labelSexo);
		/*botoes de sexo*/
		ButtonGroup grupo = new ButtonGroup();
		final JRadioButton buttonFeminino = new JRadioButton("Feminino");
		buttonFeminino.setBounds(15, 230, 149, 23);
		frameCadastro.getContentPane().add(buttonFeminino);

		final JRadioButton buttonMasculino = new JRadioButton("Masculino");
		buttonMasculino.setBounds(195, 230, 149, 23);
		frameCadastro.getContentPane().add(buttonMasculino);
		frameCadastro.setVisible(true);

		grupo.add(buttonFeminino);
		grupo.add(buttonMasculino);
		
		/*labels de erros com icones*/
		final JLabel erroNome = new JLabel("Favor preencher o campo");
		erroNome.setToolTipText("Campo de nome vazio");
		erroNome.setIcon(new ImageIcon(MenuCadastro.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		erroNome.setBounds(333, 50, 239, 30);
		erroNome.setVisible(false);
		frameCadastro.getContentPane().add(erroNome);

		final JLabel erroCPF = new JLabel("CPF invalido, digite um numero valido");
		erroCPF.setIcon(new ImageIcon(MenuCadastro.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		erroCPF.setBounds(187, 110, 300, 50);
		erroCPF.setVisible(false);
		frameCadastro.getContentPane().add(erroCPF);

		final JLabel erroDATA = new JLabel("Data invalida, digite novamente");
		erroDATA.setIcon(new ImageIcon(MenuCadastro.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		erroDATA.setBounds(197, 159, 300, 50);
		erroDATA.setVisible(false);
		frameCadastro.getContentPane().add(erroDATA);

		final JLabel erroSEXO = new JLabel("Campo obrigatorio");
		erroSEXO.setIcon(new ImageIcon(MenuCadastro.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		erroSEXO.setBounds(75, 210, 250, 20);
		erroSEXO.setVisible(false);
		frameCadastro.getContentPane().add(erroSEXO);

		/*Botao de Enviar para dar salvar cadastro*/
		JButton buttonOk = new JButton("Enviar");
		buttonOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				boolean statusValidacao = false;
				ColecaoPessoas colecao = new ColecaoPessoas();
				String nomePessoa, numCPF, dataNascimento;
				Character sexo = null;

				/*Obtendo os valores*/
				nomePessoa = textField_Nome.getText().trim();
				numCPF = textField_CPF.getText().trim();
				dataNascimento = textField_Data.getText().trim();
				if(buttonFeminino.isSelected())
					sexo = 'F';
				if(buttonMasculino.isSelected())
					sexo = 'M';
				
				/*VALIDA NOME*/
				if(Validacao.validaNome(nomePessoa) == false){
					statusValidacao = false;
					erroNome.setVisible(true);
				}
				else if(Validacao.validaNome(nomePessoa)){
					erroNome.setVisible(false);
					statusValidacao = true;
				}
				/*VALIDA CPF*/
				if(Validacao.validaCPF(numCPF) == false){
					statusValidacao = false;
					erroCPF.setVisible(true);
				}
				else if(Validacao.validaCPF(numCPF)){
					erroCPF.setVisible(false);
					statusValidacao = true;
				}
				/*VALIDA DATA*/
				if(Validacao.validaData(dataNascimento) == false){
					statusValidacao = false;
					erroDATA.setVisible(true);
				}
				else if(Validacao.validaData(dataNascimento)){
					statusValidacao = true;
					erroDATA.setVisible(false);
				}
				/*VALIDA SEXO*/
				if(!buttonFeminino.isSelected() &&  !buttonMasculino.isSelected()){
					statusValidacao = false;
					erroSEXO.setVisible(true);
				}
				else if(buttonFeminino.isSelected() || buttonMasculino.isSelected()){
					statusValidacao = true;
					erroSEXO.setVisible(false);
				}
				
				/*Menu maior ou menor de idade*/
				if(statusValidacao == true){
					String yearSubstring = dataNascimento.substring(6);
					Integer ano = Integer.parseInt(yearSubstring);
					boolean foiVacinada;
					Integer quantVacinas;
					if(2016-ano >= 18){
						/*Se for maior de idade*/
						frameCadastro.setVisible(false);
						MenuMaiorIdade JanelaMenuMaiorIdade = new MenuMaiorIdade();
						foiVacinada  = JanelaMenuMaiorIdade.getFoiVacinada();
						Maior_de_idade pessoaMaior = new Maior_de_idade(nomePessoa, sexo, numCPF, dataNascimento, foiVacinada);
						colecao.setPessoas(pessoaMaior);
					}
					else if(2016-ano <= 18){
						/*Se for menor de idade*/
						frameCadastro.setVisible(false);
						MenuMenorIdade JanelaMenuMenorIdade  = new MenuMenorIdade();
						quantVacinas = JanelaMenuMenorIdade.getQuantVezes();
						Menor_de_idade pessoaMenor = new Menor_de_idade(nomePessoa, sexo, numCPF, dataNascimento, quantVacinas);
						colecao.setPessoas(pessoaMenor);						
					}
				}
			}
		});
		buttonOk.setBounds(469, 205, 117, 25);
		frameCadastro.getContentPane().add(buttonOk);
	}
}