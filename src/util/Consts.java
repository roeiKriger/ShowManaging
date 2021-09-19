package util;

import java.net.URLDecoder;


public class Consts 
{
	private Consts() {
		throw new AssertionError();
	}
	
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";

	
	//insert queries //
	public static final String SQL_INS_SHOW = "{ call quer_insert_show(?,?,?,?) }";
	public static final String SQL_INS_THEATER = "{ call quer_insert_theater(?,?,?,?,?,?) }";
	public static final String SQL_INS_SHOW_IN_THEATER = "{ call quer_insert_showInTheater(?,?,?,?,?,?,?) }";
	
	
	//update queries//
	public static final String SQL_UPDA_SHOW = "{ call quer_update_show(?,?,?,?) }";
	public static final String SQL_UPDA_THEATER = "{ call quer_update_theater(?,?,?,?,?,?) }";
	public static final String SQL_UPDA_SHOW_IN_THEATER = "{ call quer_update_showInTheater(?,?,?,?,?,?,?) }";
	
	
	//select all showInTheater by Show ID and Theater ID
	public static final String SQL_SEL_SHOW_IN_THEATER ="SELECT tbl_show_and_theater.theaterId, tbl_show_and_theater.showId\r\n"
			+ "FROM tbl_show_and_theater;\r\n"
			+ "";
	
	
	
	public static final String SQL_SEL_SHOW_TICKETS_BY_SHOWID_THEATERID = "call quer_select_tickets_by_showId_theaterId(?,?)";
	

	//for recomedation//
	public static final String SQL_SEL_ALL_SHOWS_RECOM = "call quer_sel_reco_by_parm(?,?,?)";
	public static final String SQL_GET_EVENT_DATE = "call quer_get_date(?,?)";
	
	public static final String SQL_SHOW_EXIST = "call quer_is_show_exist(?)";
	
	public static final String SQL_THEATER_EXIST = "call quer_is_theater_exist(?)";
	
	//quer_is_theater_exist
	
	
	
	
	//getting show from DB
	public static final String SQL_SEL_ALL_THEATERS ="SELECT tbl_theater.*\r\n"+ "FROM tbl_theater";
	
	public static final String SQL_SEL_ALL_SHOWINTHEATERS = "SELECT tbl_show.*, tbl_theater.*, tbl_show_and_theater.price, tbl_show_and_theater.dateOfShow, tbl_show_and_theater.startHour, tbl_show_and_theater.status, tbl_show_and_theater.updateDate\r\n"
			+ "FROM tbl_theater INNER JOIN (tbl_show INNER JOIN tbl_show_and_theater ON tbl_show.showId = tbl_show_and_theater.showId) ON tbl_theater.theaterId = tbl_show_and_theater.theaterId";
	

	//getting all custumers
	public static final String SQL_SEL_ALL_CUSTUMERS = "SELECT tbl_person.fName, tbl_person.lName, tbl_person.phoneNum, tbl_person.city, tbl_customer.cMail, tbl_customer.password\r\n"
			+ "FROM tbl_person INNER JOIN tbl_customer ON tbl_person.phoneNum = tbl_customer.phoneNum";

	//getting all Admins
	public static final String SQL_SEL_ALL_ADMIN = "SELECT tbl_person.fName, tbl_person.lName, tbl_person.phoneNum, tbl_person.city, tbl_admin.UserName, tbl_admin.Password\r\n"
			+ "FROM tbl_person INNER JOIN tbl_admin ON tbl_person.phoneNum = tbl_admin.phoneNum";
	
	
	
	//insertOrder
	
	public static final String SQL_NEW_ORDER = "{ call ins_order(?,?) }";
	public static final String SQL_NEW_Ticket = "{ call ins_tick_order(?,?,?,?) }";
	
	public static final String SQL_ALL_ORDERIDS = "SELECT tbl_order.orderId\r\n"
			+ "FROM tbl_order";
	public static final String SQL_INS_ITEM_ORDER = "{ call ins_itemOrder(?,?,?) }";
	//select items 
	public static final String SQL_ALL_ITEMS = "SELECT tbl_item.*\r\n" + "FROM tbl_item";
	
	
	public static final String SQL_AMOUNT_OF_FOOD = "{ call quer_food_amount(?,?) }";
	
	
	
	
	//for Register//
	public static final String SQL_INS_PERSON=" { call ins_Person(?,?,?,?) }";
	public static final String SQL_INS_CUSTUMER=" { call quer_Ins_Custumer(?,?,?) }";
	
	
	//for shibutz//
	public static final String SQL_SEL_SEATS_BY_THEATER=" { call quer_sel_seats_by_theater(?) }";
	public static final String SQL_UPD_SEAT_AVALIABLE=" { call upd_seat_avaliable(?,?,?,?) }";
	
	//for entrance//
	public static final String SQL_INS_ENTERNCE=" { call quer_ins_entrance(?,?,?,?,?,?) }";

	public static final String SQL_SEL_ENTRACE=" { call quer_sel_entrace_by_dates(?,?) }";
	
	public static final String SQl_GET_ALL_ENTRACE ="SELECT tbl_entrance.*\r\n"+ "FROM tbl_entrance";
	
	
	
/*---------------------------------------------------------------------------------------*/
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				System.out.println(decoded);

				return decoded + "/src/entity/DB_ShowManaging.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				return decoded + "src/entity/DB_ShowManaging.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public enum Manipulation {
    	UPDATE, INSERT, DELETE;
    }
}


