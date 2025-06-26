package app.utils.db;

import java.util.Date;

/**
 * @author Eldair F. Dornelles
 */
public class DBException extends Exception {
    
    Date dataTimeFaill;
    
    public DBException(Exception ex){
        super(ex);
        dataTimeFaill = new Date();
    }

    public DBException(String msg){
        super(msg);
        dataTimeFaill = new Date();
    }
    
    @Override
    public String getMessage(){
        return dataTimeFaill.toString() + '\n' + super.getMessage();
    } 
}
