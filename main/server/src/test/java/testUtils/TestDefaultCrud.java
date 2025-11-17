/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.responseData.CrudResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static testUtils.TestDefault.printTestName;

/**
 *
 * @author Joan Renau Valls
 */
public abstract class TestDefaultCrud<
        DatabaseIdType,
        ItemType extends DatabaseEntry<DatabaseIdType>,
        CreateResponseType extends CrudResponse<ItemType>,
        GetResponseType extends CrudResponse<ItemType>,
        UpdateResponseType extends CrudResponse<ItemType>,
        DeleteResponseType extends CrudResponse<ItemType>
    > extends TestDefault {
    Gson gson = (new GsonBuilder())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass

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
    
    public abstract Class<CreateResponseType> getCreateResponseClass();
    public abstract Class<GetResponseType> getGetResponseClass();
    public abstract Class<UpdateResponseType> getUpdateResponseClass();
    public abstract Class<DeleteResponseType> getDeleteResponseClass();
    
    public abstract Credentials getCreateCredentials();
    public abstract Credentials getGetCredentials();
    public abstract Credentials getUpdateCredentials();
    public abstract Credentials getDeleteCredentials();
    
    public abstract String getResourceUri();
    public abstract String getClassName();
       
    public String makeResourceUrl(String crudAction) {
        return super.makeUrl(getResourceUri()) + crudAction;
    }
    
    protected void createItemBasicTests(String testname, String sessionToken) {
        AuthorizedQueryInfo createInfo =  generateCreateRequest(getAdminCredentials().getSessionToken(), getInitialItem());
        this.basicRequestTests(
            "create" + getClassName(),
            makeResourceUrl("/create"),
            createInfo,
            getInitialItem(),
            sessionToken
        );
    }
    
    protected void getItemBasicTests(String testname, String sessionToken) {
        AuthorizedQueryInfo getInfo =  generateCreateRequest(getUserCredentials().getSessionToken(), getInitialItem());
        this.basicRequestTests(
            "get" + getClassName() + "BasicTests",
            makeResourceUrl("/get"),
            getInfo,
            null,
            sessionToken
        );
    }
    
    
    protected void updateItemBasicTests(String testname, String sessionToken) {
        AuthorizedQueryInfo info =  generateUpdateRequest(getAdminCredentials().getSessionToken(), getUpdatedItem());
            this.basicRequestTests(
                "update" + getClassName() + "BasicTests",
            makeResourceUrl("/update"),
                info,
                getUpdatedItem(),
                sessionToken
            );
    }
    
    protected void deleteItemBasicTests(String testname, String sessionToken){
        AuthorizedQueryInfo info =  generateDeleteRequest(getAdminCredentials().getSessionToken(), getInitialItem().getId());
        this.basicRequestTests(
            "delete" + getClassName() + "BasicTests",
            makeResourceUrl("/delete"),
            info,
            getInitialItem().getId(),
            sessionToken
        );
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
    
    protected void createAllItems(String testName){
        List<ItemType> allItems = getAllTestItems();
        printTestName(testName);
        String url = makeResourceUrl("/create");
        AuthorizedQueryInfo info;
        
        for (ItemType item : allItems){
            assertNotNull(getCreateCredentials().getSessionToken());
            info = generateCreateRequest(getCreateCredentials().getSessionToken(), item);
            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            System.out.println("The response body is: " + response.getBody());
            CreateResponseType createReponse = gson.fromJson(response.getBody(), getCreateResponseClass());
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
  
    
    protected  void getItemById(String testName) {
        printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getGetCredentials().getSessionToken(), getInitialItem().getId()); 
        assertNotNull(getInitialItem().getId());
        assertNotNull(info.getMessageData());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetResponseType responseMessage = gson.fromJson(response.getBody(), getGetResponseClass());
        List<ItemType> items = responseMessage.getMessageData();
        assertThat(items.size()).isEqualTo(1);
        assertThat(gson.toJson(items.get(0))).isEqualTo(gson.toJson(getInitialItem()));
    }
        
    protected void getNoExistingItemById(String testName) {
        printTestName(testName);
        String url = makeResourceUrl("/get");
        assertNotNull(getNoExistingItem().getId());
        AuthorizedQueryInfo info = generateGetRequest(getGetCredentials().getSessionToken(), getNoExistingItem().getId());
        assertNotNull(info.getMessageData());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); 
    }
    
    protected void updateItem(String testName) {
        printTestName(testName);
        String url = makeResourceUrl("/update");
        getUpdatedItem().setId(getInitialItem().getId());
        AuthorizedQueryInfo info = generateUpdateRequest(getUpdateCredentials().getSessionToken(), getUpdatedItem());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);  // TODO maybe should check that it returns the updated item?
        
        AuthorizedQueryInfo getInfo = generateGetRequest(getGetCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> getResponse = makePostRequest(makeResourceUrl("/get"), getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetResponseType getResponseObject = gson.fromJson(getResponse.getBody(), getGetResponseClass());   //TODO This is wrong as fuck
        System.out.println(gson.toJson(getResponseObject));
        List<ItemType> returnedItemsList = getResponseObject.getMessageData();
        assertThat(returnedItemsList.size()).isEqualTo(1);
        ItemType returnedItem = returnedItemsList.get(0);
  
        assertNotNull(getUpdatedItem());
        assertNotNull(getUpdatedItem().getId());
        assertNotNull(returnedItem);
        assertThat(gson.toJson(returnedItem)).isEqualTo(gson.toJson(getUpdatedItem()));  
    }
    
    protected void getItemToDelete(String testName) {
                printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getGetCredentials().getSessionToken(), getUpdatedItem().getId()); 
        assertNotNull(info.getMessageData());
        assertNotNull(info.getMessageData());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
     protected void deleteItem(String testName) {
        printTestName(testName);
        String url = makeResourceUrl("/delete");
        AuthorizedQueryInfo info = generateDeleteRequest(getDeleteCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> delResp = makePostRequest(url, info);
        DeleteResponseType delRespObject = gson.fromJson("", getDeleteResponseClass());
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        String getUrl = makeResourceUrl("/get");
        AuthorizedQueryInfo getInfo = generateGetRequest(getGetCredentials().getSessionToken(), getUpdatedItem().getId());
        ResponseEntity<String> getResponse = makePostRequest(getUrl, getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        
        delResp = makePostRequest(url, info);
        delRespObject = gson.fromJson("", getDeleteResponseClass());
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
     
        protected void getDeletedItem(String testName) {
                printTestName(testName);
        String url = makeResourceUrl("/get");
        AuthorizedQueryInfo info = generateGetRequest(getGetCredentials().getSessionToken(), getUpdatedItem().getId()); 
        assertNotNull(info.getMessageData());
        assertNotNull(info.getMessageData());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
