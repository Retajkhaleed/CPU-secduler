import java.util.*;

class Process {
String pid;     // اسم العملية 
int bt;         // Burst Time وقت التنفيذ
int at;         // Arrival Time وقت الوصول
int tat;        // Turnaround Time
int wt;         // Waiting Time
 int remaining; //الوقت المتبقي

Process(String pid, int bt, int at) {
this.pid = pid;
this.bt = bt;
this.at = at;
this.remaining = bt;
}}

public class CPUSchedule {

static Scanner sc = new Scanner(System.in);
static int n;

 public static void main(String[] args) {
List<Process> processes = getInput();
roundRobin(processes, 2);           
fcfs(processes);                       
sjf(processes);                        
srtf(processes); }
    

 public static List<Process> getInput() {
System.out.print("Enter number of processes: ");
n = sc.nextInt();
List<Process> processes = new ArrayList<>();

for (int i = 0; i < n; i++) {
    
System.out.print("Enter Burst Time for P" + (i + 1) + ": ");
int bt = sc.nextInt();
System.out.print("Enter Arrival Time for P" + (i + 1) + ": ");
int at = sc.nextInt();
processes.add(new Process("P" + (i + 1), bt, at));}
 return processes;
}

    

public static void printOutput(String name, List<String> gantt, List<Process> results) {
    
System.out.println("\n== " + name + " ==");
System.out.println("Gantt Chart: " + String.join(" | ", gantt));
System.out.println("Process\tBT\tAT\tTAT\tWT");

double totalTAT = 0, totalWT = 0;
for (Process p : results) {
 System.out.printf("%-5s\t%d\t%d\t%d\t%d\n", p.pid, p.bt, p.at, p.tat, p.wt);
totalTAT += p.tat;
totalWT  += p.wt;
}
    
 System.out.printf("Avg TAT = %.2f\n", totalTAT / results.size());
System.out.printf("Avg WT  = %.2f\n", totalWT / results.size());
System.out.println("*********************************");
}

    

    // خوارزمية FCFS
public static void fcfs(List<Process> input) {
List<Process> processes = new ArrayList<>();
for (Process p : input) {
processes.add(new Process(p.pid, p.bt, p.at));}

    
        
for (int i = 0; i < processes.size() - 1; i++) {//lop for sort process by the Arrival Time using "Selection sort"
int min = i;
for (int j = i + 1; j < processes.size(); j++) {
if (processes.get(j).at < processes.get(min).at) {
min = j;}
}
    
if (min != i) {
Process temp = processes.get(i);
processes.set(i, processes.get(min));
processes.set(min, temp);}
}
    

int time = 0;             // to track current time
List<String> gantt = new ArrayList<>();
    // Compute TAT and WT for each process
 for (Process p : processes) {
if (time < p.at) {
gantt.add("Idle");
time ++;  // wait if cpu is idle
}

gantt.add(p.pid); // build gantt chart
time += p.bt;              // update  time
 p.tat = time - p.at;       // calculate turnaround time
p.wt = p.tat - p.bt;              // calculate waiting time
 }
printOutput("FCFS",gantt,processes);
}

    
    
