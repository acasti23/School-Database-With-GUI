import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

class StudentFileManager{
   String filename;
   ArrayList<Student> student = new ArrayList<Student>();// arrayList<Student>

   StudentFileManager(String filename){//constuctor //cs136
      try{
         File file = new File(filename);
         this.filename = filename;
         Scanner FileScanner = new Scanner(file); //Create File scanner
           //Read File line by line
         while (FileScanner.hasNext()){ 
            String line = FileScanner.nextLine();//read next line
            String[] s = line.split(",");//Split line into a string array
               //Asign the element of array to variables
            int sid = Integer.parseInt(s[0]);
            String firstname = s[1];
            String lastname = s[2];
            String address = s[3];
            String city = s[4];
            String state = s[5];
            String zip = s[6];
              //Create Student object using variables
            Student stud = new Student(sid, firstname, lastname, address, city, state, zip);
            student.add(stud);//Add Student Object to array list  
         }
         FileScanner.close();
      }
      catch(IOException ioe){
         System.out.println("Error: " + ioe.getMessage());
      }
   }
   boolean AddStudent(int ID, String FirstName,String LastName, String Address, String City, String State, String Zip)throws EmptyFieldException, IOException{
      if (GetStudent(ID) == null){
         if( FirstName.equals("") || LastName.equals("") || Address.equals("") || City.equals("") || State == null || Zip.equals("")){
            throw new EmptyFieldException("One or More Fields Are Empty");
         }
         else{
              //If Student ID does not exist then it will add Student to arraylist
            Student stud = new Student(ID, FirstName, LastName, Address, City, State, Zip);
            student.add(stud);
         
              //Append Student info to file
            FileWriter fwriter = new FileWriter(filename,true); 
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println(stud);
            outputFile.close();
              //Comfirmation that student was Added and returnd true
            System.out.println("Student has been added");
            return true;
         }
      }
         //If student Exist print an error message and return False
      else {
         System.out.println("Student Already Exists");
         return false;
      }
   }
   Student GetStudent(int id )throws EmptyFieldException, IOException{
      if (id == 0){
         throw new EmptyFieldException("Not a Valid ID");
      }
      else{
         for(int i = 0; i < student.size(); i++){
            Student current = student.get(i);
            int ID = current.ID;
            if (ID==id)
               return current;
         }
         return null;
      }
      
   }

   boolean updateStudent(int id, String firstname, String lastname, String address, String city, String state, String zip)throws IOException, EmptyFieldException{
      if( firstname.equals("")|| lastname.equals("")|| address.equals("")|| city.equals("")|| state == null|| zip.equals("") ){
         throw new EmptyFieldException("One or More Fields Are Empty");
      }
      else{
      
         if (GetStudent(id) != null){
            Student stud = GetStudent(id);
            int index = student.indexOf(GetStudent(id));//Find the location of the student in the arraylist
            stud.setFirstName(firstname);
            stud.setLastName(lastname);
            stud.setAddress(address);
            stud.setCity(city);
            stud.setState(state);
            stud.setZip(zip);
         
            student.set(index, stud);//Replace student object in arraylist
            //Write the whole arraylist to the file
            FileWriter fwriter = new FileWriter(filename); 
            PrintWriter outputFile = new PrintWriter(fwriter);
            for (int i = 0; i < student.size(); i++){
               outputFile.println(student.get(i));
            }
         
            outputFile.close();
            //System.out.println("Student has been updated");
            return true;
         }
      
      }
      return false;
   }
}
class Student{
   int ID;
   String FirstName, LastName, Address, City, State, Zip;
   //Constructor
   Student(int ID, String FirstName,String LastName, String Address, String City, String State, String Zip){
      this.ID = ID;
      this.FirstName = FirstName;
      this.LastName = LastName;
      this.Address = Address;
      this.City = City;
      this.State = State;
      this.Zip = Zip;
   }
   void setID(int SID){ ID = SID;}
   void setFirstName(String firstName){ FirstName = firstName;}
   void setLastName(String lastName){ LastName = lastName; }
   void setAddress(String address){ Address = address; }
   void setCity(String city){ City = city;}
   void setState(String state){ State = state; }
   void setZip(String zip){Zip = zip;}
   public String toString(){
      return ID + "," + FirstName + "," + LastName + "," + Address + "," + City + "," + State + "," + Zip;
   }
}

class CourseFileManager{
   ArrayList<Course> courses = new ArrayList<Course>(); // arrayList<Courset>
   String filename; 

