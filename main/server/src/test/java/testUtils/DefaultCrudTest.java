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
import mjp.server.ServerMJP.database.DishRepository;
import mjp.server.ServerMJP.database.User;
import mjp.server.ServerMJP.model.SessionManager;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.dish.DishCreateInfo;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.dish.DishCreateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import testUtils.Credentials;
import static testUtils.TestDefaultClass.printTestName;

/**
 *
 * @author twiki
 */
public abstract class DefaultCrudTest<ItemType extends DatabaseEntry, ResponseType extends CrudResponse<ItemType>> extends TestDefaultClass {
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
//
//    protected class Credentials {
//        String username;
//        String password;
//        String sessionToken;
//
//
//        public String getUsername() {return username;}
//
//        public String getPassword() {return password;}
//
//        public String getSessionToken() {return sessionToken;}
//
//        public void setUsername(String username) {this.username = username;}
//
//        public void setPassword(String password) {this.password = password;}
//
//        public void setSessionToken(String sessionToken) {this.sessionToken = sessionToken;}
//    }
    
    
    
    protected abstract Credentials getUserCredentials();
    protected abstract Credentials getAdminCredentials();
    protected abstract ItemType getInitalItem();
    protected abstract ItemType getUpdatedItem();
    protected abstract List<ItemType> getAllTestItems();
    public abstract AuthorizedQueryInfo<ItemType> createRequest(String sessionToken, ItemType entry);
//    public abstract CrudResponse<ItemType> createResponse(String Message, List<ItemType> entries);
    
    
    
    
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
    
    protected void createAllItems(String testname, Class<ResponseType> responseClazz){
        List<ItemType> allItems = getAllTestItems();
                printTestName("createAllDishes");
        String url = makeUrl("/dish/create");//
//        Dish dish = initialDish;
        AuthorizedQueryInfo info;
        
        for (ItemType item : allItems){
            assertNotNull(getAdminCredentials().getSessionToken());
            info = createRequest(getAdminCredentials().getSessionToken(), item);
            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            ResponseType createReponse = gson.fromJson(response.getBody(), responseClazz);
            assertThat(createReponse.getMessageStatus()).isEqualTo("Success");
            assertThat(createReponse.getMessageData().size()).isEqualTo(1);
            ItemType dishResponse = createReponse.getMessageData().get(0);
            assertNotNull(dishResponse.getId());
            item.setId(dishResponse.getId());
            assertThat(gson.toJson(dishResponse)).isEqualTo(gson.toJson(item));
        }
        assertNotNull(allItems.get(0).getId());
        assertNotNull(getInitalItem().getId());
    }
//    
//        protected void updateAllItems(String testname, Class<ResponseType> responseClazz){
//            ItemType updatedDish = getUpdatedItem();
//            ItemType initialDish = getInitalItem();
//            
//            printTestName("updateDish");
//            String url = makeUrl("/dish/update");
//            updatedDish.setId(initialDish.getId());
//            AuthorizedQueryInfo info = createRequest(getAdminCredentials().getSessionToken(), updatedDish);
//            ResponseEntity<String> response = makePostRequest(url, info);
//            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//            AuthorizedQueryInfo getInfo = createRequest(getAdminCredentials().getSessionToken(), updatedDish.getId());
//            ResponseEntity<String> getResponse = makePostRequest("/dish/get", getInfo);
//            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//            DishGetResponse getResponseObject = gson.fromJson(getResponse.getBody(), DishGetResponse.class);
//            System.out.println(gson.toJson(getResponseObject));
//            List<Dish> returnedDishesList = getResponseObject.getDishes();
//            assertThat(returnedDishesList.size()).isEqualTo(1);
//            Dish returnedDish = returnedDishesList.get(0);
//
//            assertNotNull(updatedDish.getId());
//            assertNotNull(returnedDish);
//            assertNotNull(updatedDish);
//            assertThat(gson.toJson(returnedDish)).isEqualTo(gson.toJson(updatedDish));       
//        }
   
}
