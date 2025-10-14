# ⚙️ CPU Scheduling Simulator (Java)

This project is a simple *CPU Scheduling simulation* written in *Java*.  
It demonstrates how different scheduling algorithms manage process execution and CPU utilization.

---

## 🧩 File Included
| File | Description |
|------|--------------|
| Process.java | Defines the Process class used to represent each process with attributes such as ID, arrival time, burst time |

---

## 🚀 Description
The project is designed to simulate how an operating system schedules multiple processes for execution.  
The Process class serves as the foundation for implementing different scheduling algorithms, such as:

- *First Come First Serve (FCFS)*
- *Shortest Job First (SJF)*
- *Shortest Remaining Time First (SRTF)*
- *Round Robin (RR)*

---

## 🧠 Key Concepts
- Process representation in Java (Attributes: ID, Arrival Time, Burst Time, Waiting Time, Turnaround Time)
- CPU scheduling algorithms 

---

## 🧱 Example Process Structure
```java
public class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}
