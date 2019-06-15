package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import model.Cidade;
import utils.SpringUtilities;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<Cidade> cidades = new ArrayList<>();

	private class CidadesListModel extends AbstractListModel<Cidade> {

		private static final long serialVersionUID = 1L;

		@Override
		public Cidade getElementAt(int index) {
			return cidades.get(index);
		}

		@Override
		public int getSize() {
			return cidades.size();
		}

		public void add(Cidade cidade) {
			cidades.add(cidade);
			fireIntervalAdded(cidade, cidades.size() - 1, cidades.size() - 1);
		}

		public void set(int indexOfCidade, Cidade cidade) {
			cidades.set(indexOfCidade, cidade);
			fireContentsChanged(cidade, indexOfCidade, indexOfCidade);
		}

	}

	private JTextField tfNome;
	private JTextField tfCep;
	private JTextField tfUf;
	private JButton ok;
	private JList<Cidade> lista;
	private CidadesListModel model;
	private int indexOfCidade = -1;
	private Connection con;

	private PreparedStatement psUpdate;

	private PreparedStatement psInsert;

	public Tela() throws Exception {
		setTitle("Cadastro de cidades");
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponents();

	}

	private void initComponents() {

		/**** campos descricao e preco ****/
		JPanel jp1 = new JPanel();
		jp1.setLayout(new SpringLayout());

		this.tfNome = new JTextField();
		this.tfCep = new JTextField();
		this.tfUf = new JTextField();

		jp1.add(new JLabel("Nome :"));
		jp1.add(tfNome);

		jp1.add(new JLabel("Cep :"));
		jp1.add(tfCep);

		jp1.add(new JLabel("UF :"));
		jp1.add(tfUf);

		// apenas para ajeitar a posicao dos componentes na tela
		SpringUtilities.makeCompactGrid(jp1, 2, 2, 3, 3, // initX, initY
				3, 3); // xPad, yPad

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(jp1, BorderLayout.CENTER);

		/**** botao salvar ****/
		JPanel jp2 = new JPanel();
		this.ok = new JButton("Salvar");
		jp2.add(ok);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				try {
					if (indexOfCidade == -1) {
						Cidade c = new Cidade(tfNome.getText(), Integer.parseInt(tfCep.getText()), tfUf.getText());
						model.add(c);

						psInsert.setString(1, c.getNome());
						psInsert.setInt(2, c.getCep());
						psInsert.setString(3, c.getUf());
					} else {
						Cidade c = cidades.get(indexOfCidade);
						c.setNome(tfNome.getText());
						c.setCep(Integer.parseInt(tfCep.getText()));
						model.set(indexOfCidade, c);
						indexOfCidade = -1;

						psUpdate.setString(1, c.getNome());
						psUpdate.setDouble(2, c.getCep());
						psUpdate.setString(3, c.getUf());
					}

					tfNome.setText("");
					tfCep.setText("");
					tfUf.setText("");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		jp.add(jp2, BorderLayout.SOUTH);

		add(jp, BorderLayout.NORTH);

		/**** JList ****/
		this.model = new CidadesListModel();
		this.lista = new JList<Cidade>(this.model);
		this.lista.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // duplo clique
					// indexOfProduto = lista.locationToIndex(e.getPoint());
					indexOfCidade = lista.getSelectedIndex();
					Cidade c = cidades.get(indexOfCidade);

					tfNome.setText(c.getNome());
					tfCep.setText(c.getCep() + "");
					tfUf.setText(c.getUf());
				}
			}

		});
		add(new JScrollPane(this.lista), BorderLayout.CENTER);

	}

	public static void main(String[] args) throws Exception {
		Tela tp = new Tela();
		tp.setVisible(true);

	}

}
