package client.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import client.VO.SeatVO;
import client.VO.TicketVO;
import client.util.CloseUtil;
import client.util.ConnUtil;

public class TicketDAO {
	private static TicketDAO instance = new TicketDAO();

	private TicketDAO() {
	}

	public static TicketDAO getInstance() {
		return instance;
	}

	private static Connection con;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public void connect() {
		try {
			con = ConnUtil.getConnection();
			// System.out.println("������ ���̽� ���� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Ƽ�� �˻� select List (input �� �̸�)
	public ArrayList<TicketVO> selectList(String customerId) {
		String sql = "select * from ticket where customerId = ? "; //�� ���̵� ������ ������ ������ �̾��ּ���!
		
		ArrayList<TicketVO> list = new ArrayList<>();
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String customerName = rs.getString("customerName");
				String seatNumber = rs.getString("seatNumber");
				String theatherName = rs.getString("theatherName");
				String roomNumber = rs.getString("roomNumber");
				String movieName = rs.getString("movieName");
				String day = rs.getString("day");
				int time = rs.getInt("time");
				String reserveDate = rs.getString("reserveDate");
				int cost = rs.getInt("cost");
				int person = rs.getInt("person");
				TicketVO ticketVO = new TicketVO(
						customerName,customerId,seatNumber,theatherName,roomNumber,
						movieName,day, time, reserveDate, cost, person	
						);
				list.add(ticketVO);
			}
			//System.out.println("����� �¼� select");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
		return list;
	}
	
	//���� ��� -> delete - �׳� ���� �ع�����.
		public void setSeatCancel(TicketVO ticket) {
			String theathername = ticket.getTheatherName();
			String roomNumber = ticket.getRoomNumber();
			String seatNumber = ticket.getSeatNumber();
			int time = ticket.getTime();
			String day = ticket.getDay();
			
			String sql = "delete from seat where theathername = ? and roomNumber = ? and time = ? and day = ? and seatNumber = ?";
			try {
				connect();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, theathername);
				pstmt.setString(2, roomNumber);
				pstmt.setInt(3, time);
				pstmt.setString(4, day);
				pstmt.setString(5, seatNumber);
				pstmt.executeUpdate();
				System.out.println("�¼� ���� ���");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CloseUtil.close(pstmt, con);
			}
		}
		
	// Ƽ�� ����  delete (�������)
	public void delete(TicketVO ticket) {
		String customerId = ticket.getCustomerId();// �� ���̵� -ticketingView 0
		String seatNumber = ticket.getSeatNumber(); // �¼� �̸� -seatView 0
		String theatherName = ticket.getTheatherName(); // ���� �̸� -ticketingView 0
		String roomNumber = ticket.getRoomNumber(); // �� ��ȣ -ticketingView 0
		String movieName = ticket.getMovieName(); // ��ȭ �̸� -ticketingView 0
		String day = ticket.getDay(); // ��ȭ ���� ��¥. �� �� �� -- > ������ ��¥�� ����. -ticketingView 0
		
		int time = ticket.getTime(); // ��ȭ ȸ�� -ticketingView 0
		String sql = "delete from ticket "
				+ "where customerId = ? and seatNumber = ? and theatherName = ? and roomNumber = ? and movieName = ? "
				+ "and day = ? and time =?";
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			pstmt.setString(2, seatNumber);
			pstmt.setString(3, theatherName);
			pstmt.setString(4, roomNumber);
			pstmt.setString(5, movieName);
			pstmt.setString(6, day);
			pstmt.setInt(7, time);
			pstmt.executeUpdate();
			System.out.println("Ƽ�� ���� �Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
	}

	
	// Ƽ�� ���  insert
	public void insert(TicketVO ticket) {
		String customerName = ticket.getCustomerName(); // �� �̸� -ticketingView 0
		String customerId = ticket.getCustomerId();// �� ���̵� -ticketingView 0
		String seatNumber = ticket.getSeatNumber(); // �¼� �̸� -seatView 0
		String theatherName = ticket.getTheatherName(); // ���� �̸� -ticketingView 0
		String roomNumber = ticket.getRoomNumber(); // �� ��ȣ -ticketingView 0
		String movieName = ticket.getMovieName(); // ��ȭ �̸� -ticketingView 0
		String day = ticket.getDay(); // ��ȭ ���� ��¥. �� �� �� -- > ������ ��¥�� ����. -ticketingView 0
		int time = ticket.getTime(); // ��ȭ ȸ�� -ticketingView 0
		int cost = ticket.getCost(); // Ƽ�� ���� -ticketingView 0
		int person = ticket.getPerson(); // �ο� �� -ticketingView 0

		// ������ �ð� �����ϱ�
		SimpleDateFormat f = new SimpleDateFormat("yyyy�� MM��dd�� HH��mm��ss��");
		Calendar c = Calendar.getInstance();
		String reserveDate = f.format(c.getTime()); // ���糯¥�� ����.
		ticket.setReserveDate(reserveDate);

		String sql = "insert into ticket values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerName);
			pstmt.setString(2, customerId);
			pstmt.setString(3, seatNumber);
			pstmt.setString(4, theatherName);
			pstmt.setString(5, roomNumber);
			pstmt.setString(6, movieName);
			pstmt.setString(7, day);
			pstmt.setInt(8, time);
			pstmt.setString(9, reserveDate);
			pstmt.setInt(10, cost);
			pstmt.setInt(11, person);
			System.out.println(ticket.toString());
			pstmt.executeUpdate();
			System.out.println("Ƽ�� ��� �Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
	}

}
