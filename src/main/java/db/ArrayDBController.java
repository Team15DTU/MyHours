package db;

import dao.DALException;
import dto.activity.ActivityDTO;
import dto.activity.IActivityDTO;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.job.JobDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;
import dto.worker.WorkerHiberDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ArrayDBController implements IDBController {

    /*
    -------------------------- Fields --------------------------
     */
    
    private static ArrayList<IWorkerDTO> workerList;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ArrayDBController () {

        workerList = new ArrayList<>();

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public ArrayList<IWorkerDTO> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(ArrayList<IWorkerDTO> workerList) {
        this.workerList = workerList;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    @Override
    public void createWorker(IWorkerDTO workerDTO) {

        workerList.add(workerDTO);

    }

    @Override
    public IWorkerDTO getIWorkerDTO(String email) {

        IWorkerDTO workerDTOToReturn = new WorkerHiberDTO();

        for (IWorkerDTO workerDTO : workerList) {
            if (workerDTO.getEmail().equals(email)) {
                workerDTOToReturn = workerDTO;
                break;
            }
        }

        return workerDTOToReturn;
    }

    @Override
    public IWorkerDTO getIWorkerDTO(int id) {
        IWorkerDTO workerDTOToReturn = new WorkerHiberDTO();

        for (IWorkerDTO workerDTO : workerList ){
            if (workerDTO.getWorkerID() == id) {
                workerDTOToReturn = workerDTO;
                break;
            }
        }

        return workerDTOToReturn;
    }

    @Override
    public List<IWorkerDTO> getIWorkerDTOList() {
        return workerList;
    }

    @Override // TODO: Hvad skal det her bruges til?
    public List<IWorkerDTO> getIWorkerDTOList(int minID, int maxID) {
        return null;
    }

    @Override // TODO: Hvad skal det her bruges til?
    public List<IWorkerDTO> getIWorkerDTOList(String name) {
        return null;
    }

    @Override
    public synchronized boolean updateWorker(IWorkerDTO updatedWorkerDTO) {
        boolean success = false;

        int listSize = workerList.size();

        for (int i = 0; i < listSize; i++){
            if (workerList.get(i).getWorkerID() == updatedWorkerDTO.getWorkerID()){
                workerList.get(i).setFirstName(updatedWorkerDTO.getFirstName());
                workerList.get(i).setSurName(updatedWorkerDTO.getSurName());
                workerList.get(i).setEmail(updatedWorkerDTO.getEmail());
                if (updatedWorkerDTO.getBirthday() == null){
                    workerList.get(i).setBirthday(updatedWorkerDTO.getBirthday());
                } else {
                    workerList.get(i).setBirthday(null);
                }
                workerList.get(i).setPassword(updatedWorkerDTO.getPassword());

                success = true;
                break;
            }
        }

        return success;
    }

    @Override
    public synchronized boolean deleteWorker(String email) {

        return workerList.removeIf(workerDTO -> (workerDTO.getEmail().equals(email)));
    }

    @Override
    public void createEmployer(IEmployerDTO employer) {
        for (IWorkerDTO workerDTO : workerList) {
            if (workerDTO.getWorkerID() == employer.getWorkerID()) {
                workerDTO.getIEmployers().add(employer);
                break;
            }
        }
    }

    @Override
    public IEmployerDTO getIEmployerDTO(int id) {
        IEmployerDTO employerDTOToReturn = new EmployerDTO();

        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()){
                if (employerDTO.getEmployerID() == id){
                    employerDTOToReturn = employerDTO;
                    break;
                }
            }
        }

        return employerDTOToReturn;
    }

    @Override
    public List<IEmployerDTO> getIEmployerList() {
        List<IEmployerDTO> fullEmployerList = new ArrayList<>();
        for (IWorkerDTO workerDTO : workerList){
            fullEmployerList.containsAll(workerDTO.getIEmployers());
        }
        return fullEmployerList;
    }

    @Override //TODO: Ved ikke hvad det her skal bruges til?
    public List<IEmployerDTO> getIEmployerList(int minID, int maxID) {
        return null;
    }

    @Override //TODO:  Ved ikke hvad det her skal bruges til?
    public List<IEmployerDTO> getIEmployerList(String name) {
        return null;
    }

    @Override
    public boolean updateEmployer(IEmployerDTO updatedEmployerDTO) {
        boolean succes = false;

        for (IWorkerDTO workerDTO : workerList ) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()){
                if (employerDTO.getEmployerID() == updatedEmployerDTO.getEmployerID()){
                    employerDTO.setName(updatedEmployerDTO.getName());
                    employerDTO.setColor(updatedEmployerDTO.getColor());
                    employerDTO.setTelephone(updatedEmployerDTO.getTelephone());
                    succes = true;
                    break;
                }
            }
        }
        return succes;
    }

    @Override
    public boolean deleteEmployer(int employerID) {
        boolean success = false;

        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()) {
                if (employerDTO.getEmployerID() == employerID) {
                    workerDTO.getIEmployers().remove(employerDTO);
                    success = true;
                    break;
                }
            }
        }

        return success;
    }

    @Override
    public void createJob(IJobDTO job) {
        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()){
                if (job.getEmployerID() == employerDTO.getEmployerID()) {
                    employerDTO.getIJobList().add(job);
                    break;
                }
            }
        }
    }

    @Override
    public IJobDTO getIJobDTO(int id) {
        IJobDTO jobDTOToReturn = new JobDTO();
        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()){
                for (IJobDTO jobDTO : employerDTO.getIJobList()) {
                    if (jobDTO.getJobID() == id) {
                        jobDTOToReturn = jobDTO;
                        break;
                    }
                }
            }
        }
        return jobDTOToReturn;
    }

    @Override
    public List<IJobDTO> getIJobDTOList() {
        List<IJobDTO> fullJobList = new ArrayList<>();
        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()) {
                fullJobList.containsAll(employerDTO.getIJobList());
            }
        }
        return fullJobList;
    }

    @Override
    public List<IJobDTO> getIJobDTOList(int employerID) {
        List<IJobDTO> listToReturn = new ArrayList<>();
        for (IWorkerDTO workerDTO : workerList) {
            for (IEmployerDTO employerDTO : workerDTO.getIEmployers()){
                if (employerDTO.getEmployerID() == employerID) {
                    listToReturn = employerDTO.getIJobList();
                }
            }
        }
        return listToReturn;
     }

    @Override // TODO: Der her kan jeg ikke se hvad skal bruges til? Hvilket navn er det? Employer? Job? Worker?
    public List<IJobDTO> getIJobDTOList(String name) {
        return null;
    }

    @Override // TODO: Det her giver ikke mening?
    public List<IJobDTO> getIJobDTOList(double minSalary, double maxSalary) {
        return null;
    }

    @Override
    public boolean updateJob(IJobDTO jobDTO) {
        boolean success = false;
        for (IWorkerDTO workerDTO : workerList) {

        }


        return success;
    }

    @Override
    public boolean deleteJob(int jobID) {
        return false;
    }

    @Override
    public void createActivity(IActivityDTO activity) {

    }

    @Override
    public IActivityDTO getIActivity(int id) {
        return null;
    }

    @Override
    public List<IActivityDTO> getIActivityList() {
        return null;
    }

    @Override
    public List<IActivityDTO> getIActivityList(int jobID) {
        return null;
    }

    @Override
    public List<IActivityDTO> getIActivityList(Date date) {
        return null;
    }

    @Override
    public List<IActivityDTO> getIActivityList(double minVal, double maxVal) {
        return null;
    }

    @Override
    public boolean updateActivity(IActivityDTO activityDTO) {
        return false;
    }

    @Override
    public void deleteActivity(ActivityDTO activityDTO) throws DALException {

    }

    @Override
    public String setTimeZoneFromSQLServer() {
        return null;
    }

    @Override
    public int getNextAutoIncremental(String tableName) {
        return 0;
    }

    @Override
    public void logOut(HttpServletRequest request) {

    }

    @Override
    public boolean isSessionActive(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean loginCheck(WorkerDTO user, HttpServletRequest request) {
        return false;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
