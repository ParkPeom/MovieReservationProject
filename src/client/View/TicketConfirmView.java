package client.View;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.DAO.CustomerDAO;
import client.DAO.MovieDAO;
import client.VO.CustomerVO;
import client.VO.MovieVO;
import client.VO.TicketVO;
import client.View.SeatView.ImgPanel;

import java.awt.Color;
import java.awt.Dimension;

//���� �Ϸ� ������ 
public class TicketConfirmView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	//DAO
	private CustomerDAO customerDao = CustomerDAO.getInstance();
	//VO
	private TicketVO ticket;
	private CustomerVO customer;
	
	//Panel
	private JPanel panel = new JPanel();
	private ImgPanel imgPanel;
	class ImgPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ImageIcon icon = new ImageIcon("./logo.GIF");
		private Image img = icon.getImage(); // �̹��� ��ü

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Dimension d = getSize();
			g.drawImage(img, 0, 0, d.width, d.height, null);
		}
	}
	
	private JLabel lblNewLabel = new JLabel("���Ű� �Ϸ�Ǿ����ϴ�.");
	private JButton btn1 = new JButton("���ų��� Ȯ��");
	private JButton btn2 = new JButton("���ų��� ���");
	private JButton btn3 = new JButton("���� ���ϱ�");
	
	//������
	public TicketConfirmView(TicketVO ticket) {
		this.ticket = ticket;
		buildGUI();
		setEvent();
	}

	//��ư ���� �׼� 
	@Override
	public void actionPerformed(ActionEvent e) {
		//���ų��� Ȯ��
		if (e.getSource() == btn1) {
			System.out.println("���ų��� Ȯ��");
			TicketConfirmView01 view1 = new TicketConfirmView01(ticket);
		}
		
		//���ų��� ���
		if (e.getSource() == btn2) {
			System.out.println("���ų��� ���");
			TicketConfirmView02 view2 = new TicketConfirmView02(ticket);
		}
		
		//������ 
		if (e.getSource() == btn3) {
			System.out.println("���� ���ϱ�");
			customer = customerDao.selectById(ticket.getCustomerId());
			dispose();
			TicketingView ticketingView = new TicketingView(customer);
		}
	}

	public void setEvent() {
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
	}
	
	//GUI����
	public void buildGUI() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("./favicon.PNG");
		setIconImage(img);
		setTitle("���� �Ϸ�");
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
		
		imgPanel = new ImgPanel();
		imgPanel.setBounds(219, 53, 325, 151);
		panel.add(imgPanel);
		
		lblNewLabel.setText("'" + ticket.getCustomerName() +"' �� ���Ű� �Ϸ�Ǿ����ϴ�.");
		lblNewLabel.setFont(new Font("�޸տ�����", Font.PLAIN, 20));
		lblNewLabel.setBounds(223, 211, 368, 82);
		panel.add(lblNewLabel);
		
		btn1.setFont(new Font("�޸տ�����", Font.PLAIN, 17));
		btn1.setBounds(149, 304, 149, 82);
		panel.add(btn1);
		
		btn2.setFont(new Font("�޸տ�����", Font.PLAIN, 17));
		btn2.setBounds(322, 304, 149, 82);
		panel.add(btn2);
		
		btn3.setFont(new Font("�޸տ�����", Font.PLAIN, 17));
		btn3.setBounds(498, 304, 149, 82);
		panel.add(btn3);
	}
}
