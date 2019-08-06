package Positions;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {

    @JsonProperty("name")
    private String m_ModelName;
   @JsonProperty("position")
    private int m_PositionNumber;

    public Position(String i_ModelName, int i_PositionNumber) {
       m_ModelName = i_ModelName;
       m_PositionNumber = i_PositionNumber;
    }

    public String getM_ModelName() {
        return m_ModelName;
    }

    public void setM_ModelName(String m_ModelName) {
        this.m_ModelName = m_ModelName;
    }

    public int getM_PositionNumber() {
        return m_PositionNumber;
    }

    public void setM_PositionNumber(int m_PositionNumber) {
        this.m_PositionNumber = m_PositionNumber;
    }

}
