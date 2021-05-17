package client.View;

import javax.swing.JFrame;

import client.DAO.MovieDAO;
import client.DAO.SeatDAO;
import client.DAO.TicketDAO;
import client.VO.MovieVO;
import client.VO.SeatVO;
import client.VO.TheatherVO;
import client.VO.TicketVO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JCheckBox;

public class SeatView extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	// VO
	private TicketVO ticket;
	private MovieVO movie;
	private SeatVO seat;

	private ArrayList<String> reservedSeat = new ArrayList<String>();

	// DAO
	private MovieDAO movieDao = MovieDAO.getInstance();
	private SeatDAO seatDao = SeatDAO.getInstance();
	private TicketDAO ticketDao = TicketDAO.getInstance(); // ��� �뵵

	// src
	private String movieImgSrc;
	private int person = 0;// �¼��� ����� �ο���.
	private int personCheck = 0; // �¼� ����� �ο��� check��

	// �¼��� �̸� �迭�� ����� ���ƾ� �Ѵ�.
	// ��, ��
	private JCheckBox[][] seats = new JCheckBox[15][15];
	private Vector<String> seatsNumber = new Vector<String>(); // ���⿡ check�� �¼��� ����ȴ�.
	private Vector<JLabel> seatDetails = new Vector<>();

	private ImgPanel imgPanel;

	class ImgPanel extends JPanel {
		private ImageIcon icon;
		private Image img; // �̹��� ��ü

		public ImgPanel() {
			// �̹��� �޾ƿ;���.
			movie = movieDao.getMovie(ticket.getMovieName());
			movieImgSrc = movie.getImgSrc();
			//System.out.println(movieImgSrc);
			icon = new ImageIcon("./" + movieImgSrc);
			img = icon.getImage(); // �̹��� ��ü
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Dimension d = getSize();
			g.drawImage(img, 0, 0, d.width, d.height, null);
		}
	}

	private JPanel titlePanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel seatPanel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	private JPanel leftBottomPanel = new JPanel();
	private JLabel lblNewLabel_1_1 = new JLabel("���� :");
	private JLabel lblNewLabel_3_1 = new JLabel("��¥ :");
	private JLabel lblNewLabel_5 = new JLabel("�ο� :");
	private JLabel lblNewLabel_7 = new JLabel("\uAE08\uC561 :");

	private JLabel titleLabel = new JLabel("-");
	private JLabel theatherLabel = new JLabel("-");
	private JLabel dayLabel = new JLabel("-");
	private JLabel personLabel = new JLabel("-");
	private JLabel costLabel = new JLabel("-");

	private final JLabel checkSeatLabel = new JLabel("\uC120\uD0DD\uD55C \uC88C\uC11D \uBC88\uD638");
	private JButton ticketingButton = new JButton("\uC88C\uC11D\uC120\uD0DD ");
	private JButton reCheckSeatButton = new JButton("�ٽ� ����");
	private JLabel seatInfoLabel = new JLabel("\uC88C\uC11D \uD604\uD669");
	private JLabel seatInfoDetailLabel = new JLabel("255 / 255");

	public SeatView(TicketVO ticket) {
		this.ticket = ticket;
		setInfotoLabel();
		setPerson();
		buildGUI();
		setEvent();
		checkInfo();
	}

	public void setInfotoLabel() {
		titleLabel.setText(ticket.getCustomerName() + " ��");
		titleLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 15));
		theatherLabel.setText(ticket.getTheatherName());
		dayLabel.setText(ticket.getDay());
		personLabel.setText(Integer.toString(ticket.getPerson()) +"��");
		costLabel.setText(Integer.toString(ticket.getCost()));

		person = ticket.getPerson();
		// �¼� �ϳ� �������� Label �� ���� �¼����� ����.
		// �ο����� �°� �¼��� �� ������ ���, ��� �¼� üũ ���ϵ��� ���� �Ѵ�.

	}

	// �ο��� ��ŭ �¼� check ���ִ°� �־��ֱ� .
	public void setPerson() {
		for (int i = 0; i < person; i++) {
			JLabel jLabel = new JLabel("�¼� " + (i + 1));
			jLabel.setBounds(11, (35 + (i * 25)), 57, 15);
			jLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 10));
			jLabel.setForeground(Color.GRAY);

			// Vector�� �־ ���������ϵ��� �Ѵ�.
			seatDetails.add(jLabel);
			panel_1.add(jLabel);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// �¼� �ٽü���.
		if (e.getSource() == reCheckSeatButton) {
			seatAllchecked(); // �̰� �ƴ� ***************************************** -> DB ���� �޾Ƽ� �־����.
			personCheck = 0;
			for (int i = 0; i < person; i++) {
				seatDetails.get(i).setText("�¼� " + (i + 1));
				// check �� �¼��� unchecked�� �����������.
				String text = seatsNumber.get(i);

				int row = text.charAt(0); // �ప
				String colStr = text.substring(1);// 1�ڿ� �ִ� �� �����;ߵ�.

				row = row - 65;
				int col = Integer.parseInt(colStr) - 1;
				seats[row][col].setSelected(false);
			}
			
			for (int i = 0; i < reservedSeat.size(); i++) {
				//System.out.println(reservedSeat.get(i) + " �¼��� ����Ǿ� ����. ");
				String text = reservedSeat.get(i);
				int row = text.charAt(0); // �ప
				String colStr = text.substring(1);// 1�ڿ� �ִ� �� �����;ߵ�.
				row = row - 65;
				int col = Integer.parseInt(colStr) - 1;
				seats[row][col].setEnabled(false);
			}
			reCheckSeatButton.setEnabled(false);
			ticketingButton.setEnabled(false);
		}

		if (e.getSource() == ticketingButton) {
			// �¼� ���� �ϱ�.
			reserve();
		}
	}

	public void checkInfo() {
		// '������','1��','O15',1,'2021�� 2�� 5��' �������� ������ �¼� checkbox�� �ٲ��ش�. enabled �ϰ�
		String theathername = ticket.getTheatherName();
		String roomNumber = ticket.getRoomNumber();
		int time = ticket.getTime();
		String day = ticket.getDay();

		reservedSeat = seatDao.getSeatList(theathername, roomNumber, time, day);
		for (int i = 0; i < reservedSeat.size(); i++) {
			//System.out.println(reservedSeat.get(i) + " �¼��� ����Ǿ� ����. ");
			String text = reservedSeat.get(i);
			int row = text.charAt(0); // �ప
			String colStr = text.substring(1);// 1�ڿ� �ִ� �� �����;ߵ�.
			row = row - 65;
			int col = Integer.parseInt(colStr) - 1;
			seats[row][col].setEnabled(false);
		}
		
		int leftSeat = (255 - reservedSeat.size());
		seatInfoDetailLabel.setText(Integer.toString(leftSeat) + " / 255");
	}

	// �¼��� �Ϻ��� �����ϸ� ����Ǵ� �޼���
	public void reserve() {
		// setReserveDate
		String seatNumberCheck = "";
		// set SeatVO reserved
		for (int i = 0; i < person; i++) {
			// �ο�����ŭ Ƽ�� VO�� ������ش�.
			// SeatVO ~ reserved �ǰ� ����
			SeatVO seatVO = new SeatVO();
			seatVO.setTheathername(ticket.getTheatherName());
			seatVO.setRoomNumber(ticket.getRoomNumber());
			seatVO.setTime(ticket.getTime());
			seatVO.setReserved("y");
			seatVO.setDay(ticket.getDay());
			seatVO.setSeatNumber(seatsNumber.get(i)); // �¼� ��ȣ �ֱ�
			//System.out.println(seatVO.toString());
			seatDao.setSeatRerserved(seatVO);
			
			//seatNumber�����
			if((person-1) == i ) {
				seatNumberCheck += seatsNumber.get(i);
			} else {
				seatNumberCheck += (seatsNumber.get(i)+"/");
			}
		}
		// Ƽ�� �� ����� �����.
		ticket.setSeatNumber(seatNumberCheck);
		// ticket insert
		ticketDao.insert(ticket); //Ƽ�ϵ�� 
		dispose();
		TicketConfirmView ticketConfirmView = new TicketConfirmView(ticket);
	}


	// ��� �¼� �������� �ϱ�.
	public void seatAlldisabledchecked() {
		for (int j = 0; j < 15; j++) {
			for (int i = 0; i < 15; i++) {
				seats[j][i].setEnabled(false);
			}
		}
		ticketingButton.setEnabled(true);
	}

	// ��� �¼� ���� ���ְ� ����.
	public void seatAllchecked() {
		for (int j = 0; j < 15; j++) {
			for (int i = 0; i < 15; i++) {
				seats[j][i].setEnabled(true);
			}
		}
	}

	// �¼��� ������ �ޱ�.
	class SeatChangeListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == 1) {
				// System.out.println("checked" + e.getSource());

				// �¼� ��ȣ �̾Ƴ���.
				JCheckBox tempCheckbox = (JCheckBox) e.getSource();
				String test = tempCheckbox.getText().trim();
				String[] testArr = test.split(",", 0);

				// ��
				String row = testArr[0];
				String col = testArr[1];

				seatsNumber.add(row + col); // �¼� ��ȣ vector�� �ֱ�.
				seatsNumber.set(personCheck, row + col);
				seatDetails.get(personCheck).setText(row + col);
				personCheck++;
				if (person == personCheck) {
					System.out.println("��� �¼� check �Ϸ�.");
					reCheckSeatButton.setEnabled(true);
					seatAlldisabledchecked();
				}
			} else {
				// check �������� �ٲ��ִ� �͵� �ʿ��ϴ�.
				if (personCheck != 0) {
					personCheck--;
				}
				// System.out.println("unchecked" + personCheck);
				seatDetails.get(personCheck).setText("�¼� " + (personCheck + 1));
			}
		}
	}

	public void setEvent() {
		for (int j = 0; j < 15; j++) {
			for (int i = 0; i < 15; i++) {
				seats[j][i].addItemListener(new SeatChangeListener());
			}
		}
		reCheckSeatButton.addActionListener(this);
		ticketingButton.addActionListener(this);
	}

	public void buildGUI() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("./favicon.PNG");
		setIconImage(img);

		setTitle("�¼� ����");
		setSize(818, 612);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // ȭ�� ��� ��½�Ű��
		setResizable(false);
		setVisible(true);
		getContentPane().setLayout(null);

		titlePanel.setBounds(0, 0, 812, 31);
		getContentPane().add(titlePanel);
		titlePanel.setLayout(null);

		titleLabel.setBounds(609, 10, 100, 21);
		titlePanel.add(titleLabel);

		leftPanel.setBounds(608, 52, 192, 521);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(null);

		// �̹��� �ǳ��� ��.
		imgPanel = new ImgPanel();
		imgPanel.setBounds(0, 0, 180, 383);
		leftPanel.add(imgPanel);
		imgPanel.setLayout(null);

		leftBottomPanel.setBounds(0, 373, 180, 148);
		leftPanel.add(leftBottomPanel);
		leftBottomPanel.setLayout(null);

		lblNewLabel_1_1.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(12, 10, 63, 25);
		leftBottomPanel.add(lblNewLabel_1_1);

		lblNewLabel_3_1.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
		lblNewLabel_3_1.setBounds(12, 35, 63, 25);
		leftBottomPanel.add(lblNewLabel_3_1);

		lblNewLabel_5.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(12, 60, 63, 25);
		leftBottomPanel.add(lblNewLabel_5);

		lblNewLabel_7.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(12, 85, 63, 25);
		leftBottomPanel.add(lblNewLabel_7);

		ticketingButton.setFont(new Font("�޸տ�����", Font.PLAIN, 13));
		ticketingButton.setBounds(74, 113, 94, 25);
		leftBottomPanel.add(ticketingButton);

		theatherLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 10));
		theatherLabel.setBounds(87, 10, 85, 25);
		leftBottomPanel.add(theatherLabel);

		dayLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 10));
		dayLabel.setBounds(87, 39, 85, 25);
		leftBottomPanel.add(dayLabel);

		personLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 10));
		personLabel.setBounds(87, 64, 85, 25);
		leftBottomPanel.add(personLabel);

		costLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 10));
		costLabel.setBounds(87, 89, 85, 25);
		leftBottomPanel.add(costLabel);

		seatPanel.setBounds(10, 41, 598, 532);
		getContentPane().add(seatPanel);
		seatPanel.setLayout(null);
		panel_1.setBackground(Color.WHITE);

		panel_1.setBounds(465, 10, 127, 480);
		seatPanel.add(panel_1);
		panel_1.setLayout(null);
		checkSeatLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 13));
		checkSeatLabel.setBounds(11, 10, 103, 15);
		panel_1.add(checkSeatLabel);

		reCheckSeatButton.setBounds(17, 447, 97, 23);
		panel_1.add(reCheckSeatButton);

		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(12, 10, 447, 480);
		seatPanel.add(panel_2);
		panel_2.setLayout(null);
		JLabel screenLabel = new JLabel("\t\t\t\t\t\t\t\tScreen");
		screenLabel.setBackground(Color.WHITE);
		screenLabel.setForeground(Color.BLACK);
		screenLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
		screenLabel.setBounds(198, 10, 76, 15);
		panel_2.add(screenLabel);
		panel_2.setForeground(Color.WHITE);
		seatInfoLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 12));
		seatInfoLabel.setBounds(186, 427, 59, 21);
		
		panel_2.add(seatInfoLabel);
		seatInfoDetailLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 12));
		seatInfoDetailLabel.setBounds(186, 449, 68, 21);
		
		panel_2.add(seatInfoDetailLabel);
		reCheckSeatButton.setEnabled(false);
		ticketingButton.setEnabled(false);
		repaint();
		// ��
		for (int i = 0; i < 15; i++) {
			JLabel seatColLabel = new JLabel();
			char input = (char) ('A' + i);
			seatColLabel.setText(Character.toString(input));
			seatColLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
			seatColLabel.setBounds(25, (72 + (i * 20)), 22, 15);
			panel_2.add(seatColLabel);

		}

		// ��
		for (int i = 0; i < 15; i++) {
			JLabel seatRowLabel = new JLabel();
			String input = Integer.toString(i + 1);
			seatRowLabel.setText(input);
			seatRowLabel.setBounds(61 + (i * 22), 55, 22, 15);
			seatRowLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 14));
			panel_2.add(seatRowLabel);
		}

		// �¼� checkBox
		for (int j = 0; j < 15; j++) {
			for (int i = 0; i < 15; i++) {
				JCheckBox chkBox = new JCheckBox("");
				chkBox.setBackground(Color.WHITE);
				chkBox.setForeground(Color.WHITE);
				chkBox.setBounds(61 + (i * 22), 72 + (j * 20), 22, 15);
				seats[j][i] = chkBox; // �¼��� seats�迭�� �־���. 2�����迭�� ����.
				char input = (char) (j + 65);
				chkBox.setText(input + "," + Integer.toString(i + 1)); // �¼��� ������ ���� �� ����.
				panel_2.add(chkBox);
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
}