   //Constructor
   CourseFileManager(String filename){
      try{
         this.filename = filename;
         File file = new File(filename);
         Scanner FileScanner = new Scanner(file); //Create File scanner
         //Read File line by line
         if (file.exists()){
            while (FileScanner.hasNext()){ 
               String line = FileScanner.nextLine();//read next line
               String[] c = line.split(",");//Split line into a string array
               //Asign the element of array to variables
               int courseId = Integer.parseInt(c[0]);
               String courseName = c[1];
               String courseDescription = c[2];
               //Create course object using variables
               Course course = new Course(courseId, courseName, courseDescription);
               courses.add(course);//Add course Object to array list 
            }
            FileScanner.close();
         }
      }
      catch(IOException e){
         System.out.println("Error: " + e.getMessage());
      }
   }
   Course GetCourse(int cid)throws EmptyFieldException, IOException{
      //try{  
      if(cid == 0){
         throw new EmptyFieldException("Invalid Course ID");
      }
      else{
         for(int i = 0; i < courses.size(); i++){
            Course current = courses.get(i);
            int ID = current.courseID;
            if (ID==cid)        
               return current;
         }
      }
      return null;
      
   }
   boolean AddCourse(int CID, String courseName, String courseDescrip)throws EmptyFieldException, IOException{
      //If course does not exist collect info, create new course object and add to arraylist and return true 
      if (GetCourse(CID) == null){//Call getCourse method to find if course exis
         if(courseName.equals("")||courseDescrip.equals("")){
            throw new EmptyFieldException("Course ID Field is Blank");
         }
         else{
            Course cour = new Course(CID, courseName, courseDescrip);
            courses.add(cour);
         
            //Append the new course object to the file
            FileWriter fwriter = new FileWriter(filename,true); 
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println(cour.courseID + "," + cour.name + "," + cour.description);
            outputFile.close();
         
            System.out.println("Course has been added");//Confirmation Statement
            return true;
         }
      }
      else {//If the course already exist then display an error message and return false
         System.out.println("Course Already Exists");
         return false;
      }
   }

   boolean updateCourse(int cid, String courName, String courseDescription)throws IOException,EmptyFieldException{
      //Check if course exists, if it does then update the course object and return true
      if (GetCourse(cid) != null){
         Course cour = GetCourse(cid);
         int index = courses.indexOf(cour);//Find the location of the course in the arraylist
         cour.setID(cid);
         cour.setName(courName);
         cour.setDescription(courseDescription);
         courses.set(index, cour);//replace cour
           //print every course in Arraylist in a wiped fi;e
         FileWriter fwriter = new FileWriter(filename); 
         PrintWriter outputFile = new PrintWriter(fwriter);
         for (int i = 0; i < courses.size(); i++){
            outputFile.println(courses.get(i).courseID + "," + courses.get(i).name + "," + courses.get(i).description);  
         }
         outputFile.close();
         System.out.println("Course Has Been Updated");
         return true;
      }
        //if the course does not exsist then display an error message and return false
      System.out.println("Course Does Not Exist");
      return false;
   }
}
class Course{
   int courseID;
   String name, description;
   Course(int CourseID, String name, String description){
      this.courseID = courseID;
      this.name = name;
      this.description = description;
   }
   void setID(int courseID){ this.courseID = courseID;}
   void setName(String name){ this.name = name; }
   void setDescription(String description){this.description = description;}
   public String toString(){
      return  courseID + "," + name + "," + description;
   }
}
class Enrollment{
   int SID, CID, EID;
   String year, semester;
   char grade;
   Enrollment( int EID, int CID, int SID, String year, String semester, char grade){
      this.SID = SID;
      this.CID = CID;
      this.EID = EID;
      this.year = year;
      this.semester = semester;
      this.grade = grade;
   }
   void setCID(int CID) {this.CID = CID;}
   void setSID(int SID){this.SID = SID;}
   void setEID(int EID){this.EID = EID;}
   void setYear(String year) { this.year = year;}
   void setSemester(String semester) {this.semester = semester; }
   void setGrade(char grade){this.grade = grade;}
}

class EnrollmentFileManager{
   String filename;
   ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();// arrayList<Enrollment>
   CourseFileManager c = new CourseFileManager("course.txt");
   StudentFileManager s = new StudentFileManager("student.txt");
   //Constructure
   EnrollmentFileManager(String filename){
      try{
         this.filename = filename;
         File file = new File(filename);
         Scanner FileScanner = new Scanner(file); //Create File scanner
         //Read File line by line
         if (file.exists()){
            while (FileScanner.hasNext()){ 
               String line = FileScanner.nextLine();//read next line
               String[] e = line.split(",");//Split line into a string array
               //Asign the element of array to variables
               int EID = Integer.parseInt(e[0]);
               int SID = Integer.parseInt(e[1]);
               int CID = Integer.parseInt(e[2]);
               String Year = e[3];
               String Semester = e[4];
               char Grade = e[5].charAt(0);
               //Create course object using variables
               Enrollment enrollment = new Enrollment( EID, SID, CID,Year, Semester,Grade);
               enrollments.add(enrollment);//Add course Object to array list 
            }
            FileScanner.close();
         }
      }
      catch(IOException ioe){
         System.out.println("Error" + ioe.getMessage());
      }
   }

