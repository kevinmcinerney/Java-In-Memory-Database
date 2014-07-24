package mapsql.sql.test;

import mapsql.sql.condition.Equals;
import mapsql.sql.condition.GreaterThan;
import mapsql.sql.condition.GreaterThanOrEqual;
import mapsql.sql.condition.LessThan;
import mapsql.sql.condition.LessThanOrEqual;
import mapsql.sql.condition.Like;
import mapsql.sql.condition.OrCondition;
import mapsql.sql.core.Condition;
import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.field.CHARACTER;
import mapsql.sql.field.INTEGER;
import mapsql.sql.statement.CreateTable;
import mapsql.sql.statement.Delete;
import mapsql.sql.statement.DropTable;
import mapsql.sql.statement.Insert;
import mapsql.sql.statement.Select;
import mapsql.sql.statement.Update;

public class ExampleTestProgram {
	static SQLManager manager = new SQLManager();
	
	public static void main(String[] args) {
		createTableStatement();

		showTables();
		insertData();
		selectTable();

		//insertPartialData();
		selectTable();
		
		updateData();
//		updateOrData();
		selectTable();

		deleteData();
		selectTable();
		
		dropTable();
		showTables();
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
		System.out.println("Create Table");
		executeStatement(
				new CreateTable(
						"contact", 
						new Field[] {
								new INTEGER("id", true, false, true), 
								new CHARACTER("name", 30, false, true), 
								new CHARACTER("email", 30, false, true),
								//new CHARACTER("age",30, false, true)
						}
				)
			);
		System.out.println("Create Table");
	 /**executeStatement(
				new CreateTable(
						"contacts2",
						new Field[] {
								new INTEGER ("id",true,false,true),
								new CHARACTER("name",30,false,true),
								new CHARACTER("email", 50, false, true),
								new INTEGER("int", true,false,true)
				}
			)
		);*/
	}
	
	
	public static void showTables() {
		System.out.println("Show Tables");
		executeStatement(new Select("mapsql.tables", new String[] { "*" }));
	}
	
	public static void selectTable() {
		System.out.println("Select Tables");
		executeStatement(new Select("contact", new String[] { "*" }));
		//executeStatement(new Select("contacts2", new String[] { "*" }));
	}
	
	public static void dropTable() {
		System.out.println("Drop Tables");
		executeStatement(new DropTable("contact"));
	}
	
	public static void insertData() {
		System.out.println("Insert Data");
		executeStatement(
				new Insert(
						"contact", 
						new String[] {"name", "email"}, 
						new String[] {"Rem", "rem.collier@ucd.ie"}
				)
				
		);
		executeStatement(
				new Insert(
						"contact", 
						new String[] {"name", "email"}, 
						new String[] {"Kev", "kev.mac@ucd.ie"}
				)
		);
		/**executeStatement(
				new Insert(
						"contacts2", 
						new String[] {"name","email","int"},//test check-for-not-nulls here
						new String[] {"kevin","k@mail.com","39"}
						
				)
		);
		executeStatement(
				new Insert(
						"contacts2", 
						new String[] {"name"}, 
						new String[] {"Mary"}
				)
		);**/
	}

	public static void insertPartialData() {
		System.out.println("Insert Partil Data");
		executeStatement(new Insert("contact", new String[] {"name"}, new String[] {"Henry"}));
	}

	public static void updateData() {
		System.out.println("update Data");
		executeStatement(
				new Update(
						"contact", 
						new String[] {"name","email"}, 
						new String[] {"John","henry.mcloughlin@ucd.ie"}, 
						new Equals("id", "2")
				)
		);
	}
	
	public static void updateOrData() {
		System.out.println("updateOrData");
		executeStatement(
				new Update(
						"contact", 
						new String[] {"email"}, 
						new String[] {"henry.mcloughlin@ucd.ie"}, 
						new OrCondition(
								new Equals("id", "1"), 
								new Equals("id", "2")
						)
				)
		);
	}
	
	public static void deleteData() {
		System.out.println("deleteData");
		executeStatement(new Delete("contact", new Like("name", "%oh%")));
	}
	
	public static void selectTableWithColumns() {
		System.out.println("selectTableWithColumns");
		executeStatement(new Select("contact", new String[] { "id", "name" }));
	}
}
