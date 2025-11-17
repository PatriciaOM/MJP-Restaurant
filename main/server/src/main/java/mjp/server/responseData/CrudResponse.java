/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mjp.server.responseData;

import java.util.List;

/**
 * This holds the data returned on a request of create get update or delete
 * @author Joan Renau Valls
 * @param <DataType> The type of the data being returned primarily is an instance of a class defining a database table
 */
public interface CrudResponse <DataType>{
    public void setMessageStatus(String messageStatus);
    public String getMessageStatus();
    public void setMessageData(List<DataType> data);
    public List<DataType> getMessageData();
}
