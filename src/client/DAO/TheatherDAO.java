package client.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import client.VO.SeatVO;
import client.VO.TheatherVO;
import client.util.CloseUtil;
import client.util.ConnUtil;

public class TheatherDAO {
	private static TheatherDAO instance = new TheatherDAO();

	private TheatherDAO() {
	}

	public static TheatherDAO getInstance() {
		return instance;
	}

	private static Connection con;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public void connect() {
		try {
			con = ConnUtil.getConnection();
			System.out.println("������ ���̽� ���� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ��ȭ�� ����Ʈ ����ϱ�.
	public ArrayList<TheatherVO> selectList() {
		ArrayList<TheatherVO> list = new ArrayList<>();
		try {
			connect();
			String sql = "select * from theather";

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TheatherVO theather = new TheatherVO();
				theather.setTheathername(rs.getString("theathername"));
				list.add(theather);
			}
		} catch (Exception e) {
		} finally {
			CloseUtil.close(pstmt, con);
		}
		return list;
	}
	
	//�¼��� ����Ѵ�. reserved �Ǿ� �ִ��� �ȵǾ��մ��� Ȯ���Ѵ�. 
	public SeatVO[][] getSeats(){
		String sql = "select * from Theather where thathername = ? and roomNumber = ? and time = ?";
		SeatVO[][] seats= new SeatVO[15][15];
		return seats;
	}
	
	//�ϳ� ��� �Ѵ�. 

	//����� �¼��� ������ ����Ѵ�. ��ȭ���̸�, ���̸�, ��ȭ ȸ�� �� �޾Ƽ�.
	public int getReservedSeat(String theatherName, String roomNumber, int time) {
		String sql = "select * from Theather where thathername = ? and roomNumber = ? and time = ?";
		try {
			connect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theatherName);
			pstmt.setString(2, roomNumber);
			pstmt.setInt(3, time);
			if(rs.next()) {
				return rs.getInt("reservedSeat"); //����� �¼��� ���� 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
		return 0;
	}


}
