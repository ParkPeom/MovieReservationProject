package client.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.VO.CustomerVO;
import client.util.CloseUtil;
import client.util.ConnUtil;

//singleton ���� ����
public class CustomerDAO {

	private static CustomerDAO instance = new CustomerDAO();

	private CustomerDAO() {
	}

	public static CustomerDAO getInstance() {
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

	// select - ȸ�� �˻� - �α���
	/*
	 * 1) id Ȯ�� - �ִ� id -> ��й�ȣȮ�� 1. ��й�ȣ�� Ʋ�� -> return // ��й�ȣ�� Ȯ���ϼ��� 2. ��й�ȣ�� ����
	 * -> login �Ϸ�. - ���� ����. 9+
	 * 
	 * - ���� id -> return // id�� Ȯ���ϼ���
	 */

	// ���̵�� ã�ƿ���
	public CustomerVO selectById(String id) {
		String sql = "select * from customer where id = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			CustomerVO customer = new CustomerVO();
			// id Ȯ��
			if (rs.next()) {
				customer.setId(id);
				customer.setPassword(rs.getString("name"));
				customer.setName(rs.getString("name"));
				customer.setEmail(rs.getString("email"));
				customer.setPhoneNumber(rs.getString("phonenumber"));
				return customer;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	// �α���
	// ���� : 0 ���� : ��й�ȣ���� : -1, ��ϵ������� ���̵� : -2 | �����ͺ��̽� ���� : -3;
	public int login(String id, String password) {
		String sql = "select * from customer where id = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// id Ȯ��
			if (rs.next()) {
				System.out.println("id�� Ȯ�ε�. ");
				String pwd = rs.getString("password"); // ���� ��й�ȣ

				// 1) ��й�ȣ�� �´� ���.
				if (password.equals(pwd)) {
					System.out.println("�α��� �Ϸ�");
					return 0;
				}
				// 2) ��й�ȣ�� Ʋ�� ���
				else {
					return -1;
				}
			}
			// ���� id
			else {
				return -2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -3; // �����ͺ��̽� ����
	}
	
	//ȸ������
	public boolean register(CustomerVO customer) {
		String name = customer.getName();
		String id = customer.getId();
		String password = customer.getPassword();
		String email = customer.getEmail();
		String phoneNumber = customer.getPhoneNumber();

		String sql = "insert into customer values (?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, password);
			pstmt.setString(4, email);
			pstmt.setString(5, phoneNumber);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(pstmt, con);
		}
		return false;
	}


	// update - ���̵� �� ��й�ȣ ����

	// delete - ȸ�� ����

}
