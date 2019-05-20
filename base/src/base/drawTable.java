package base;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.AbstractTableModel;

public class drawTable extends JFrame {
	// Модель данных таблицы
	private DefaultTableModel tableModel;
	private JTable table1;
	// Данные для таблиц
	ArrayList<problem> hi = conn.getArr();
	ArrayList<solution> solutions = conn.getSolutionArr();
	ArrayList<rating> ratings = conn.getRatingArr();
	private Object[][] array = new String[][] { {} };
	// Заголовки столбцов
	private Object[] columnsHeader = new String[] { "Порядковый номер", "Описание проблемы", "Статус решения",
			"Решение", "Я люблю большие члены", "Удалить" };
	public drawTable() {
		super("Я гей");
		JButton gay = new JButton("я гей");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(columnsHeader);
		for (int i = hi.size() - 1; i >= 0; i--) {
			problem temp = hi.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.number), temp.description,
					temp.rating.getRating(), temp.solution.getSolution(), "Редактировать", "Удалить" });
		}
		// Создание таблицы на основании модели данных
		table1 = new JTable(tableModel);
		// Создание кнопки добавления строки таблицы
		JButton add = new JButton("Добавить");
		table1.getColumn("Я люблю большие члены").setCellRenderer(new ButtonRenderer());
		table1.getColumn("Я люблю большие члены").setCellEditor(new ButtonEditor(new JCheckBox()));
		// Формирование интерфейса
		Box contents = new Box(BoxLayout.Y_AXIS);
		contents.add(new JScrollPane(table1));
		// contents.add(new JScrollPane(table2));
		getContentPane().add(contents);

		JPanel buttons = new JPanel();
		buttons.add(add);
		// buttons.add(remove);
		getContentPane().add(buttons, "South");
		// Вывод окна на экран
		setSize(400, 300);
		setVisible(true);
	}
	public static void update() {
		
	}
	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;
		private int rowNum;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			rowNum = row + 1;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				System.out.println();
				editProblemForm a = new editProblemForm(Integer.parseInt(table1.getModel().getValueAt(rowNum-1, 0).toString()), hi.get(rowNum - 1).description,
						hi.get(rowNum - 1).rating.getRating(), hi.get(rowNum - 1).solution.getSolution(), solutions, ratings);
			}
			isPushed = false;
			return label;
		}
	}
}
