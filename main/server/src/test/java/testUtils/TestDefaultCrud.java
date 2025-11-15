/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.List;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.dish.DishGetResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static testUtils.TestDefault.printTestName;

/**
 *
 * @author twiki
 */
public abstract class TestDefaultCrud<
        DatabaseIdType,
        ItemType extends DatabaseEntry<DatabaseIdType>,
        CreateResponseType extends CrudResponse<ItemType>
    > extends TestDefault {
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass

    protected abstract Credentials getUserCredentials();
    protected abstract Credentials getAdminCredentials();
    protected abstract ItemType getInitialItem();
    protected abstract ItemType getUpdatedItem();
    protected abstract List<ItemType> getAllTestItems();
    protected abstract ItemType getNoExistingItem();  // This item is not  returned in getAllTestItems();
    public abstract AuthorizedQueryInfo<ItemType> generateCreateRequest(String sessionToken, ItemType entry);
    public abstract AuthorizedQueryInfo<DatabaseIdType> generateGetRequest(String sessionToken, DatabaseIdType entryId);
    public abstract AuthorizedQueryInfo<ItemType> generateUpdateRequest(String sessionToken, ItemType entry);
    public abstract AuthorizedQueryInfo<DatabaseIdType> generateDeleteRequest(String sessionToken, DatabaseIdType entryId);
    
    public abstract String getResourceUri();
       
    public String makeResourceUrl(String crudAction) {
        return super.makeUrl(getResourceUri()) + crudAction;
    }
    
    protected void basicSetup(String testname){
        printTestName(testname);
        Credentials userCred = getUserCredentials();
        Credentials adminCred = getAdminCredentials();

        getUserCredentials().setSessionToken(this.login(userCred.getUsername(), userCred.getPassword()));
        getAdminCredentials().setSessionToken(this.login(adminCred.getUsername(), adminCred.getPassword()));
        System.out.println("usersSessionToken=" + userCred.getSessionToken());
        System.out.println("adminSessionToken=" + adminCred.getSessionToken());
        User user = this.getUserBySessionToken(userCred.getSessionToken());
        User admin = this.getUserBySessionToken(adminCred.getSessionToken());
        System.out.println("User user to json: " + gson.toJson(user));
        System.out.println("Admin user to json: " + gson.toJson(admin));
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN);
    }
    
    protected void createAllItems(String testName, Class<CreateResponseType> responseClazz){
        List<ItemType> allItems = getAllTestItems();
        printTestName(testName);
        String url = makeResourceUrl("/create");
        AuthorizedQueryInfo info;
        
        for (ItemType item : allItems){
            assertNotNull(getAdminCredentials().getSessionToken());
            info = generateCreateRequest(getAdminCredentials().getSessionToken(), item);
            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            CreateResponseType createReponse = gson.fromJson(response.getBody(), responseClazz);
            assertThat(createReponse.getMessageStatus()).isEqualTo("Success");
            assertThat(createReponse.getMessageData().size()).isEqualTo(1);
            ItemType itemResponse = createReponse.getMessageData().get(0);
            assertNotNull(itemResponse.getId());
            item.setId(itemResponse.getId());
            assertThat(gson.toJson(itemResponse)).isEqualTo(gson.toJson(item));
        }
        assertNotNull(allItems.get(0).getId());
        assertNotNull(getInitialItem().getId());
    }
  
    
    protected <GetResponseType extends CrudResponse<ItemType>> void getItemById(String testName, Class<GetResponseType> responseClazz) {
        printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getUserCredentials().getSessionToken(), getInitialItem().getId()); 
        assertNotNull(getInitialItem().getId());
        assertNotNull(info.getMessageData());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetResponseType responseMessage = gson.fromJson(response.getBody(), responseClazz);
        List<ItemType> items = responseMessage.getMessageData();
        assertThat(items.size()).isEqualTo(1);
        assertThat(gson.toJson(items.get(0))).isEqualTo(gson.toJson(getInitialItem()));
    }
        
    protected <GetResponseType extends CrudResponse<ItemType>> void getNoExistingItemById(String testName, Class<GetResponseType> responseClazz) {
        printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getUserCredentials().getSessionToken(), getNoExistingItem().getId());
        assertNotNull(getNoExistingItem().getId());
        assertNotNull(info.getMessageData());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); 
    }
    
    protected <UpdateResponseType, GetResponseType extends CrudResponse<ItemType>> void updateItem(String testName, Class<UpdateResponseType> responseClazz) {
        printTestName(testName);
        String url = makeResourceUrl("/update");
        getUpdatedItem().setId(getInitialItem().getId());
        AuthorizedQueryInfo info = generateUpdateRequest(getAdminCredentials().getSessionToken(), getUpdatedItem());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);  // TODO maybe should check that it returns the updated item?
        
        AuthorizedQueryInfo getInfo = generateGetRequest(getUserCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> getResponse = makePostRequest(makeResourceUrl("/get"), getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DishGetResponse getResponseObject = gson.fromJson(getResponse.getBody(), DishGetResponse.class);   //TODO This is wrong as fuck
        System.out.println(gson.toJson(getResponseObject));
        List<Dish> returnedDishesList = getResponseObject.getDishes();
        assertThat(returnedDishesList.size()).isEqualTo(1);
        Dish returnedDish = returnedDishesList.get(0);
  
        assertNotNull(getUpdatedItem());
        assertNotNull(getUpdatedItem().getId());
        assertNotNull(returnedDish);
        assertThat(gson.toJson(returnedDish)).isEqualTo(gson.toJson(getUpdatedItem()));  
        
    }
    
    protected <GetResponseType extends CrudResponse<ItemType>> void getItemToDelete(String testName, Class<GetResponseType> responseClazz) {
                printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getUserCredentials().getSessionToken(), getUpdatedItem().getId()); 
        assertNotNull(info.getMessageData());
        assertNotNull(info.getMessageData());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
     protected <DeleteResponseType extends CrudResponse<ItemType>> void deleteItem(String testName, Class<DeleteResponseType> responseClazz) {
        printTestName("deleteDish");
        String url = makeResourceUrl("/delete");
        AuthorizedQueryInfo info = generateDeleteRequest(getAdminCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> delResp = makePostRequest(url, info);
        DeleteResponseType delRespObject = gson.fromJson("", responseClazz);
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        String getUrl = makeResourceUrl("/get");
        AuthorizedQueryInfo getInfo = generateGetRequest(getUserCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> getResponse = makePostRequest(getUrl, getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        
        delResp = makePostRequest(url, info);
        delRespObject = gson.fromJson("", responseClazz);
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
     
        protected <GetResponseType extends CrudResponse<ItemType>> void getDeletedItem(String testName, Class<GetResponseType> responseClazz) {
                printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getUserCredentials().getSessionToken(), getUpdatedItem().getId()); 
        assertNotNull(info.getMessageData());
        assertNotNull(info.getMessageData());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
