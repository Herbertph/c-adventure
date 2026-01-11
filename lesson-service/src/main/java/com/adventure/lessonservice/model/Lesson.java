package com.adventure.lessonservice.model;
import jakarta.persistence.*;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int orderIndex; 
    private String title;

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String instruction;

    @Column(length = 10000)
    private String initialCode;

    @Column(length = 5000)
    private String expectedOutput;

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getOrderIndex() {
        return orderIndex;
    }
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public String getInstruction() {
    return instruction;
}

public void setInstruction(String instruction) {
    this.instruction = instruction;
}
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInitialCode() {
        return initialCode;
    }
    public void setInitialCode(String initialCode) {
        this.initialCode = initialCode;
    }
    public String getExpectedOutput() {
        return expectedOutput;
    }
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
    @Override
public String toString() {
    return "Lesson{" +
            "id=" + id +
            ", orderIndex=" + orderIndex +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", instruction='" + instruction + '\'' +
            ", initialCode='" + initialCode + '\'' +
            ", expectedOutput='" + expectedOutput + '\'' +
            '}';
}

}