   boolean addEnrollment(int EID, int CID, int SID,  String year, String semester, char grade)throws IOException, EmptyFieldException {
      //If Enrollment does not exsist then add it to the arraylist and return true
      if( year.equals("") || semester == null || grade == ' '){
         throw new EmptyFieldException("One or More Fields Are Empty");
      }
      else{
         if (GetEnrollment(EID, CID, SID, year, semester) == null){
            Enrollment enroll = new Enrollment(EID, SID, CID, year, semester, grade);
            enrollments.add(enroll);
         
            //Append the new course object to the file
            FileWriter fwriter = new FileWriter(filename,true); 
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println(enroll.EID + "," + enroll.SID + "," + enroll.CID + "," + enroll.year + "," + enroll.semester + "," + enroll.grade);
            outputFile.close();
         
            System.out.println("Enrollment Has Been Added");
            return true;
         }
         System.out.println("Enrollment Already Exists");
         return false;
      }
   }

   Enrollment GetEnrollment(int eid, int cid, int sid, String Year, String Semester) throws EmptyFieldException, IOException{
      if (c.GetCourse(cid) != null && s.GetStudent(sid) != null){
         for(int i =0; i< enrollments.size(); i++){
            Enrollment current = enrollments.get(i);
            int Eid = current.EID;
            if(Eid == eid){ 
               return current;
            }
         }
      }
      return null;
   }
   boolean AddEnrollment(int EID, int CID, int SID, String year, String semester, char Grade)throws EmptyFieldException, IOException{
      if (year.equals("") || semester == null || Grade == ' '){
         throw new EmptyFieldException("One or More Fields Are Empty");
      }
      else{
       //If Enrollment does not exsist then add it to the arraylist and return true
         if (GetEnrollment(EID, CID, SID, year, semester) == null){
            if (c.GetCourse(CID) != null && s.GetStudent(SID) != null){
               Enrollment enroll = new Enrollment(EID, SID, CID, year, semester, Grade);
               enrollments.add(enroll);
            
             //Append the new course object to the file
               FileWriter fwriter = new FileWriter(filename,true); 
               PrintWriter outputFile = new PrintWriter(fwriter);
               outputFile.println(enroll.EID + "," + enroll.CID + "," + enroll.SID + "," + enroll.year + "," + enroll.semester + "," + enroll.grade);
               outputFile.close();
            
               System.out.println("Enrollment Has Been Added");
               return true; 
            }
         }
         return false;
      }
   }
   Enrollment GetEnrollment(int eid)throws EmptyFieldException, IOException {
      if (eid==0){
         throw new EmptyFieldException("Not a Valid ID");
      }
      else{
         for(int i =0; i< enrollments.size(); i++){
            Enrollment current = enrollments.get(i);
            int Eid = current.EID;
            if(Eid == eid){
               return current;
            }
         }
      }
      return null;
   }
   boolean updateEnrollment(int EID, int CID, int SID, String Year, String semester, char Grade)throws IOException, EmptyFieldException{
      if (GetEnrollment(EID, CID, SID, semester, Year) != null){
         if (Year.equals("") || semester == null || Grade == ' '){
            throw new EmptyFieldException("One or More Fields Are Empty");
         }
         else{
            if (c.GetCourse(CID) != null && s.GetStudent(SID) != null){
               Enrollment enroll = GetEnrollment(EID, CID, SID, Year, semester);
               int index = enrollments.indexOf(enroll);
               enroll.setEID(EID);
               enroll.setCID(CID);
               enroll.setSID(SID);
               enroll.setYear(Year);
               enroll.setSemester(semester);
               enroll.setGrade(Grade);
               enrollments.set(index, enroll);
            
               FileWriter fwriter = new FileWriter(filename); 
               PrintWriter outputFile = new PrintWriter(fwriter);
               for (int i = 0; i < enrollments.size(); i++){
                  outputFile.println(enrollments.get(i).EID + "," +  enrollments.get(i).CID + "," + enrollments.get(i).SID + "," +enrollments.get(i).year + "," + enrollments.get(i).semester + "," + enrollments.get(i).grade);  
               }
               outputFile.close();
               System.out.println("Enrollment Has Been Updated");
               return true;
            }
         }
      }
      System.out.println("Enrollment Does Not Exist");
      return false;
   }
   String FindStudentsEnrolled(String CourseName, String Semester, String Year)throws IOException, EmptyFieldException{
      String report = "";
      if (CourseName.equals("") || Semester == null|| Year.equals("")){
         throw new EmptyFieldException("Error One or More Fields are Empty");
      }
      else{ 
         for (int i = 0; i< c.courses.size(); i++){
            Course currentCourse = c.courses.get(i);
            if(currentCourse.name.equals(CourseName)){
               int CID = currentCourse.courseID;
               for(int j = 0; j< enrollments.size(); j++){
                  Enrollment currentEnrollment = enrollments.get(j);
                  if((currentEnrollment.CID) == CID||( Year.equals(currentEnrollment.year)) &&( Semester.equals(currentEnrollment.semester))){
                     int SID = currentEnrollment.SID;
                     Student student = s.GetStudent(SID);
                     report += "      " + student.ID + "        " + student.FirstName + " " + student.LastName + "         "  + currentEnrollment.semester + "      " + currentEnrollment.year + "       " + currentEnrollment.grade +"\n";
                  }
               }
            }
         
         } 
      }
      return report;
   }
}