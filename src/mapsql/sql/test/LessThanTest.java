package mapsql.sql.test;
import mapsql.sql.condition.LessThan;
import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.field.CHARACTER;
import mapsql.sql.field.INTEGER;
import mapsql.sql.statement.CreateTable;
import mapsql.sql.statement.Insert;
import mapsql.sql.statement.Select;
import mapsql.sql.statement.Update;

public class LessThanTest {
	static SQLManager manager = new SQLManager();
	public static void main(String[] args) {
		createTableStatement();
		insertData();
		selectTable();
		selectRow(); //This tests the LessThan method 
	
		}
	

private static void executeStatement(SQLStatement statement) {
	try {
		SQLResult result = manager.execute(statement);
		System.out.println(result);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void createTableStatement() {
	executeStatement(
			new CreateTable(
					"contact", 
					new Field[] {
							new INTEGER("id", true, false, true), 
							new CHARACTER("name", 30, false, true), 
							new CHARACTER("email", 30, false, false)
					}
			)
	);
}public static void insertData() {
	executeStatement(
			new Insert(
					"contact", 
					new String[] {"name", "email"}, 
					new String[] {"Kevin", "kev.mac@ucd.ie"}
			)
			
	);
	executeStatement(
			new Insert(
					"contact", 
					new String[] {"name", "email"}, 
					new String[] {"Noreen", "nor.len@ucd.ie"}
			)
	);
	executeStatement(
			new Insert(
					"contact", 
					new String[] {"name", "email"}, 
					new String[] {"bob", "kev.bob@ucd.ie"}
			)
	);
}
public static void selectTable() {
	executeStatement(new Select("contact", new String[] { "*" }));
}

public static void selectRow(){
	executeStatement(new Select("contact", new String[] {"id","name","email"}, new LessThan("id","2")));
}
}