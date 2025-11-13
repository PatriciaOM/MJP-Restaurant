/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

/**
 *
 * @author twiki
 */
public abstract class  DataEntry<IdType> {
    abstract IdType getId();
    abstract void setId(IdType id);
}
