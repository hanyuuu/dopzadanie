package base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class editProblemForm extends Components {
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Сохранить", "Изменить" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[4];
	private int problemNum;
	private String solution;
	private String status;
	private String description;
	static boolean updated = false;
	//String[] items = { "Проблема в основном решена", "Проблема скорее не решена", "Решение неверное" };
	private JComboBox list = new JComboBox();
	private JComboBox solutionslist = new JComboBox();
	private JComboBox ratingslist = new JComboBox();
	private String[] labelNames = { "Порядковый номер", "Описание проблемы", "Статус решения", "Решение",
			"Родительская проблема" };
	private int[] labelBounds = { 10, 5, 280, 50, 10, 45, 280, 50, 10, 85, 280, 50, 10, 125, 280, 50, 10, 165, 280,
			50 };
	private int[] textFieldsBounds = { 180, 17, 280, 25, 180, 57, 280, 25,180, 177, 280, 25 };
	private JLabel[] label = new JLabel[5];
	private int[] buttonBounds = { 10, 300, 100, 25, 150, 300, 90, 25 };
	ArrayList<solution> solutions = new ArrayList<solution>();
	ArrayList<rating> ratings = new ArrayList<rating>();
	rating bigrating;
	public editProblemForm(int problemNum, String description, String status, String solution, ArrayList<solution> solutions, ArrayList<rating> ratings) {
		this.problemNum = problemNum;
		this.status = status;
		this.solution = solution;
		this.description = description;
		this.solutions = solutions;
		this.ratings = ratings;
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Редактировать проблему", mainPanel, 500, 400);
		for (int i = 0; i < 5; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			if (i < 3) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
			}
			if (i < 2) {
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
		}
		//String[] items = new String[solutions.size()];
		String[] tempsolutions = new String[solutions.size()];
		String[] tempratings = new String[ratings.size()];
		for (int a = 0;a<solutions.size();a++) {
			tempsolutions[a] = solutions.get(a).getSolution();
		}
		//System.out.println("Еще раз печатаю рейтинги");
		for (int a = 0;a<ratings.size();a++) {
			tempratings[a] = ratings.get(a).getRating();
			//System.out.println(ratings.get(a).getRating());
		}
		list = CreateComboBox(tempratings, 180, 97, 280, 25);
		solutionslist = CreateComboBox(tempsolutions, 180, 137, 280, 25);
		//ratingslist = CreateComboBox(tempratings, 180, 57, 280, 25);
		list.setSelectedItem(status);
		textField[1].setText(description);
		//textField[2].setText(solution);
		mainFrame.add(list);
		mainFrame.add(solutionslist);
		textField[0].setEditable(false);
		textField[0].setText(Integer.toString(problemNum));
		mainFrame.setVisible(true);
		
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn.Conn();
					conn.CreateDB();
					System.out.println(problemNum + " " + textField[1].getText() + " " + solutionslist.getSelectedIndex()+1 + " " + list.getSelectedIndex()+1);
					conn.EditProblemInDB(problemNum, textField[1].getText(), solutionslist.getSelectedIndex()+1, list.getSelectedIndex()+1);
					conn.CloseDB();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				drawTable.update();
				killFrame();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
	private void killFrame() {
		this.dispose();
	}
}