    // خوارزمية SJF
 public static void sjf(List<Process> input) {

 // making a copy to avoid modifying it
 List<Process> processes = new ArrayList<>();
for (Process p : input) {
processes.add(new Process(p.pid, p.bt, p.at));
}
     

//making lists for completed processes and gantt char
List<Process> completed = new ArrayList<>();
List<String> gantt = new ArrayList<>();
int time = 0;  // Current time tracker
     

// runing a Loop until all processes are completed
while (completed.size() < processes.size()) {
Process shortest = null;

// finding the shortest process that has already arrived and not completed(the one that has the shortest time)
for (Process p : processes) {
if (!completed.contains(p) && p.at <= time) {
if (shortest == null || p.bt < shortest.bt) {
shortest = p;
}}}    

    
// if thear is no process that has arrived CPU remains idle for one unit of time
if (shortest == null) {
gantt.add("Idle");
 time++;
continue;
}
        
// executing the selected process(the shortest process)
gantt.add(shortest.pid);                     // adding a process to Gantt chart
 if (time < shortest.at) time = shortest.at;  // adjust time if CPU was idle
shortest.wt = time - shortest.at;            // waiting time
time += shortest.bt;                         
shortest.tat = shortest.wt + shortest.bt;    //turnaround time
completed.add(shortest);                     // marking the process as completed
}

       
// Printing the results
printOutput("SJF", gantt, completed);
}
       
    
    // خوارزمية SRTF
    public static void srtf(List<Process> input) {
List<Process> processes = new ArrayList<>();//list of processes 
for (Process p : input) processes.add(new Process(p.pid, p.bt, p.at));//loop to create copy of each process 

int time = 0, complete = 0;
int[] remaining = new int[n];
for (int i = 0; i < n; i++) remaining[i] = processes.get(i).bt;//loop to copy each process into the remaining array

List<String> gantt = new ArrayList<>();
Process[] result = new Process[n];

while (complete < n) {//completed process 
int idx = -1;
int minBt = Integer.MAX_VALUE;

for (int i = 0; i < n; i++) {//the one with shortest remaining time
if (processes.get(i).at <= time && remaining[i] > 0 && remaining[i] < minBt) {
idx = i;
minBt = remaining[i];
}}

    

if (idx == -1) {//if no process ready make the cpu idle and increment time
gantt.add("Idle");
time++;
continue;
}

remaining[idx]--;//decrease the remaining time 
 gantt.add(processes.get(idx).pid);//add to thr grantt chart

if (remaining[idx] == 0) {
complete++;
int end_time = time + 1;
int tat = end_time - processes.get(idx).at;
int wt = tat - processes.get(idx).bt;
Process p = processes.get(idx);
p.tat = tat;
p.wt = wt;
    result[idx] = p;
}
    
time++;
}
printOutput("SRTF", gantt, Arrays.asList(result));//print results 
}

 // خوارزمية Round Robin
public static void roundRobin(List<Process> input, int quantum) {
List<Process> pList = new ArrayList<>();//list of processes 
for (Process p : input) pList.add(new Process(p.pid, p.bt, p.at));//loop to create copy of each process 
int[] remBt = new int[n];
for (int i = 0; i < n; i++) remBt[i] = pList.get(i).bt;//copy burst time from list to array
boolean[] arrived = new boolean[n];
boolean[] completed = new boolean[n];
int time = 0;
 List<String> gantt = new ArrayList<>();
List<Integer> queue = new LinkedList<>();
Process[] result = new Process[n];
int done = 0;

 while (done < n) {//Adding arrived processes to the queue
     
for (int i = 0; i < n; i++) {
if (pList.get(i).at <= time && !arrived[i]) {
queue.add(i);
arrived[i] = true;
}}

if (queue.isEmpty()) {//CPU is idlle increment time 
gantt.add("Idle");
time++;
continue;}

     
//Get next process from queue and calculate execution time
int idx = queue.remove(0);
int execTime = Math.min(quantum, remBt[idx]);
            
//Execute the current process and add new arrived processes to the queue
for (int i = 0; i < execTime; i++) {
gantt.add(pList.get(idx).pid);
time++;
remBt[idx]--;

for (int j = 0; j < n; j++) {
if (pList.get(j).at <= time && !arrived[j]) {
queue.add(j);
 arrived[j] = true;
}
}}
     
//if process done calculate time else add back to queue
 if (remBt[idx] > 0) {
 queue.add(idx);}
     
 else if (!completed[idx]) {
int tat = time - pList.get(idx).at;
int wt = tat - pList.get(idx).bt;
pList.get(idx).tat = tat;
pList.get(idx).wt = wt;
result[idx] = pList.get(idx);
completed[idx] = true;
done++;
}}
    
printOutput("Round Robin", gantt, Arrays.asList(result));
}}
