package com.toyota.scs.serviceparts.util;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import com.toyota.scs.serviceparts.BaseTest;
 
public class DBUtil extends BaseTest {
 
 	static int rowCount;
	public static void insertRecordIntoPart(int partId, int OrderID, String PartDesc, String PartNumber, String vendorPartNum, String LinitemNum) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO SP_PART (part_id, order_id, direct_ship, home_position, line_item_number,"
			+ " modified_by, modified_date, order_quantity, part_desc, part_number, status,vendor_part_number,ddd,outstanding_quantity,sub_part_number)"
				+ "values (?,?,?,?,?,'TEST',CURRENT_TIMESTAMP,100,?,?,'test',?,'2020-08-01',100,581650702200);";
		Connection con = createConnection();
 		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, partId);
		stmt.setInt(2, OrderID);
		stmt.setString(3, "N");
		stmt.setString(4, "S");
		stmt.setString(5, LinitemNum);
		stmt.setString(6, PartDesc);
		stmt.setString(7, PartNumber);
		stmt.setString(8, vendorPartNum);
		int i = stmt.executeUpdate();
 	
 		}
	public static void insertRecordIntoOrder(int orderID, String orderType, String purchaseOrderNumber, String vendor)
			throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO SP_Order(order_id, order_type,po_number, vendor_code, modified_by, modified_date )"
				+ "VALUES(?,?,?,?,'TEST',CURRENT_TIMESTAMP);";
	Connection con = createConnection();
 
		PreparedStatement stmt = con.prepareStatement(sql);
 
		stmt.setInt(1, orderID);
		stmt.setString(2, orderType);
		stmt.setString(3, purchaseOrderNumber);
			stmt.setString(4, vendor);
			int i = stmt.executeUpdate();
		
		con.close();
 	}
      public static Connection createConnection() throws SQLException, ClassNotFoundException {
              Class.forName("org.postgresql.Driver");
              String connectionURL = "jdbc:postgresql://localhost:5432/SCSServicePartsDB";
              Connection con = DriverManager.getConnection(connectionURL, "postgres",
                            Settings.getProperty("spring.datasource.password"));
             return con;

        }
       public static void insertRecordIntoVendors(int id, String vendors, String desc)
                      throws SQLException, ClassNotFoundException {
              Connection con = createConnection();

              String sql = "INSERT INTO SP_VENDOR (id, modified_by, modified_date, trading_partner_id, vendor_code, vendor_desc)"
                               + "values (?,'BATCH',CURRENT_TIMESTAMP,1111,?,?);";
                      PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, id);
                     stmt.setString(2, vendors);
                      stmt.setString(3, "test" + desc);
                      int i = stmt.executeUpdate();
               
              con.close();

        }
}


