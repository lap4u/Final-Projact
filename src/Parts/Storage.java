package Parts;

public class Storage {

    public Storage(boolean i_isSSD, int i_GB) {
        m_isSSD = i_isSSD;
        m_GB = i_GB;
    }

    public boolean isM_isSSD() {
        return m_isSSD;
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
