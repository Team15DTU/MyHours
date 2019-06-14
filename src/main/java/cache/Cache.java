package cache;

import dto.worker.IWorkerDTO;

import java.util.HashMap;

/**
 * @author Rasmus Sander Larsen
 */

//TODO: Elements in cache needs to be cleaned up from time to time

public class Cache implements ICache {

    /*
    -------------------------- Fields --------------------------
     */
    
    private HashMap<String, IWorkerDTO> cache;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public Cache () {

        cache = new HashMap<>();

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public HashMap<String, IWorkerDTO> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, IWorkerDTO> cache) {
        this.cache = cache;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public void addIWorkerDTOToCache (IWorkerDTO iWorkerDTO) {

        cache.put(iWorkerDTO.getEmail(), iWorkerDTO);

    }

    public IWorkerDTO getIWorkerDTOFromCache (String email) {

        return cache.get(email);

    }

    public void removeIWorkerDTOFromCache (String email) {
        cache.remove(email);
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
