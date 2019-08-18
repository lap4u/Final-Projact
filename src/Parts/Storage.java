package Parts;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;



@Entity
public class Storage implements Serializable {


private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;



    public Storage()
    {



    }
    

    public Storage(boolean i_isSSD, int i_GB) {
        m_isSSD = i_isSSD;
        m_GB = i_GB;
    }





    public void setM_isSSD(boolean m_isSSD) {
        this.m_isSSD = m_isSSD;
    }
    public void setM_GB(int m_GB) {
        this.m_GB = m_GB;
    }

    public int getM_GB() {
        return m_GB;
    }
    public boolean getM_SSD() {
        return m_isSSD;
    }


    private boolean m_isSSD;
    private int m_GB;




}
