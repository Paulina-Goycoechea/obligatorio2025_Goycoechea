package uy.edu.um.entities;

import lombok.Data;

@Data
public class CrewMember {
    private int personId;
    private String department;
    private String job;

    public CrewMember(int personId, String department, String job) {
        this.personId = personId;
        this.department = department;
        this.job = job;
    }
}
