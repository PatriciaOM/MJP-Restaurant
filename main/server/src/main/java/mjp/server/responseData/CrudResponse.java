/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mjp.server.responseData;

import java.util.List;

/**
 *
 * @author twiki
 * @param <DataType>
 */
public interface CrudResponse <DataType>{
    public void setMessageStatus(String messageStatus);
    public String getMessageStatus();
    public void setMessageData(List<DataType> data);
    public List<DataType> getMessageData();
}
