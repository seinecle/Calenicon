package CKANClient;

import DatahubMethods.ResourcePropertiesIO;
import com.google.gson.Gson;

import java.util.Map;
import java.util.HashMap;

/**
 * The primary interface to this package the Client class is responsible for
 * managing all interactions with a given connection.
 *
 * @author Ross Jones <ross.jones@okfn.org>
 * @version 1.7
 * @since 2012-05-01
 */
public final class Client {

    private Connection _connection = null;

    /**
     * Constructs a new Client for making requests to a remote CKAN instance.
     *
     * @param c A Connection object containing info on the location of the CKAN
     * Instance.
     * @param apikey A user's API Key sent with every request.
     */
    public Client(Connection c, String apikey) {
        this._connection = c;
        this._connection.setApiKey(apikey);
    }

    /**
     * Loads a JSON string into a class of the specified type.
     */
    protected <T> T LoadClass(Class<T> cls, String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, cls);
    }

    /**
     * Handles error responses from CKAN
     *
     * When given a JSON string it will generate a valid CKANException
     * containing all of the error messages from the JSON.
     *
     * @param json The JSON response
     * @param action The name of the action calling this for the primary error
     * message.
     * @throws A CKANException containing the error messages contained in the
     * provided JSON.
     */
    private void HandleError(String json, String action)
            throws CKANException {

        CKANException exception = new CKANException("Errors occured performing: " + action);

        HashMap hm = LoadClass(HashMap.class, json);
        Map<String, Object> m = (Map<String, Object>) hm.get("error");
        for (Map.Entry<String, Object> entry : m.entrySet()) {
            if (entry.getKey().startsWith("_")) {
                continue;
            }
            exception.addError(entry.getValue() + " - " + entry.getKey());
        }
        throw exception;
    }

    /**
     * Retrieves a dataset
     *
     * Retrieves the dataset with the given name, or ID, from the CKAN
     * connection specified in the Client constructor.
     *
     * @param name The name or ID of the dataset to fetch
     * @returns The Dataset for the provided name.
     * @throws A CKANException if the request fails
     */
    public Dataset getDataset(String name)
            throws CKANException {
        String returned_json = this._connection.Post("/api/3/action/package_show",
                "{\"id\":\"" + name + "\"}");
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            HandleError(returned_json, "getDataset");
        }
        return r.result;
    }

    /**
     * Deletes a dataset
     *
     * Deletes the dataset specified with the provided name/id
     *
     * @param name The name or ID of the dataset to delete
     * @throws A CKANException if the request fails
     */
    public void deleteDataset(String name)
            throws CKANException {
        String returned_json = this._connection.Post("/api/3/action/package_delete",
                "{\"id\":\"" + name + "\"}");
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            HandleError(returned_json, "deleteDataset");
        }
    }

    public void deleteResource(String name)
            throws CKANException {
        String returned_json = this._connection.Post("/api/3/action/resource_delete",
                "{\"id\":\"" + name + "\"}");
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            HandleError(returned_json, "deleteDataset");
        }
    }

    /**
     * Creates a dataset on the server
     *
     * Takes the provided dataset and sends it to the server to perform an
     * create, and then returns the newly created dataset.
     *
     * @param dataset A dataset instance
     * @returns The Dataset as it now exists
     * @throws A CKANException if the request fails
     */
    public Dataset createDataset(Dataset dataset)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(dataset);
        System.out.println(data);
        String returned_json = this._connection.Post("/api/3/action/package_create",
                data);
        System.out.println(returned_json);
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            // This will always throw an exception
            HandleError(returned_json, "createDataset");
        }
        return r.result;
    }

    public Dataset updateDataset(Dataset dataset)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(dataset);
        System.out.println(data);
        String returned_json = this._connection.Post("/api/3/action/package_update",
                data);
        System.out.println(returned_json);
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            // This will always throw an exception
            HandleError(returned_json, "updateDataset");
        }
        return r.result;
    }

    public Dataset updateUserRights(String user, String dataset, String role)
            throws CKANException {
        Gson gson = new Gson();
        String[] fields = new String[3];
        fields[0] = user;
        fields[1] = dataset;
        fields[2] = role;

        String data = gson.toJson(fields);
        data = "{\"user\":" + user + ",\"domain_object\":" + dataset + ",\"roles\":[" + role + "]}";
        System.out.println(data);
        String returned_json = this._connection.Post("/api/3/action/user_role_update",
                data);
        System.out.println(returned_json);
        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
        if (!r.success) {
            // This will always throw an exception
            HandleError(returned_json, "createDataset");
        }
        return r.result;
    }

    public void deleteDataset(Dataset dataset)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(dataset);
        System.out.println(data);
        String returned_json = this._connection.Post("/api/3/action/package_delete",
                data);
        System.out.println(returned_json);
    }

    /**
     * Retrieves a group
     *
     * Retrieves the group with the given name, or ID, from the CKAN connection
     * specified in the Client constructor.
     *
     * @param name The name or ID of the group to fetch
     * @returns The Group instance for the provided name.
     * @throws A CKANException if the request fails
     */
    public Group getGroup(String name)
            throws CKANException {
        String returned_json = this._connection.Post("/api/3/action/group_show",
                "{\"id\":\"" + name + "\"}");
        Group.Response r = LoadClass(Group.Response.class, returned_json);
        if (!r.success) {
            HandleError(returned_json, "getGroup");
        }
        return r.result;
    }

    /**
     * Deletes a Group
     *
     * Deletes the group specified with the provided name/id
     *
     * @param name The name or ID of the group to delete
     * @throws A CKANException if the request fails
     */
    public void deleteGroup(String name)
            throws CKANException {
        String returned_json = this._connection.Post("/api/3/action/group_delete",
                "{\"id\":\"" + name + "\"}");
        Group.Response r = LoadClass(Group.Response.class, returned_json);
        if (!r.success) {
            HandleError(returned_json, "deleteGroup");
        }
    }

    /**
     * Creates a Group on the server
     *
     * Takes the provided Group and sends it to the server to perform an create,
     * and then returns the newly created Group.
     *
     * @param group A Group instance
     * @returns The Group as it now exists on the server
     * @throws A CKANException if the request fails
     */
    public Group createGroup(Group group)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(group);
        String returned_json = this._connection.Post("/api/3/action/package_create",
                data);
        Group.Response r = LoadClass(Group.Response.class, returned_json);
        if (!r.success) {
            // This will always throw an exception
            HandleError(returned_json, "createGroup");
        }
        return r.result;
    }

    public String updateResource(Resource resource)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(resource);
