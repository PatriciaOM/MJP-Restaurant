/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mjp.server.queryData.crud;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author twiki
 * @param <ItemsType> A database entry type
 * @param <RepoType> The database repository for accessing the table of IemsType
 */
public interface GetInfo<ItemsType, RepoType> {
    public List<ItemsType>  findAllItems(RepoType repository);
    
    default List<ItemsType> convertIterableToList(Iterable<ItemsType> iterable) {
        List<ItemsType> ret = new ArrayList();
        for (ItemsType item : iterable){
            ret.add(item);
        }
        return ret;
    }
}
