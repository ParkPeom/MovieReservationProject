package client.View;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import client.DAO.CustomerDAO;
import client.DAO.SeatDAO;
import client.DAO.TicketDAO;
import client.VO.CustomerVO;
import client.VO.TicketVO;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTable;

public class TicketConfirmView02 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// DAO
	private CustomerDAO customerDao = CustomerDAO.getInstance();
	private TicketDAO ticketDao = TicketDAO.getInstance();
	private SeatDAO seatDao = SeatDAO.getInstance();

	// VO
	private TicketVO ticket;
	private ArrayList<TicketVO> tickets;

	private JPanel panel = new JPanel();
	private JLabel lblNewLabel = new JLabel("\uC608\uB9E4 \uB0B4\uC5ED");
	private JButton btnNewButton = new JButton("\uB4A4\uB85C \uAC00\uAE30");
	private JTable table;
	private JDialog dialog1 = new JDialog(this, "���� ��� �Ϸ�", true);

	private int count;
	private JPanel panel_1 = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	private JLabel sizeLabel = new JLabel("-");

	// ������
	public TicketConfirmView02(TicketVO ticket) {
		this.ticket = ticket;
		buildGUI();
		setEvent();
		setDialog();
		setInfo();
	}

	public void setEvent() {
		btnNewButton.addActionListener(this);
	}

	public void setDialog() {
		JLabel text1 = new JLabel("���� ��� �Ϸ�");
		text1.setHorizontalAlignment(SwingConstants.CENTER);
		dialog1.setLocationRelativeTo(this);
		dialog1.getContentPane().setLayout(new BorderLayout(3, 3));
		text1.setFont(new Font("Serif", Font.BOLD, 14));
		dialog1.getContentPane().add(text1, BorderLayout.CENTER);
		dialog1.setResizable(false);
		dialog1.setSize(250, 180);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			dispose();
		}
	}

	public void setInfo() {
		tickets = ticketDao.selectList(ticket.getCustomerId());
		sizeLabel.setText(Integer.toString(tickets.size()) + "�� �˻���");

		// Ƽ�� ����.
		for (int i = 0; i < tickets.size(); i++) {
			TicketVO ticket = tickets.get(i);
			String movieName = ticket.getMovieName();
			String theatherName = ticket.getTheatherName();
			String roomNumber = ticket.getRoomNumber();
			String seatNumber = ticket.getSeatNumber();
			String day = ticket.getDay();
			String time = Integer.toString(ticket.getTime()) + " ȸ��";

			JLabel label1 = new JLabel(movieName);
			label1.setBounds(10, (60 + (i * 30)), 100, 15);
			panel_1.add(label1);

			JLabel label2 = new JLabel(theatherName);
			label2.setBounds(110, (60 + (i * 30)), 100, 15);
			panel_1.add(label2);

			JLabel label3 = new JLabel(roomNumber);
			label3.setBounds(210, (60 + (i * 30)), 100, 15);
			panel_1.add(label3);

			JLabel label4 = new JLabel(seatNumber);
			label4.setBounds(310, (60 + (i * 30)), 100, 15);
			panel_1.add(label4);

			JLabel label5 = new JLabel(day);
			label5.setBounds(410, (60 + (i * 30)), 100, 15);
			panel_1.add(label5);

			JLabel label6 = new JLabel(time);
			label6.setBounds(510, (60 + (i * 30)), 100, 15);
			panel_1.add(label6);

			// seatNumber ������
			// Ƽ�� �¼� ��ȣ�� "/"�� �������� ������� �Ѵ�.
			String[] seats = seatNumber.split("/");
			int seatSize = seats.length;

			JButton btn = new JButton("���� ���"); // action�����ʸ� ���⿡ �޾ƾߵ�.
			btn.setBounds(610, (60 + (i * 30)), 100, 15);
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ticketDao.delete(ticket); // Ƽ���� ����

					// Ƽ�Ͽ� ����� �¼��� ������ŭ Ƽ���� ���������.
					for (int i = 0; i < seatSize; i++) {
						// seatDAO ticket�� ������
						ticket.setSeatNumber(seats[i]); // �̷��� �־, ����� �ְ� ����� �ش�. //���� ����.
						seatDao.setSeatCancel(ticket);
					}
					dialog1.setVisible(true);
					dispose();
					TicketConfirmView02 t = new TicketConfirmView02(ticket);
				}
			});
			panel_1.add(btn);
		}
	}

	// GUI����
	public void buildGUI() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("./favicon.PNG");
		setIconImage(img);
		setTitle("���� ���� ���");
		setSize(818, 612);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // ȭ�� ��� ��½�Ű��
		setResizable(false);
		setVisible(true);
		getContentPane().setLayout(null);

		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 812, 583);
		getContentPane().add(panel);
		panel.setLayout(null);

		lblNewLabel.setText("'" + ticket.getCustomerName() + "' ����  ���� ��Ȳ ");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 21));
		lblNewLabel.setBounds(22, 10, 596, 56);
		panel.add(lblNewLabel);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(12, 67, 776, 506);

		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("\uC601\uD654");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 10, 59, 31);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("�޸տ�����", Font.PLAIN, 15));

		JLabel lblNewLabel_2 = new JLabel("\uC601\uD654\uAD00");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(110, 10, 59, 31);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\uAD00");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(210, 10, 59, 31);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("\uC88C\uC11D\uBC88\uD638");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(310, 10, 83, 31);
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("\uB0A0\uC9DC");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(410, 10, 83, 31);
		panel_1.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("ȸ��");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(510, 10, 83, 31);
		panel_1.add(lblNewLabel_6);

		btnNewButton.setFont(new Font("�޸տ�����", Font.PLAIN, 17));
		btnNewButton.setBounds(630, 20, 158, 40);
		panel.add(btnNewButton);

		sizeLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 20));
		sizeLabel.setBounds(355, 24, 190, 28);
		panel.add(sizeLabel);
	}
}
