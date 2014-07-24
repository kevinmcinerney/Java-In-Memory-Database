package mapsql.sql.condition;

import java.util.Map;

import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.TableDescription;
import mapsql.sql.field.CHARACTER;

public class Like extends AbstractCondition {
	private String column;
	private String value;
	
	public Like(String column, String value) {
		this.column = column;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(TableDescription description, Map<String, String> data) throws SQLException {
			
		int vStart = 1;
		int vEnd = value.length() -1;
		int cStart = 0;
		int cEnd = data.get(column).length() - 1;
		int count = 0;
		
		if(value.charAt(0) == '%' && value.charAt(value.length()-1) == '%'){
			for(int i = cStart; i <= cEnd;  i++){
				//System.out.println(data.get(column).charAt(i));
				if(data.get(column).toLowerCase().charAt(i) == value.toLowerCase().charAt(vStart)){
					for (int j = i, k = vStart; k <= vEnd && j <= cEnd; j++, k++){
						//System.out.println("\t" + data.get(column).charAt(j) + " AND " + value.charAt(k));
						if(data.get(column).toLowerCase().charAt(j) != value.toLowerCase().charAt(k)){
							continue;
						}
						count++;
						if( count == value.length() - 2){
							//System.out.println("\t\t Pattern Matched");
							return true;
						}
					}
					count = 0;
				}
			}
		}
		else if (value.charAt(0) == '%' && value.charAt(value.length()-1) != '%'){
			for(int i = cEnd, j = vEnd; i >= 0  && j > 0  ;  i--, j--){
				//System.out.println(data.get(column).charAt(i)  + " AND " + value.charAt(j));
				if (data.get(column).toLowerCase().charAt(i) != value.toLowerCase().charAt(j)){
					continue;					
				}
				count++;
				if(count == value.length() - 1){
					//System.out.println("\t\t Pattern Matched");
					return true;
				}
			}
		}
		else if ((value.charAt(0) != '%' && value.charAt(value.length()-1) == '%')){
			for (int i = 0; i < value.length()-1 && i < data.get(column).length(); i++){
				//System.out.println(data.get(column).toLowerCase().charAt(i)  + " AND " + value.toLowerCase().charAt(i));
				if (data.get(column).toLowerCase().charAt(i) != value.toLowerCase().charAt(i)){
					continue;
				}
				count++;
				if (count == value.length()-1){
					//System.out.println("\t\t Pattern Matched");
					return true;
				}
			}
		}
		return false;
	}
			
}

