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
    /**
     * @param messageStatus a message to be returned indicating if te status of the response
     */
    public void setMessageStatus(String messageStatus);
    /**
     * @param messageStatus a message to be returned indicating if te status of the response
     */
    public String getMessageStatus();
    /**
     * @param data a list of entries associated with the query
     */
    public void setMessageData(List<DataType> data);
    /**
     * @param data a list of entries associated with the query
     */
    public List<DataType> getMessageData();
}