//        System.out.println("data: " + data);
        String returned_json = this._connection.Post("/api/3/action/resource_update",
                data);
        String r = returned_json;
        System.out.println("r: " + r);
        return r;
    }

    public String createResource(String dataset, Map<String, String> resourceProperties)
            throws CKANException {
        String resourcePropertiesString = ResourcePropertiesIO.mapPropertiesToMarkDown(resourceProperties, dataset);
        Gson gson = new Gson();
//        System.out.println("postString: " + postString.toString());
//        String works = "{\"package_id\":\"" + "testapi" + "\", \"url\":\"http://en.wikipedia.org/wiki/File:Lake_mapourika_NZ.jpeg\", \"name\":\"allo\"}";
//        System.out.println("works: " + works);
        String returned_json = this._connection.Post("/api/3/action/resource_create", resourcePropertiesString);
//        String returned_json = this._connection.Post("/api/action/resource_create", "{\"package_id\":\"" + "testapi" + "\", \"url\":\"http://en.wikipedia.org/wiki/File:Lake_mapourika_NZ.jpeg\", \"name\":\"allo\"}");
//        String returned_json = this._connection.Post("/api/action/resource_create", works);
        String r = returned_json;
//        System.out.println("r: " + r);
        return r;
    }

    public void deleteResource(Resource resource)
            throws CKANException {
        Gson gson = new Gson();
        String data = gson.toJson(resource);
        System.out.println("data: " + data);

        String returned_json = this._connection.Post("/api/3/action/delete/resource_delete",
                data);
        System.out.println("response: " + returned_json);
//        Dataset.Response r = LoadClass(Dataset.Response.class, returned_json);
//        if (!r.success) {
//            System.out.println("did not work out!");;
//        }
    }

    public String uploadFile()
            throws CKANException {

        String returned_json = this._connection.MuliPartPost("", "");

        System.out.println("r: " + returned_json);
        return returned_json;
    }

    /**
     * Uses the provided search term to find datasets on the server
     *
     * Takes the provided query and locates those datasets that match the query
     *
     * @param query The search terms
     * @returns A SearchResults object that contains a count and the objects
     * @throws A CKANException if the request fails
     */
    public Dataset.SearchResults findDatasets(String query)
            throws CKANException {

        String returned_json = this._connection.Post("/api/3/action/package_search",
                "{\"q\":\"" + query + "\"}");
        Dataset.SearchResponse sr = LoadClass(Dataset.SearchResponse.class, returned_json);
        if (!sr.success) {
            // This will always throw an exception
            HandleError(returned_json, "findDatasets");
        }
        return sr.result;
    }
}
