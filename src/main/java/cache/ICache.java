package cache;

import DTOs.worker.IWorkerDTO;

import java.util.HashMap;

/**
 * @author Rasmus Sander Larsen
 */
public interface ICache {
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    HashMap<String, IWorkerDTO> getCache();

    void setCache(HashMap<String, IWorkerDTO> cache);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    void addIWorkerDTOToCache (IWorkerDTO iWorkerDTO);

    IWorkerDTO getIWorkerDTOFromCache (String email);

    void removeIWorkerDTOFromCache (String email);

}
