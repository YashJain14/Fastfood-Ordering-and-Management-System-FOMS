

package interfaces;
/**
Interface for database controller classes.
This interface defines methods for reading data from a file (readDB) and writing data to a file (writeDB).
*/
public interface DBController {

	public boolean readDB(String filename);
	public boolean writeDB(String filename);
}