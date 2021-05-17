package client.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.VO.SeatVO;
import client.VO.TicketVO;
import client.util.CloseUtil;
import client.util.ConnUtil;

public class SeatDAO {
	private static SeatDAO instance = new SeatDAO();

	private SeatDAO() {
	}

	public static SeatDAO getInstance() {
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

	//���� �ϱ�
	public void setSeatRerserved(SeatVO seat) {
		String theathername = seat.getTheathername();
		String roomNumber = seat.getRoomNumber();
		String seatNumber = seat.getSeatNumber();
		int time = seat.getTime();
		String day = seat.getDay();
		String sql = "insert into seat values(?,?,?,?,?,?)";
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theathername);
			pstmt.setString(2, roomNumber);
			pstmt.setString(3, seatNumber);
			pstmt.setInt(4, time);
			pstmt.setString(5, day);
			pstmt.setString(6, "y");
			pstmt.executeUpdate();
			//System.out.println("�¼� update");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
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

	//����� �¼��� ��ȣ�� �޾ƿ��� select
	public ArrayList<String> getSeatList(String theathername, String roomNumber, int time, String day) {
		String sql = "select * from seat where theathername = ? and roomNumber = ? and time = ? and day = ? and reserved = 'y'";
		ArrayList<String> list = new ArrayList<>();
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theathername);
			pstmt.setString(2, roomNumber);
			pstmt.setInt(3, time);
			pstmt.setString(4, day);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String seatNumber = rs.getString("seatnumber");
				list.add(seatNumber);
			}
			//System.out.println("����� �¼� select");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
		return list;
	}
}
