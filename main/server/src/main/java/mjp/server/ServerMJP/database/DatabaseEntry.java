/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mjp.server.ServerMJP.database;

/**
 * Class that will be inherited by any class who's instances will represent entries on the database tables.
 * @author Joan Renau Valls
 * @param <IdType>The type of the entry id
 */
public interface DatabaseEntry<IdType> {
    abstract IdType getId();
    abstract void setId(IdType id);
}
