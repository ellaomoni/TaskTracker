import java.time.LocalDateTime;


//Task Model
public class Task {
    private int  id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; 


    // --- Constructor ---
    public Task(int id, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
 
    // --- Getters ---
 
    public int getId() {
        return id;
    }
 
    public String getDescription() {
        return description;
    }
 
    public String getStatus() {
        return status;
    }
 
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
 
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
 
    // --- Setters ---
 
    public void setId(int id) {
        this.id = id;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
 
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}